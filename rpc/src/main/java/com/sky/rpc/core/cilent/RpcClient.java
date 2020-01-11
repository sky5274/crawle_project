package com.sky.rpc.core.cilent;

import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import com.sky.rpc.base.RpcRequest;
import com.sky.rpc.core.cilent.netty.RpcNettyClientHandel;
import com.sky.rpc.core.client.socket.RpcSocketClientHandel;
import com.sky.rpc.handle.factory.RpcRequetHandleFactory;
import com.sky.rpc.provider.ProviderServer;

public class RpcClient <T>{
	
	private InetSocketAddress addr;
	private int timeout=30000;
	public RpcClient(InetSocketAddress addr){
		this.addr=addr;
	}
	public RpcClient(InetSocketAddress addr,int timeout){
		this.addr=addr;
		this.timeout=timeout;
	}
	
	public T request(Class<T> serviceInterface, Method method, Object[] args,String interfaceImpl) throws Throwable {
		RpcRequest request=new RpcRequest(interfaceImpl==null?serviceInterface.getName():interfaceImpl,method,args);
		return request(request);
	}
	public T request(RpcRequest request) throws Throwable {
		RpcClientHandel rpcClientHandel;
		if(ProviderServer.isSocketServer()) {
			rpcClientHandel=new RpcSocketClientHandel();
		}else {
			rpcClientHandel=new RpcNettyClientHandel();
		}
		//rpc客户端  预处理
		RpcRequetHandleFactory.before(request);
		T res =rpcClientHandel.invoke(request,addr,timeout);
		//rpc客户端  执行返回结果
		RpcRequetHandleFactory.after(request, res);
		return res;
	}
}
