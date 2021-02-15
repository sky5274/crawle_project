package com.sky.rpc.core.cilent;

import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import com.sky.rpc.base.RpcRequest;
import com.sky.rpc.core.RpcTypeContant;
import com.sky.rpc.handle.factory.RpcRequetHandleFactory;
import com.sky.rpc.util.RpcSpringBeanUtil;

/**
 * rpc proxy invoke client
 * @author 王帆
 * @date  2020年1月16日 下午10:34:40
 */
public class RpcClient{
	
	private InetSocketAddress addr;
	private int timeout=30000;
	private String rpcType;
	public RpcClient(InetSocketAddress addr){
		this(addr, RpcTypeContant.getType());
	}
	public RpcClient(InetSocketAddress addr,int timeout){
		this(addr, timeout,RpcTypeContant.getType());
	}
	public RpcClient(InetSocketAddress addr, String rpcType){
		this.addr=addr;
		if(StringUtils.isEmpty(rpcType)) {
			rpcType=RpcTypeContant.getType();
		}
		this.rpcType=rpcType;
	}
	public RpcClient(InetSocketAddress addr,int timeout, String rpcType){
		this.addr=addr;
		this.timeout=timeout;
		if(StringUtils.isEmpty(rpcType)) {
			rpcType=RpcTypeContant.getType();
		}
		this.rpcType=rpcType;
	}
	
	public <T>  T request(Class<?> serviceInterface, Method method, Object[] args,String interfaceImpl) throws Throwable {
		RpcRequest request=new RpcRequest(interfaceImpl==null?serviceInterface.getName():interfaceImpl,method,args);
		return request(request);
	}
	public <T> T request(RpcRequest request) throws Throwable {
		RpcClientHandle rpcClientHandle=getRpcClientHandle(rpcType);
		//rpc客户端  预处理
		RpcRequetHandleFactory.before(request);
		T res =rpcClientHandle.invoke(request,addr,timeout);
		//rpc客户端  执行返回结果
		RpcRequetHandleFactory.after(request, res);
		return res;
	}
	
	private static Map<String, RpcClientHandle> rpcClientHandelMap=null;
	
	public static RpcClientHandle getRpcClientHandle(String rpcType) {
		if(rpcClientHandelMap==null) {
			Map<String, RpcClientHandle> handlemaps = RpcSpringBeanUtil.getBeansByType(RpcClientHandle.class);
			if(CollectionUtils.isEmpty(handlemaps)) {
				throw new IllegalAccessError("rpc client handle is empty");
			}
			rpcClientHandelMap=new HashMap<String, RpcClientHandle>(16);
			for(RpcClientHandle handle:handlemaps.values()) {
				rpcClientHandelMap.put(handle.getRpcType(), handle);
			}
		}
		return rpcClientHandelMap.get(rpcType);
	}
}
