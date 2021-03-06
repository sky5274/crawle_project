package com.sky.rpc.core.client.http;

import java.lang.reflect.Type;
import java.net.InetSocketAddress;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sky.rpc.base.RpcRequest;
import com.sky.rpc.core.RpcTypeContant;
import com.sky.rpc.core.client.RpcClientHandle;
import com.sky.rpc.util.RpcHttpUtil;

/**
 * rpc http invoke handle
 * @author wangfan
 *
 * @param <T>
 */
@Component
public class RpcHttpClientInvokeHandle implements RpcClientHandle{
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T invoke(RpcRequest request, InetSocketAddress addr, int timeout) throws Throwable {
		Type returnType = request.getReturnType();
		request.setReturnType(null);
		String result = RpcHttpUtil.doPost(String.format("http://%s:%d/resttemplate/invoke", addr.getHostString(),addr.getPort()), request);
		if(result ==null) {
			return null;
		}
		JSONObject obj = JSON.parseObject(result);
		if(obj.get("exception") !=null) {
			Class<? extends Throwable> clazz=null;
			try {
				clazz= (Class<? extends Throwable>) Class.forName(obj.getString("throwClazz"));
			} catch (Exception e) {
				clazz=Exception.class;
			}
			throw JSON.parseObject(JSON.toJSONString(obj.get("exception")),clazz);
		}
		if(returnType !=null) {
			return (T) JSON.parseObject(JSON.toJSONString(obj.get("data")),returnType);
		}else {
			return (T) obj.get("data");
		}
	}

	@Override
	public String getRpcType() {
		return RpcTypeContant.rpcTypeLimit.get(2);
	}
}
