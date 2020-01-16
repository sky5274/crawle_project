package com.sky.rpc.core.cilent.http;

import java.net.InetSocketAddress;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sky.rpc.base.RpcRequest;
import com.sky.rpc.core.RpcTypeContant;
import com.sky.rpc.core.cilent.RpcClientHandle;
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
		String result = RpcHttpUtil.doPost(String.format("http://%s:%d/resttemplate/invoke", addr.getHostString(),addr.getPort()), request);
		if(result ==null) {
			return null;
		}
		JSONObject obj = JSON.parseObject(result);
		if(obj.get("exception") !=null) {
			 throw JSON.parseObject(JSON.toJSONString(obj.get("exception")),Exception.class);
		}
		if(request.getReturnType() !=null) {
			return (T) JSON.parseObject(JSON.toJSONString(obj.get("data")),request.getReturnType());
		}else {
			return (T) obj.get("data");
		}
	}

	@Override
	public String getRpcType() {
		return RpcTypeContant.rpcTypeLimit.get(2);
	}
}
