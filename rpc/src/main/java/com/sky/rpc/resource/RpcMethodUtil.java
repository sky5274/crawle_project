package com.sky.rpc.resource;

import java.lang.reflect.Method;

import org.springframework.core.LocalVariableTableParameterNameDiscoverer;

public class RpcMethodUtil {
	static LocalVariableTableParameterNameDiscoverer ParameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
		
	public static String[] getMethodParameterNames(Method method) {
		return ParameterNameDiscoverer.getParameterNames(method);
	}
}
