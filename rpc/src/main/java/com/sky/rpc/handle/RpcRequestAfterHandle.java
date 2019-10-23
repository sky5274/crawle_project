package com.sky.rpc.handle;

import com.sky.rpc.base.RpcRequest;

/**
 *    rpc 调用后Handle
 * @author 王帆
 * @date  2019年10月2日 下午10:00:25
 */
public interface RpcRequestAfterHandle {
	
	public void afterInvoke(RpcRequest req,Object res);
}
