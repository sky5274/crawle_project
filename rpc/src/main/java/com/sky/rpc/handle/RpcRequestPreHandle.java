package com.sky.rpc.handle;

import com.sky.rpc.base.RpcRequest;

/**
 * rpc  请求参数预处理接口
 * @author 王帆
 * @date  2019年9月30日 下午1:33:48
 */
public interface RpcRequestPreHandle {
	
	/**
	 * 预处理请求参数
	 * @param req
	 * @author 王帆
	 * @date 2019年9月30日 下午1:36:06
	 */
	public void preRequest(RpcRequest req); 
}
