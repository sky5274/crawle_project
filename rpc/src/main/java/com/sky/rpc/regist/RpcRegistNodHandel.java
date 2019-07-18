package com.sky.rpc.regist;

import com.sky.rpc.core.RpcElement;

public interface RpcRegistNodHandel<T> extends RpcNodeHandel<T>{
	public RpcElement getRpcNodeConfig(Class<?> clazz);
}
