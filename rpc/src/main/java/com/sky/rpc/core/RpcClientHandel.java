package com.sky.rpc.core;

import java.net.InetSocketAddress;
import com.sky.rpc.base.RpcRequest;

public interface RpcClientHandel {
	public <T> T invoke(RpcRequest request, InetSocketAddress addr) throws Throwable;
	
}
