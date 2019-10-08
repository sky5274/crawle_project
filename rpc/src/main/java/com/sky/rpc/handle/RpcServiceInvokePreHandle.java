package com.sky.rpc.handle;

import com.sky.rpc.base.RpcRequest;

/**
 * 服务端 rpc方法执行请求  预执行
 * @author 王帆
 * @date  2019年10月5日 下午9:04:22
 */
public interface RpcServiceInvokePreHandle {
	
	public void preInvoke(RpcRequest req);
}
