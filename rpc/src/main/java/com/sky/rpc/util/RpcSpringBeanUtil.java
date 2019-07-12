package com.sky.rpc.util;

import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;


public class RpcSpringBeanUtil {
	 
    private static ApplicationContext applicationContext = null;
 
    public static void setApplicationContext(ApplicationContext applicationContext){
        if(RpcSpringBeanUtil.applicationContext == null){
        	System.err.println(applicationContext);
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
