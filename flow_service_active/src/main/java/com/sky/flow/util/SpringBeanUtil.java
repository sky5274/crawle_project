package com.sky.flow.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;

@Configuration 
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SpringBeanUtil implements ApplicationContextAware{
	 
    private static ApplicationContext applicationContext = null;
 
    public void setApplicationContext(ApplicationContext applicationContext){
        if(SpringBeanUtil.applicationContext == null){
        	SpringBeanUtil.applicationContext  = applicationContext;
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
