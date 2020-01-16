package com.sky.rpc.core.cilent;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.net.InetSocketAddress;

import org.springframework.util.StringUtils;

import com.sky.rpc.base.RpcRequest;
import com.sky.rpc.core.cilent.http.RpcHttpClientInvokeHandle;
import com.sky.rpc.core.cilent.netty.RpcNettyClientHandel;
import com.sky.rpc.core.client.socket.RpcSocketClientHandel;
import com.sky.rpc.handle.factory.RpcRequetHandleFactory;
import com.sky.rpc.provider.ProviderServer;

public class RpcClient{
	
	private InetSocketAddress addr;
	private int timeout=30000;
	private String rpcType;
	public RpcClient(InetSocketAddress addr){
		this(addr, ProviderServer.getType());
	}
	public RpcClient(InetSocketAddress addr,int timeout){
		this(addr, timeout,ProviderServer.getType());
	}
	public RpcClient(InetSocketAddress addr, String rpcType){
		this.addr=addr;
		if(StringUtils.isEmpty(rpcType)) {
			rpcType=ProviderServer.getType();
		}
		this.rpcType=rpcType;
	}
	public RpcClient(InetSocketAddress addr,int timeout, String rpcType){
		this.addr=addr;
		this.timeout=timeout;
		if(StringUtils.isEmpty(rpcType)) {
			rpcType=ProviderServer.getType();
		}
		this.rpcType=rpcType;
	}
	
	@SuppressWarnings("unchecked")
	public <T>  T request(Class<?> serviceInterface, Method method, Object[] args,String interfaceImpl) throws Throwable {
		RpcRequest request=new RpcRequest(interfaceImpl==null?serviceInterface.getName():interfaceImpl,method,args);
		return request(request,method.getGenericReturnType());
	}
	public <T> T request(RpcRequest request,Type type) throws Throwable {
		RpcClientHandel rpcClientHandel;
		if(ProviderServer.rpcTypeLimit.indexOf(rpcType)<2) {
			if(ProviderServer.isSocketServer()) {
				rpcClientHandel=new RpcSocketClientHandel();
			}else {
				rpcClientHandel=new RpcNettyClientHandel();
			}
		}else {
			rpcClientHandel=new RpcHttpClientInvokeHandle<T>(type);
		}
		
		//rpc客户端  预处理
		RpcRequetHandleFactory.before(request);
		T res =rpcClientHandel.invoke(request,addr,timeout);
		//rpc客户端  执行返回结果
		RpcRequetHandleFactory.after(request, res);
		return res;
	}
}
