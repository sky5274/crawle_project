package com.sky.rpc.config;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.util.StringUtils;
import com.sky.rpc.core.RpcBeanProxyFactory;
import com.sky.rpc.core.RpcElement;
import com.sky.rpc.regist.RpcConsumerNodeHandel;

/**
 * rpc provider regist to zookeeper and rpc consumer proxy regist into spring
 * @author 王帆
 * @date  2019年7月10日 下午5:12:58
 */
@SuppressWarnings("rawtypes")
public class RpcReadyRegistInfoConfigration {
	private Log log=LogFactory.getLog(getClass());
	private BeanDefinitionRegistry registry;
	private Collection<RpcConsumerNodeHandel> rpcConsumerRegistNodHandels=new LinkedList<>();
	
	public void init() {
		String[] beanNames = registry.getBeanDefinitionNames();
		for(String name:beanNames) {
			BeanDefinition beanDefined = registry.getBeanDefinition(name);
			String className=beanDefined.getBeanClassName();
			if(StringUtils.isEmpty(className)) {
				continue;
			}
			try {
				Class<?> clazz = Class.forName(className);
				List<Class<?>> interfaces = getAllInterface(clazz);
				for(Class<?> intf:interfaces) {
					if(intf.getName().equals(RpcConsumerNodeHandel.class.getName())) {
						rpcConsumerRegistNodHandels.add((RpcConsumerNodeHandel) clazz.newInstance());
						break;
					}
				}
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
				log.error(e.getMessage(), e);
			}
		}
		for(String name:beanNames) {
			BeanDefinition beanDefined = registry.getBeanDefinition(name);
			registRpcBean(beanDefined);
		}
	}
	
	private List<Class<?>> getAllInterface(Class<?> clazz) {
		if(clazz.getName().equals(Object.class.getName())){
			return null;
		}
		List<Class<?>> interfaces=new LinkedList<>();
		interfaces.addAll(Arrays.asList(clazz.getInterfaces()));
		List<Class<?>> supintfs = getAllInterface(clazz.getSuperclass());
		if(supintfs!=null && !supintfs.isEmpty()) {
			interfaces.addAll(supintfs);
		}
		return  interfaces;
	}
	
	private void registRpcBean(BeanDefinition beanDefined) {
		if(beanDefined !=null && !StringUtils.isEmpty(beanDefined.getBeanClassName()) && !"NULL".equals(beanDefined.getBeanClassName().toUpperCase())) {
				try {
					Class<?> clazz = Class.forName( beanDefined.getBeanClassName());
					// rpc consumer bean regist into spring
					registRpcConsumerBean(clazz,beanDefined);
				} catch (ClassNotFoundException e) {
					throw new NullPointerException(e.getMessage());
				}
		}
	}
	
	/**
	 * regist the rpc consumer bean
	 * @param clazz
	 * @author 王帆
	 * @date 2019年7月11日 上午8:42:24
	 */
	private void registRpcConsumerBean(Class<?> clazz,BeanDefinition beanDefined) {
		for(RpcConsumerNodeHandel<?> handel:rpcConsumerRegistNodHandels) {
			Map<RpcElement,Class<?>> consumerEles = handel.getRpcNodeConfig(clazz);
			if(consumerEles!=null && !consumerEles.keySet().isEmpty()) {
				Set<RpcElement> eles = consumerEles.keySet();
				for(RpcElement ele:eles) {
					registRpcConsumerBeanClass(consumerEles.get(ele),ele);
				}
			}
		}
	}
	
	/**
	 * 注册 rpc consumer bean
	 * @param clazz
	 * @param ele
	 * @author 王帆
	 * @date 2019年7月10日 下午4:38:03
	 */
	private void registRpcConsumerBeanClass(Class clazz, RpcElement ele) {
		if(ele!=null) {
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
		definition.setLazyInit(false);
		registry.registerBeanDefinition(ele.getId(),definition);
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
