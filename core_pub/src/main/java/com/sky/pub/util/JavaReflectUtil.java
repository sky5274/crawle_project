package com.sky.pub.util;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.springframework.util.CollectionUtils;

/**
 * java  reflect util
 * @author wangfan
 * @date 2020年1月21日 上午10:22:44
 */
public class JavaReflectUtil {
	
	/**
	 * 判定是否继承 或实现
	 * @param clazz
	 * @return
	 * @author 王帆
	 * @date 2019年1月18日 下午3:02:49
	 */
	public static boolean isExtrendOrImpl(Class<?> clazz,Class<?> superClass) {
		boolean flag=false;
		List<Class<?>> superClasses = getAllSuperClass(clazz);
		for(Class<?> sup:superClasses) {
			if(sup.getName().equals(superClass.getName())) {
				flag=true;
			}
		}
		Class<?>[] intfaceClass = clazz.getInterfaces();
		if(intfaceClass !=null) {
			for(Class<?> itf:intfaceClass) {
				if(itf.getName().equals(superClass.getName())) {
					flag=true;
				}
			}
		}
		return flag;
	}
	
	/**
	 * 获取类的父级方法
	 * @param clazz
	 * @return
	 */
	public static List<Class<?>> getAllSuperClass(Class<?> clazz) {
		List<Class<?>> supers=new LinkedList<Class<?>>();
		return getAllSuperClass(clazz,supers);
	}
	
	private static List<Class<?>> getAllSuperClass(Class<?> clazz, List<Class<?>> supers) {
		if(!Object.class.getName().equals(clazz.getName())) {
			Class<?> superClass = clazz.getSuperclass();
			supers.add(superClass);
			return getAllSuperClass(superClass,supers);
		}
		return supers;
	}
	
	public static Set<Field> getAllFields(Class<?> clazz) {
		Set<Field> fields=new LinkedHashSet<Field>(Arrays.asList(clazz.getDeclaredFields()));
		List<Class<?>> supperClazzs = getAllSuperClass(clazz);
		if(!CollectionUtils.isEmpty(supperClazzs)) {
			supperClazzs.stream().forEach(c->fields.addAll(getFields(c)));
		}
		return fields;
	}
	public static Set<Field> getFields(Class<?> clazz) {
		return new LinkedHashSet<Field>(Arrays.asList(clazz.getDeclaredFields()));
	}
}
