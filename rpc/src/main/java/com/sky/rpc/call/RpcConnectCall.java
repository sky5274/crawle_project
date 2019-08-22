package com.sky.rpc.call;

import java.net.SocketAddress;

import com.sky.rpc.base.RpcRequest;

/**
 * rpc connect call back
 * @author 王帆
 * @date  2019年6月21日 下午2:47:47
 */
public interface RpcConnectCall {
	public <T> T call(RpcRequest req) throws Throwable;

	public SocketAddress getSocketAddress();
}
