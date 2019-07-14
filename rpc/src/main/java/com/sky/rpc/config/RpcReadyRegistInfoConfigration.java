package com.sky.rpc.config;

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
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.util.StringUtils;
import com.sky.rpc.annotation.RpcComsumer;
import com.sky.rpc.annotation.RpcProvider;
import com.sky.rpc.core.RpcBeanProxyFactory;
import com.sky.rpc.core.RpcElement;
import com.sky.rpc.provider.ProviderContant;
import com.sky.rpc.provider.ProviderServer;
import com.sky.rpc.resource.ResouceProperties;
import com.sky.rpc.zk.RpcConfig;

/**
 * rpc provider regist to zookeeper and rpc consumer proxy regist into spring
 * @author 王帆
 * @date  2019年7月10日 下午5:12:58
 */
@SuppressWarnings("rawtypes")
public class RpcReadyRegistInfoConfigration {

	private BeanDefinitionRegistry registry;
	
	public void init() {
		String[] beanNames = registry.getBeanDefinitionNames();
		for(String name:beanNames) {
			BeanDefinition beanDefined = registry.getBeanDefinition(name);
			registRpcBean(beanDefined);
		}
	}
	
	private void registRpcBean(BeanDefinition beanDefined) {
		if(beanDefined !=null && !StringUtils.isEmpty(beanDefined.getBeanClassName()) && !"NULL".equals(beanDefined.getBeanClassName().toUpperCase())) {
				try {
					Class<?> clazz = Class.forName( beanDefined.getBeanClassName());
					//rpc provider info regist
					registRpcProviderNode(clazz);
					// rpc consumer bean regist into spring
					registRpcConsumerBean(clazz);
				} catch (ClassNotFoundException e) {
					throw new NullPointerException(e.getMessage());
				}
		}
	}
	
	/**
	 * 注册rpc provider 信息到zookeeper中
	 * @param clazz
	 * @author 王帆
	 * @date 2019年7月10日 下午5:08:24
	 */
	private void registRpcProviderNode(Class<?> clazz) {
		RpcProvider anno = clazz.getAnnotation(RpcProvider.class);
		if(anno!=null) {
			ProviderContant.setHasProvider(true);
			// get the interfaces from the clazz, and set nodedata into zookeeper with the interface 
			RpcElement ele = getRpcProviderElement(clazz,anno);
			Class<?>[] interfaces = clazz.getInterfaces();
			int port = ProviderServer.getPort();
			//rpc provider class interface info regist into zookeeper
			for(Class<?> intf:interfaces) {
				try {
					RpcConfig.regist(ele.writeUrl(), intf.getName(),clazz.getName(), port);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			//rpc provider class info regist into zookeeper
			try {
				RpcConfig.regist(ele.writeUrl(), clazz.getName(), port);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * regist the rpc consumer bean
	 * @param clazz
	 * @author 王帆
	 * @date 2019年7月11日 上午8:42:24
	 */
	private void registRpcConsumerBean(Class<?> clazz) {
		Map<Class, Set<RpcComsumer>> consumerMap = getAllConsumerAnnotation(clazz);
		Set<Class> clazzes = consumerMap.keySet();
		for(Class c:clazzes) {
			Set<RpcComsumer> consumers = consumerMap.get(c);
			for(RpcComsumer consumer:consumers) {
				registRpcConsumerBeanClass(c,consumer);
			}
		}
	}
	
	/**
	 * 注册 rpc consumer bean
	 * @param clazz
	 * @param consumer
	 * @author 王帆
	 * @date 2019年7月10日 下午4:38:03
	 */
	private void registRpcConsumerBeanClass(Class clazz, RpcComsumer consumer) {
		if(consumer!=null) {
			RpcElement ele = getRpcConsumerElement(clazz,consumer);
			try {
				if(registry.getBeanDefinition(ele.getId())==null) {
					registRpcConsumerBeanOption(clazz,ele);
				}
			} catch (Exception e) {
				registRpcConsumerBeanOption(clazz,ele);
			}
		}
	}
	
	private void registRpcConsumerBeanOption(Class clazz, RpcElement ele) {
		//rpc consemer  不可以设置scope 为   SCOPE_PROTOTYPE
		BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
		GenericBeanDefinition definition = (GenericBeanDefinition) builder.getRawBeanDefinition();
		definition.getPropertyValues().add("interfaceClass", clazz);
		definition.getPropertyValues().add("node", ele);
		definition.setBeanClass(RpcBeanProxyFactory.class);
		definition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
		registry.registerBeanDefinition(ele.getId(),definition);
	}
	
	private static String group;
	private static String version;
	
	private String getRpcGroup() {
		if(group==null) {
			group=ResouceProperties.getProperty("rpc.group");
		}
		return group;
	}

	private String getRpcVersion() {
		if(version==null) {
			version=ResouceProperties.getProperty("rpc.version");
		}
		return version;
	}
	
	/**
	 * 获取rpc consumer 配置数据
	 * @param clazz
	 * @param consumer
	 * @return
	 * @author 王帆
	 * @date 2019年7月10日 下午4:38:28
	 */
	private RpcElement getRpcConsumerElement(Class clazz, RpcComsumer consumer) {
		RpcElement ele=new RpcElement();
		if(!StringUtils.isEmpty(consumer.group())) {
			ele.setGroup(consumer.group());
		}else {
			ele.setGroup(getRpcGroup());
		}
		if(!StringUtils.isEmpty(consumer.version())) {
			ele.setVersion(consumer.version());
		}else {
			ele.setVersion(getRpcVersion());
		}
		boolean flag=consumer.target().length>0;
		if(flag) {
			ele.setGroup(consumer.target()[0].getName());
		}
		ele.setInterfaceName(clazz.getName());
		ele.setId("rpc_"+clazz.getSimpleName()+(flag?"_"+consumer.target()[0].getSimpleName():""));
		ele.setTimeout(consumer.timeout());
		return ele;
	}
	private RpcElement getRpcProviderElement(Class clazz, RpcProvider provider) {
		RpcElement ele=new RpcElement();
		if(!StringUtils.isEmpty(provider.group())) {
			ele.setGroup(provider.group());
		}else {
			ele.setGroup(getRpcGroup());
		}
		if(!StringUtils.isEmpty(provider.version())) {
			ele.setVersion(provider.version());
		}else {
			ele.setVersion(getRpcVersion());
		}
		ele.setInterfaceName(clazz.getName());
		return ele;
	}

	/**
	 * get All consumer annotation with calzz 
	 * @param clazz
	 * @return
	 * @author 王帆
	 * @date 2019年7月11日 上午8:43:04
	 */
	private Map<Class, Set<RpcComsumer>> getAllConsumerAnnotation(Class<?> clazz) {
		Class<?> targetClazz=clazz;
		Map<Class,Set<RpcComsumer>> annoMap=new HashMap<Class,Set<RpcComsumer>>();
		while(!targetClazz.getName().equals(Object.class.getName())) {
			for(Field field:targetClazz.getDeclaredFields()) {
				RpcComsumer anno = field.getAnnotation(RpcComsumer.class);
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
	
	private Map<Class, Set<RpcComsumer>> getRpcConsumerAnnotation(Annotation[][] annotations, Class<?>[] classes, TypeVariable<?>[] typeVariables, Map<Class, Set<RpcComsumer>> annoMap) {
		if(annotations!=null) {
			int  index=0;
			for(Annotation[] annots:annotations) {
				for(Annotation anno:annots) {
					if(anno.getClass().getName().equals(RpcComsumer.class.getName())) {
						Class cls = getRpcClassType(typeVariables[index], classes[index]);
						addClassIntoMap(cls,(RpcComsumer)anno,annoMap);
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
					 System.out.println(type.getName());
					 System.out.println(pType);
					type=Class.forName(pType.getActualTypeArguments()[0].getTypeName());
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
		return type;
	}
	
	
	private void addClassIntoMap(Class<?> clazz, RpcComsumer consumer, Map<Class, Set<RpcComsumer>> annoMap) {
		if(consumer!=null) {
			Set<RpcComsumer> values = annoMap.get(clazz);
			if(values==null) {
				values=new HashSet<>();
			}
			values.add(consumer);
			annoMap.put(clazz, values);
		}
	}

	/**
	 * @return the registry
	 */
	public BeanDefinitionRegistry getRegistry() {
		return registry;
	}

	/**
	 * @param registry the registry to set
	 */
	public void setRegistry(BeanDefinitionRegistry registry) {
		this.registry = registry;
	}
	
}
