package com.sky.rpc.provider.netty;

import java.net.SocketAddress;
import java.util.concurrent.SynchronousQueue;
import com.sky.rpc.base.Result;
import com.sky.rpc.base.RpcRequest;
import com.sky.rpc.call.RpcConnectCall;
import io.netty.channel.ChannelHandlerContext;

/**
 * netty  call back
 * @author 王帆
 * @date  2019年6月21日 下午2:54:17
 */
public class ProviderNettyCallBack implements RpcConnectCall{
	ChannelHandlerContext ctx;
	String requestId;
	public ProviderNettyCallBack(ChannelHandlerContext ctx, String requestId) {
		this.ctx=ctx;
		this.requestId=requestId;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T call(RpcRequest req) throws Throwable {
		String id = requestId+"_"+req.getClassName()+"."+req.getMethodName();
		req.setRequestId(id);
		SynchronousQueue<Result<?>> quene=new SynchronousQueue<Result<?>>();
		//代理调用参数的方法
		ctx.writeAndFlush(req);
		ProviderObjectNettyHandel.argSyncObjectMap.put(id, quene);
		Result<?> res = quene.take();
		if(res.hasException()) {
			throw res.getException();
		}
		return (T) res.getData();
	}

	@Override
	public SocketAddress getSocketAddress() {
		return ctx.channel().remoteAddress();
	}

}
