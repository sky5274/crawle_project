package com.sky.rpc.core;

import java.lang.reflect.Method;
import java.net.InetSocketAddress;

import com.sky.rpc.base.RpcRequest;
import com.sky.rpc.core.client.socket.RpcSocketClientHandel;
import com.sky.rpc.resource.ResouceProperties;

public class RpcClient <T>{
	
	private InetSocketAddress addr;
	public RpcClient(InetSocketAddress addr){
		this.addr=addr;
	}
	
	public T request(Class<T> serviceInterface, Method method, Object[] args,String interfaceImpl) throws Throwable {
		 RpcRequest request=new RpcRequest(interfaceImpl==null?serviceInterface.getClass().getName():interfaceImpl,method,args);
		return request(request);
	}
	public T request(RpcRequest request) throws Throwable {
		RpcClientHandel rpcClientHandel;
		if(ResouceProperties.isSocketServer()) {
			rpcClientHandel=new RpcSocketClientHandel();
		}else {
			rpcClientHandel=new RpcNettyClientHandel();
		}
		try {
			return  rpcClientHandel.invoke(request,addr);
		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
}
