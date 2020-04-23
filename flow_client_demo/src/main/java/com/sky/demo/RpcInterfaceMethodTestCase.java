package com.sky.demo;


import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.List;

import com.sky.demo.test.MethodInterFace;
import com.sky.rpc.base.RpcRequest;
import com.sky.rpc.core.client.RpcClientHandle;
import com.sky.rpc.core.client.http.RpcHttpClientInvokeHandle;

/**
 * rpc interface method test cash
 * @author wangfan
 * @date 2020年1月16日 下午5:29:01
 */
public class RpcInterfaceMethodTestCase {
	public static void main(String[] args) {
		RpcClientHandle rpcClientHandel=new RpcHttpClientInvokeHandle();
		Class<MethodInterFace> clazz = MethodInterFace.class;
		//RpcRequest request = new RpcRequest(clazz.getName(),clazz.getMethods()[0],new Object[] {232,"dews"});
		RpcRequest request = new RpcRequest(clazz.getName(),clazz.getMethods()[0],new Object[] {Arrays.asList(232,"dews")});
		InetSocketAddress addr=new InetSocketAddress("127.0.0.1", 8970);
		List res = null;
		try {
			res = rpcClientHandel.invoke(request,addr,3000);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		System.err.println(res);
		System.err.println(res.getClass());
	}
}
