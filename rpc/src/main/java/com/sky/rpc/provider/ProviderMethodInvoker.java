package com.sky.rpc.provider;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.SocketAddress;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.sky.rpc.base.RpcException;
import com.sky.rpc.base.RpcRequest;
import com.sky.rpc.handle.factory.RpcRequetHandleFactory;
import com.sky.rpc.util.RpcSpringBeanUtil;

/**
 * provider handel the method relection
 * @author 王帆
 * @date  2019年7月12日 下午5:27:46
 */
public class ProviderMethodInvoker {
	private static Log log=LogFactory.getLog(ProviderMethodInvoker.class);
	
	public static Object invoke(SocketAddress addr,RpcRequest request) throws Throwable  {
		try {
			//服务端请求参数预处理
			RpcRequetHandleFactory.preInvoke(request);
			return invokeMethod(addr,Class.forName(request.getClassName()),request);
		} catch (ClassNotFoundException e) {
			throw new RpcException("["+addr+"] server can not find class:"+request.getClassName());
		}
		
	}
	public static Object invoke(SocketAddress addr,Object bean, RpcRequest request) throws Throwable {
		return invokeMethod(addr,bean.getClass(),bean,request);
	}
	
	private static Object invokeMethod(SocketAddress addr,Class<?> serviceClass, RpcRequest request) throws Throwable {
		//获取bean
		Object obj=null;
		try {
			//同class的bean随机获取
			String[] beans = RpcSpringBeanUtil.getApplicationContext().getBeanNamesForType(serviceClass);
			if(beans!=null && beans.length>0) {
				Integer index=0;
				if(beans.length>1) {
					index = Integer.valueOf(Math.random()*beans.length+"");
					if(index>=beans.length) {
						index--;
					}
				}
				obj=RpcSpringBeanUtil.getBean(beans[0]);
			}
		} catch (Exception e) {
		}
		// object is null and object class is interface
		if(obj==null && !serviceClass.isInterface()) {
			obj= serviceClass.newInstance();
		}
		if(obj==null) {
			throw new Exception("无法获取："+serviceClass.getName()+" 的对象");
		}
		return invokeMethod(addr,serviceClass, obj,request);
	}
	
	private static Object invokeMethod(SocketAddress addr,Class<?> serviceClass,Object bean, RpcRequest request) throws Throwable {
		try {
			Method method = bean.getClass().getMethod(request.getMethodName(), request.getParameterTypes());
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
			throw new RpcException("["+addr+"] server can not find method: "+request.getMethodName()+" in class:"+request.getClassName());
		} catch (Throwable e) {
			Throwable ce=e.getCause();
			throw new Throwable("["+addr+"] "+ce.getMessage(), ce);
		}
	}
	
}
