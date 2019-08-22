package com.sky.rpc.regist;

import java.util.Map;

import com.sky.rpc.core.RpcElement;

public interface RpcConsumerNodeHandel<T> extends RpcNodeHandel<T>{
	public Map<RpcElement,Class<?>> getRpcNodeConfig(Class<?> clazz);
}
