package com.sky.rpc.base;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.sky.rpc.resource.RpcMethodUtil;

/**
 * rpc client request body
 *<p>Title: RpcRequest.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2017</p>
 * <p>Company: </p>
 * @author sky
 * @date 2018年10月15日
 */

public class RpcRequest extends RpcBaseBean{
	
	/***/
	private static final long serialVersionUID = 1L;
	/**类名*/
	private String className;
	/**方法名*/
	private String methodName;
	/**方法参数*/
	private Object[] args;
	/**方法参数类型*/
	private Class<?>[] parameterTypes;
	private String[] parameterNames;
	private Map<String, String> headers=new HashMap<String, String>();
	public RpcRequest() {}
	public RpcRequest(String className, Method method, Object[] args2)  {
		this(className,method.getName(),RpcMethodUtil.getMethodParameterNames(method),method.getParameterTypes(),args2);
	}
	public RpcRequest(String className, String methodName, String[] parameterNames,Class<?>[] parameterTypes, Object[] args)  {
		super(className+"_"+System.currentTimeMillis());
		this.className=className;
		this.methodName=methodName;
		this.parameterNames=parameterNames;
		this.setParameterTypes(parameterTypes);
		this.args=args;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public Object[] getArgs() {
		return args;
	}
	public void setArgs(Object[] args) {
		this.args = args;
	}
	public Class<?>[] getParameterTypes() {
		return parameterTypes;
	}
	public void setParameterTypes(Class<?>[] parameterTypes) {
		this.parameterTypes = parameterTypes;
	}
	public String[] getParameterNames() {
		return parameterNames;
	}
	public void setParameterNames(String[] parameterNames) {
		this.parameterNames = parameterNames;
	}
	public Map<String, String> getHeaders() {
		return headers;
	}
	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}
	public void put(String key,String value) {
		headers.put(key, value);
	}
}
