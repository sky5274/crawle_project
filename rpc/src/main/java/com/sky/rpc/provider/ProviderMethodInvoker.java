package com.sky.rpc.provider;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.SocketAddress;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sky.rpc.base.RpcException;
import com.sky.rpc.base.RpcRequest;

public class ProviderMethodInvoker {
	private static Log log=LogFactory.getLog(ProviderMethodInvoker.class);
	
	public static Object invoke(SocketAddress addr,RpcRequest request) throws Throwable  {
		try {
			return invokeMethod(addr,Class.forName(request.getClassName()),request);
		} catch (ClassNotFoundException e) {
			throw new RpcException("["+addr+"] server can not find class:"+request.getClassName());
		}
		
	}
	public static Object invoke(SocketAddress addr,Object bean, RpcRequest request) throws Throwable {
		return invokeMethod(addr,bean.getClass(),bean,request);
	}
	
	private static Object invokeMethod(SocketAddress addr,Class<?> serviceClass, RpcRequest request) throws Throwable {
		return invokeMethod(addr,serviceClass, serviceClass.newInstance(),request);
	}
	private static Object invokeMethod(SocketAddress addr,Class<?> serviceClass,Object bean, RpcRequest request) throws Throwable {
		try {
			Method method = serviceClass.getMethod(request.getMethodName(), request.getParameterTypes());
			method.setAccessible(true);
			long starttime = System.currentTimeMillis();
			Object result=null;
			if(Modifier.isStatic(method.getModifiers())) {
				//静态方法调用
				result = method.invoke(null, request.getArgs());
			}else {
				result = method.invoke(bean, request.getArgs());
			}
			long endtime = System.currentTimeMillis();
			log.info(String.format(serviceClass.getName()+"."+method.getName()+" cost time: %dms",(endtime-starttime)));
			return result;
		} catch (NoSuchMethodException |SecurityException e) {
			e.printStackTrace();
			throw new RpcException("["+addr+"] server can not find method: "+request.getMethodName()+" in class:"+request.getClassName());
		} catch (Throwable e) {
			Throwable ce=e.getCause();
			throw new Throwable("["+addr+"] "+ce.getMessage(), ce);
		}
	}
	
}
