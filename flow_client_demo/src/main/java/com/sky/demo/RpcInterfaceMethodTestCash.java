package com.sky.demo;


import java.net.InetSocketAddress;
import com.sky.demo.test.MethodInterFace;
import com.sky.rpc.base.RpcRequest;
import com.sky.rpc.core.cilent.RpcClientHandel;
import com.sky.rpc.core.cilent.http.RpcHttpClientInvokeHandle;

/**
 * rpc interface method test cash
 * @author wangfan
 * @date 2020年1月16日 下午5:29:01
 */
public class RpcInterfaceMethodTestCash {
	public static void main(String[] args) {
		RpcClientHandel rpcClientHandel=new RpcHttpClientInvokeHandle<Object>(null);
		Class<MethodInterFace> clazz = MethodInterFace.class;
		RpcRequest request = new RpcRequest(clazz.getName(),clazz.getMethods()[0],new Object[] {232});
		InetSocketAddress addr=new InetSocketAddress("127.0.0.1", 8970);
		Object res = null;
		try {
			res = (String)rpcClientHandel.invoke(request,addr,3000);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		System.err.println(res);
	}
}
