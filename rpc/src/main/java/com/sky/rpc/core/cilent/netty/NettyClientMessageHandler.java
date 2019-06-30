package com.sky.rpc.core.cilent.netty;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.SynchronousQueue;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sky.rpc.base.Result;
import com.sky.rpc.base.RpcRequest;
import com.sky.rpc.call.RpcCallBack;
import com.sky.rpc.provider.ProviderMethodInvoker;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

@SuppressWarnings("deprecation")
@Sharable
public class NettyClientMessageHandler extends ChannelInboundHandlerAdapter{
	SynchronousQueue<Result<?>> queue=null;
    Log logger = LogFactory.getLog(this.getClass());
	private String reuestId;
	private Map<String, Object> argMape=new ConcurrentHashMap<>();

    public void channelInactive(ChannelHandlerContext ctx) throws InterruptedException   {
//        InetSocketAddress address =(InetSocketAddress) ctx.channel().remoteAddress();
//        logger.debug("与RPC服务器断开连接."+address);
        queue.put(new Result<>(reuestId,new Exception("rpc client closed")));
        ctx.channel().close();
    }
    public void channelRead(ChannelHandlerContext ctx, Object response)throws Exception {
    	if(response instanceof Result) {
    		queue.put((Result<?>)response);
    	}else if(response instanceof RpcRequest){
    		RpcRequest req=(RpcRequest) response;
    		Object arg = argMape.get(req.getRequestId());
    		try {
				ctx.writeAndFlush(new Result<>(req.getRequestId(), ProviderMethodInvoker.invoke(ctx.channel().localAddress(),arg, req)));
			} catch (Throwable e) {
				ctx.writeAndFlush(new Result<>(req.getRequestId(), e));
			}
    	}
    	ctx.flush();
    }
    public SynchronousQueue<Result<?>> sendRequest(RpcRequest request,Channel channel) throws Exception {
    	queue = new SynchronousQueue<>();
    	Object[] args = request.getArgs();
    	argMape.clear();
    	for(int i=0;i<args.length;i++) {
    		Object arg = args[i];
    		if(arg!=null && isRpcCallBack(arg)) {
    			argMape.put(request.getRequestId()+"_"+request.getParameterTypes()[i].getName()+"_"+i, arg);
    			args[i] = RpcCallBack.call;
    		} 
    	}
    	request.setArgs(args);
    	reuestId = request.getRequestId();
    	
    	logger.debug("rpt client proxy mentod: "+request.getClassName()+"."+request.getMethodName());
    	channel.writeAndFlush(request);
    	channel.flush();
  		return queue;
    }
    
    private boolean isRpcCallBack(Object obj) {
    	return obj instanceof RpcCallBack;
    }
    
    
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt)throws Exception {
    	if (evt instanceof IdleStateEvent){
			IdleStateEvent event = (IdleStateEvent)evt;
			if (event.state()== IdleState.ALL_IDLE){
				String info = "客户端已超过60秒未读写数据,关闭连接"+ctx.channel().remoteAddress();
				//logger.debug(info);
				queue.put(new Result<>(reuestId,new Exception(info)));
				ctx.channel().close();
			}
		}else{
			super.userEventTriggered(ctx,evt);
		}
    }
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws InterruptedException{
        //logger.info("RPC通信服务器发生异常."+cause);
        queue.put(new Result<>(reuestId,cause));
        ctx.channel().close();
    }
}
