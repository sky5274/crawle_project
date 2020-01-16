package com.sky.rpc.core.cilent.http;

import java.lang.reflect.Type;
import java.net.InetSocketAddress;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sky.rpc.base.RpcRequest;
import com.sky.rpc.core.cilent.RpcClientHandel;
import com.sky.rpc.util.RpcHttpUtil;

/**
 * rpc http invoke handle
 * @author wangfan
 *
 * @param <T>
 */
public class RpcHttpClientInvokeHandle<T> implements RpcClientHandel{
	
	private Type returnClass;

	public RpcHttpClientInvokeHandle(Type type) {
		this.returnClass=type;
	}

	@SuppressWarnings({ "unchecked"})
	@Override
	public T invoke(RpcRequest request, InetSocketAddress addr, int timeout) throws Throwable {
		String result = RpcHttpUtil.doPost(String.format("http://%s:%d/resttemplate/invoke", addr.getHostString(),addr.getPort()), request);
		if(result ==null) {
			return null;
		}
		JSONObject obj = JSON.parseObject(result);
		if(obj.get("exception") !=null) {
			 throw JSON.parseObject(JSON.toJSONString(obj.get("exception")),Exception.class);
		}
		return (T) JSON.parseObject(JSON.toJSONString(obj.get("data")),returnClass);
	}
}
