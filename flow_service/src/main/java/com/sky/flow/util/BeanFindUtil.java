package com.sky.flow.util;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.sky.rpc.resource.RpcMethodUtil;

public class BeanFindUtil {
	
	public static class ClassInfo implements Serializable{
		/***/
		private static final long serialVersionUID = 1L;
		private  String name;
		private List<MethodInfo> methods;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public List<MethodInfo> getMethods() {
			return methods;
		}
		public void setMethods(List<MethodInfo> methods) {
			this.methods = methods;
		}
		
	}
	
	public static class MethodInfo implements Serializable{
		/***/
		private static final long serialVersionUID = 1L;
		private String name;
		private String returnType;
		private List<String> paramTypes;
		private List<String> paramNames;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getReturnType() {
			return returnType;
		}
		public void setReturnType(String returnType) {
			this.returnType = returnType;
		}
		public List<String> getParamTypes() {
			return paramTypes;
		}
		public void setParamTypes(List<String> paramTypes) {
			this.paramTypes = paramTypes;
		}
		public List<String> getParamNames() {
			return paramNames;
		}
		public void setParamNames(List<String> paramNames) {
			this.paramNames = paramNames;
		}
	}
	
	public static ClassInfo getClassInfo(String clazzName) {
		try {
			Class<?> clazz = Class.forName(clazzName);
			ClassInfo classinfo =new ClassInfo();
			classinfo.setName(clazz.getName());
			List<MethodInfo> methods=new LinkedList<>();
			for(Method m:clazz.getDeclaredMethods()) {
				methods.add(getMedhodInfo(m));
			}
			classinfo.setMethods(methods);
			return  classinfo;
		} catch (ClassNotFoundException e) {
		}
		return null;
	}
	public static List<MethodInfo> getMethodInfo(String clazzName,String method) {
		try {
			Class<?> clazz = Class.forName(clazzName);
			ClassInfo classinfo =new ClassInfo();
			classinfo.setName(clazz.getName());
			List<MethodInfo> methods=new LinkedList<>();
			for(Method m:clazz.getDeclaredMethods()) {
				if(m.getName().equals(method)) {
					methods.add(getMedhodInfo(m));
				}
			}
			return methods;
		} catch (ClassNotFoundException e) {
		}
		
		return null;
	}
	
	
	private static MethodInfo getMedhodInfo(Method m) {
		MethodInfo mif=new MethodInfo();
		mif.setName(m.getName());
		mif.setReturnType(m.getReturnType().getName());
		List<String> paramnames=new LinkedList<>();
		for(Class<?> p:m.getParameterTypes()) {
			paramnames.add(p.getName());
		}
		mif.setParamNames(Arrays.asList(RpcMethodUtil.getMethodParameterNames(m)));
		mif.setParamTypes(paramnames);
		return mif;
	}
}
