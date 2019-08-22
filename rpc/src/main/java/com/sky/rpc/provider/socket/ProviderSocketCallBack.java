package com.sky.rpc.provider.socket;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.SocketAddress;
import com.sky.rpc.base.Result;
import com.sky.rpc.base.RpcRequest;
import com.sky.rpc.call.RpcConnectCall;

/**
 * socket rpc call back
 * @author 王帆
 * @date  2019年6月21日 下午2:53:53
 */
public class ProviderSocketCallBack implements RpcConnectCall{
	private ObjectInputStream input;
	private ObjectOutputStream output;
	private String requestId;
	private SocketAddress socketAddress;
	public ProviderSocketCallBack(ObjectInputStream input, ObjectOutputStream output, String requestId, SocketAddress socketAddress) {
		this.input=input;
		this.output=output;
		this.requestId=requestId;
		this.socketAddress=socketAddress;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T call(RpcRequest req) throws Throwable {
		String id = requestId+"_"+req.getClassName()+"_"+req.getMethodName();
		req.setRequestId(id);
		//代理调用参数的方法
		output.writeObject(req);
		Result<?> res = (Result<?>) input.readObject();
		if(res.hasException()) {
			throw res.getException();
		}
		return(T) res.getData();
	}
	
	@Override
	public SocketAddress getSocketAddress() {
		return socketAddress;
	}
}
