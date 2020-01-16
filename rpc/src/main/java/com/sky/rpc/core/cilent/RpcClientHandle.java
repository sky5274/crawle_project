package com.sky.rpc.core.cilent;

import java.net.InetSocketAddress;
import com.sky.rpc.base.RpcRequest;

public interface RpcClientHandle {
	
	/**
	 * rpc 方法类型
	 * @return
	 * @author 王帆
	 * @date 2020年1月16日 下午10:09:50
	 */
	public String getRpcType();
	
	/**
	 * 	执行rpc方法
	 * @param request
	 * @param addr
	 * @param timeout
	 * @return
	 * @throws Throwable
	 * @author 王帆
	 * @date 2020年1月16日 下午10:09:33
	 */
	public <T> T invoke(RpcRequest request, InetSocketAddress addr, int timeout) throws Throwable;
	
}
