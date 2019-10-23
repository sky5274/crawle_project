package com.sky.transaction.util;

import java.util.HashMap;
import java.util.Map;
import org.springframework.core.NamedThreadLocal;

public class ThreadCacheUtil {
	private volatile static ThreadLocal<Map<String,Object>> ThreadCacheHolder =new NamedThreadLocal<Map<String,Object>>("Thread mt transaction resource");
	private volatile static ThreadLocal<Boolean> TransationThreadHolder =new NamedThreadLocal<Boolean>("Thread mt transaction handle");
	
	public static void put(String key,Object value) {
		Map<String, Object> resource = getResource();
		if(resource==null) {
			resource=new HashMap<String, Object>();
		}
		resource.put(key, value);
		bindResource(resource);
	}
	
	public static Object get(String key) {
		Map<String, Object> resource = getResource();
		if(resource==null) {
			return null;
		}
		return resource.get(key);
	}
	@SuppressWarnings("unchecked")
	public static <T> T get(String key,Class<T> clazz) {
		Object res=get(key);
		if(res==null) {
			return null;
		}
		return (T) res;
	}
	
	public static void bindResource(Map<String,Object> resource) {
		ThreadCacheHolder.set(resource);
	}
	
	public static Map<String, Object> getResource() {
		return ThreadCacheHolder.get();
	}
	
	public static void remove() {
		ThreadCacheHolder.remove();
		TransationThreadHolder.remove();
	}
	
	public static void setParentThreadFlag(boolean flag) {
		TransationThreadHolder.set(flag);
	}
	public static boolean getParentThreadFlag() {
		Boolean flag = TransationThreadHolder.get();
		if(flag==null) {
			return false;
		}
		return flag;
	}
}
