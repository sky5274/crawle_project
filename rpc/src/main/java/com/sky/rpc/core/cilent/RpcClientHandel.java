package com.sky.rpc.core.cilent;

import java.net.InetSocketAddress;
import com.sky.rpc.base.RpcRequest;

public interface RpcClientHandel {
	public <T> T invoke(RpcRequest request, InetSocketAddress addr, int timeout) throws Throwable;
	
}
