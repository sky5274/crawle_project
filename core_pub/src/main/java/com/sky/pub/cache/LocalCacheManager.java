package com.sky.pub.cache;

import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.util.StringUtils;
import com.sky.pub.cache.LocalCache.CacheValue;

/**
 * local cache manager to manage dif class
 * @author 王帆
 * @date  2019年8月17日 下午10:07:14
 */
public class LocalCacheManager {
	static ConcurrentHashMap<Class<Object>, LocalCache<Object>> cacheMap=new ConcurrentHashMap<>(16);
	
	public static <T> CacheValue<T> put(String key, T value) {
		return put(key, value, 0);
	}
	@SuppressWarnings("unchecked")
	public static <T> CacheValue<T> put(String key, T value,long timeout) {
		if(value !=null && !StringUtils.isEmpty(key)) {
			Class<? extends Object> clazz = value.getClass();
			LocalCache<T> cache = (LocalCache<T>) cacheMap.get(clazz);
			if(cache==null) {
				cache=LocalCache.createLocalCache();
			}
			CacheValue<T> cacheValue = cache.put(key, value,timeout);
			cacheMap.put((Class<Object>) value.getClass(), (LocalCache<Object> )cache);
			return cacheValue;
		}
		return null;
	}
	
	@SuppressWarnings({ "unchecked", "unused" })
	public static <T> LocalCache<T> getCache(String key) {
		Enumeration<Class<Object>> clazzKeys = cacheMap.keys();
		while(clazzKeys.hasMoreElements()) {
			Class<Object> clazz = clazzKeys.nextElement();
			T obj=null;
			try {
				//类缓存
				obj=(T) clazz.newInstance();
				return (LocalCache<T>)cacheMap.get(obj.getClass());
			} catch (InstantiationException | IllegalAccessException e) {
				try {
					//接口类型的缓存
					Class<?>[] intfs = clazz.getInterfaces();
					for(Class<?> intf:intfs) {
						Class<T> objc =(Class<T>) intf; 
						LocalCache<Object> cache = cacheMap.get(clazz);
						if(cache!=null) {
							return (LocalCache<T> ) cache;
						}
					}
				} catch (Exception e2) {
				}
			}
		}
		return null;
	}
	
	public static <T> boolean containsKey(String key){
		LocalCache<T> cache = getCache(key);
		if(cache!=null) {
			return cache.containsKey(key);
		}
		return false;
	}
	
	public static <T> T get(String key){
		LocalCache<T> cache = getCache(key);
		if(cache!=null) {
			return cache.get(key);
		}
		return null;
	}
}
