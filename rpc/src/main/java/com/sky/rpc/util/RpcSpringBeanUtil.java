package com.sky.rpc.util;

import java.util.Collection;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;


@Component
public class RpcSpringBeanUtil implements ApplicationContextAware{

	private static ApplicationContext applicationContext = null;

	public static void setApplication(ApplicationContext applicationContext){
		if(RpcSpringBeanUtil.applicationContext==null) {
			RpcSpringBeanUtil.applicationContext  = applicationContext;
		}
	}
	public void setApplicationContext(ApplicationContext applicationContext){
		if(RpcSpringBeanUtil.applicationContext==null) {
			RpcSpringBeanUtil.applicationContext  = applicationContext;
		}
	}

	//获取applicationContext
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	//通过name获取 Bean.
	public static Object getBean(String name){
		return getApplicationContext().getBean(name);
	}

	//通过class获取Bean.
	public static <T> T getBean(Class<T> clazz){
		return getApplicationContext().getBean(clazz);
	}
	public static <T> Map<String, T> getBeansByType(Class<T> clazz){
		return getApplicationContext().getBeansOfType(clazz);
	}
	public static <T> Collection<T> getBeans(Class<T> clazz){
		return getBeansByType(clazz).values();
	}

	//通过name,以及Clazz返回指定的Bean
	public static <T> T getBean(String name,Class<T> clazz){
		return getApplicationContext().getBean(name, clazz);
	}

	public static Environment getEvn() {
		return getApplicationContext().getEnvironment();
	}
	public static String getEvnProperty(String key) {
		return getEvn().getProperty(key);
	}
	public static String getEvnProperty(String key,String defaultValue) {
		return getEvn().getProperty(key,defaultValue);
	}
	public static <T> T getEvnProperty(String key,Class<T> targetType) {
		return getEvn().getProperty(key,targetType);
	}
	public static <T> T getEvnProperty(String key,Class<T> targetType,T defaultValue) {
		return getEvn().getProperty(key,targetType,defaultValue);
	}
}
