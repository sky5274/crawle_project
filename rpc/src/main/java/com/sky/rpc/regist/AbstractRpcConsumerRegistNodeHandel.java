package com.sky.rpc.regist;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import com.sky.rpc.annotation.RpcComsumer;
import com.sky.rpc.core.RpcElement;

@SuppressWarnings("rawtypes")
public abstract class AbstractRpcConsumerRegistNodeHandel<T extends Annotation> implements RpcConsumerNodeHandel<T>{


	@Override
	public Map<RpcElement, Class<?>> getRpcNodeConfig(Class<?> clazz) {
		Map<Class, Set<T>> consumerMap = getAllConsumerAnnotation(clazz);
		Set<Class> clazzes = consumerMap.keySet();
		if(clazzes.isEmpty()) {
			return null;
		}
		 Map<RpcElement, Class<?>> eleMap=new HashMap<>();
		for(Class c:clazzes) {
			Set<T> consumers = consumerMap.get(c);
			for(T consumer:consumers) {
				RpcElement ele = getRpcConsumerElement(c,consumer);
				if(ele!=null) {
					eleMap.put(ele,c);
				}
			}
		}
		return eleMap;
	}
	
	/**
	 * get All consumer annotation with calzz 
	 * @param clazz
	 * @return
	 * @author 王帆
	 * @date 2019年7月11日 上午8:43:04
	 */
	private Map<Class, Set<T>> getAllConsumerAnnotation(Class<?> clazz) {
		Class<?> targetClazz=clazz;
		Map<Class,Set<T>> annoMap=new HashMap<Class,Set<T>>();
		while(!targetClazz.getName().equals(Object.class.getName())) {
			for(Field field:targetClazz.getDeclaredFields()) {
				T anno = field.getAnnotation(getAnnotation());
				if(anno!=null) {
					Class cls = getRpcClassType(field.getGenericType(),field.getType());
					addClassIntoMap(cls,anno,annoMap);
				}
			}
			for(Method method:targetClazz.getDeclaredMethods()) {
				annoMap=getRpcConsumerAnnotation(method.getParameterAnnotations(),method.getParameterTypes(),method.getTypeParameters(),annoMap);
			}
			for(Constructor<?> conts:targetClazz.getDeclaredConstructors()) {
				annoMap=getRpcConsumerAnnotation(conts.getParameterAnnotations(),conts.getParameterTypes(),conts.getTypeParameters(),annoMap);
			}
			targetClazz=targetClazz.getSuperclass();
		}
		return annoMap;
	}
	
	@SuppressWarnings("unchecked")
	private Map<Class, Set<T>> getRpcConsumerAnnotation(Annotation[][] annotations, Class<?>[] classes, TypeVariable<?>[] typeVariables, Map<Class, Set<T>> annoMap) {
		if(annotations!=null) {
			int  index=0;
			for(Annotation[] annots:annotations) {
				for(Annotation anno:annots) {
					if(anno.getClass().getName().equals(RpcComsumer.class.getName())) {
						Class cls = getRpcClassType(typeVariables[index], classes[index]);
						addClassIntoMap(cls,(T)anno,annoMap);
					}
				}
				index++;
			}
		}
		return annoMap;
	}
	
	private boolean isCollection(Class<?> clazz) {
		Class<?>[] interfaces = clazz.getInterfaces();
		for(Class<?> c:interfaces) {
			if(c.getName().equals(Collection.class.getName())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * get rpc class last clazz
	 * @param typefiled
	 * @param type
	 * @return
	 * @author 王帆
	 * @date 2019年7月11日 上午8:43:40
	 */
	private Class getRpcClassType(Type typefiled, Class<?> type) {
		//field 为数组
		if(type.isArray()) {
			try {
				type=Class.forName(typefiled.getTypeName().replaceAll("\\[\\]", ""));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//field 为集合
		if(isCollection(type)) {
			if(typefiled instanceof ParameterizedType) {
				 ParameterizedType pType = (ParameterizedType)typefiled;
				 try {
					type=Class.forName(pType.getActualTypeArguments()[0].getTypeName());
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
		return type;
	}
	
	
	private void addClassIntoMap(Class clazz, T consumer, Map<Class, Set<T>> annoMap) {
		if(consumer!=null) {
			Set<T> values = annoMap.get(clazz);
			if(values==null) {
				values=new HashSet<>();
			}
			values.add(consumer);
			annoMap.put(clazz, values);
		}
	}

	
	/**
	 * 获取rpc consumer 配置数据
	 * @param clazz
	 * @param consumer
	 * @return
	 * @author 王帆
	 * @date 2019年7月10日 下午4:38:28
	 */
	public abstract RpcElement getRpcConsumerElement(Class<?> clazz, T consumer);

}
