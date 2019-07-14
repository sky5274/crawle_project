package com.sky.rpc.provider.netty;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.SynchronousQueue;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.FactoryBean;
import com.sky.rpc.base.Result;
import com.sky.rpc.base.RpcRequest;
import com.sky.rpc.call.RpcCallBack;
import com.sky.rpc.call.RpcConnectCallFactory;
import com.sky.rpc.provider.ProviderMethodInvoker;
import com.sky.rpc.thread.RpcRequestThreadPool;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * netty  rpc server message handel
 * @author 王帆
 * @date  2019年6月16日 上午11:36:11
 */
@SuppressWarnings("deprecation")
@Sharable
public class ProviderObjectNettyHandel extends ChannelInboundHandlerAdapter{
	private Log logger=LogFactory.getLog(getClass());
	
	public static Map<String, SynchronousQueue<Result<?>>> argSyncObjectMap=new ConcurrentHashMap<>();

	public void channelInactive(ChannelHandlerContext ctx) throws InterruptedException   {
		logger.info("客户端断开连接!"+ctx.channel().remoteAddress());
		setThrowableInfo(new Throwable(ctx.channel().localAddress()+"连接关闭"));
		ctx.channel().close();
	}
	public void channelRead( ChannelHandlerContext ctx, Object msg) throws InterruptedException   {
		if(msg instanceof RpcRequest) {
			RpcRequest request=(RpcRequest) msg;
			initRpcRequest(ctx,request);
		}else if(msg instanceof Result) {
			final Result<?> res =(Result<?>) msg;
			SynchronousQueue<Result<?>> quene = argSyncObjectMap.get(res.getRequestId());
			quene.put(res);
			ctx.flush();
		}
	}
	
	/**
	 * 使用线程调用服务
	* <p>Title: initRpcRequest</p>
	* <p>Description: </p>
	* @param ctx
	* @param request
	 */
	public void initRpcRequest(final ChannelHandlerContext ctx, final RpcRequest request) {
		RpcRequestThreadPool.getCommonThreadExcutor().execute(new Runnable() {
			
			@Override
			public void run() {
				logger.info("RPC客户端请求接口:"+request.getClassName()+"   方法名:"+request.getMethodName());
				Result<Object> result=null;
				RpcConnectCallFactory.addConnectCall(new ProviderNettyCallBack(ctx, request.getRequestId()));
				int i=0;
				Object[] args  =request.getArgs();
				if(args!=null) {
					for(Object arg:args) {
						if(arg!=null) {
							if(arg instanceof RpcCallBack) {
								args[i]=getArgCallProxy(request.getParameterTypes()[i],ctx,i,request.getRequestId());
							}
						}
						i++;
					}
				}
				request.setArgs(args);
				try {
					result=new Result<Object>(request.getRequestId(),ProviderMethodInvoker.invoke(ctx.channel().localAddress(),request));
				} catch (Throwable e) {
					result=new Result<>(request.getRequestId(),e);
				}
				i=0;
				if(request.getArgs() !=null) {
					for(Object arg:request.getArgs()) {
						if(arg instanceof RpcCallBack) {
							clearObjectMap(request.getParameterTypes()[i],i,request.getRequestId());
						}
						i++;
					}
				}
				
				ctx.writeAndFlush(result);
				ctx.flush();
				RpcConnectCallFactory.removeNowConnectCall();
			}
		});
	}
	
	private void clearObjectMap(Class<?> serviceInterface, int i, String requestId) {
		List<String> keys  =new LinkedList<>(argSyncObjectMap.keySet());
		String objkey=requestId+"_"+serviceInterface.getName()+"_"+i;
		for(String key:keys) {
			if(key.startsWith(objkey)) {
				argSyncObjectMap.remove(key);
			}
		}
	}
	@SuppressWarnings("unchecked")
	private <T> T getArgCallProxy(final Class<?> serviceInterface, final ChannelHandlerContext ctx, final int i, final String requestId) {
		return (T) Proxy.newProxyInstance(FactoryBean.class.getClassLoader(), new Class<?>[]{serviceInterface},
				new InvocationHandler() {
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						logger.debug("rpc netty server call back,class: "+serviceInterface.getName()+" method: "+method.getName());
						RpcRequest req = new RpcRequest(serviceInterface.getName()+"_"+i, method, args);
						String id = requestId+"_"+serviceInterface.getName()+"_"+i;
						req.setRequestId(id);
						SynchronousQueue<Result<?>> quene=new SynchronousQueue<Result<?>>();
						//代理调用参数的方法
						ctx.writeAndFlush(req);
						argSyncObjectMap.put(id, quene);
						Result<?> res = quene.take();
						if(res.hasException()) {
							throw res.getException();
						}
						return res.getData();
					}
			
		});
	}
	
	public void setThrowableInfo( Throwable e) throws InterruptedException {
		List<String> keys=new LinkedList<>();
		for(String key:argSyncObjectMap.keySet()) {
			SynchronousQueue<Result<?>> quene = argSyncObjectMap.get(key);
			quene.put(new Result<>(null,e));
			keys.add(key);
		}
		for(String key:keys) {
			argSyncObjectMap.remove(key);
		}
	}
	
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt)throws Exception {
		if (evt instanceof IdleStateEvent){
			IdleStateEvent event = (IdleStateEvent)evt;
			if (event.state()== IdleState.ALL_IDLE){
				logger.info("客户端已超过60秒未读写数据,关闭连接"+ctx.channel().remoteAddress());
				setThrowableInfo(new Throwable(ctx.channel().localAddress()+" 客户端已超过60秒未读写数据,关闭连接"));
//				ctx.channel().close();
			}
		}else{
			super.userEventTriggered(ctx,evt);
		}
	}
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws InterruptedException   {
		logger.info(cause.getMessage());
		setThrowableInfo(new Throwable(ctx.channel().localAddress()+" "+cause.getMessage(),cause));
//		ctx.close();
	}
}
