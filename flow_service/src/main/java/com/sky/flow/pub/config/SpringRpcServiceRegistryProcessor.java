package com.sky.flow.pub.config;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import com.sky.flow.rpc.regist.ApplicationServiceRegistScanner;
import com.sky.flow.service.TaskFlowActionService;
import com.sky.rpc.config.ApplicationNettyClosedListener;


/**
 * 自动添加rpc  service接口实现
 * @author 王帆
 * @date  2019年6月7日 下午11:45:48
 */
@Configuration
public class SpringRpcServiceRegistryProcessor implements BeanDefinitionRegistryPostProcessor,ApplicationContextAware, ResourceLoaderAware{
	ApplicationContext applicationContext;
	ResourceLoader resourceLoader;
	Log log=LogFactory.getLog(getClass());
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext=applicationContext;	
	}

	

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		
	}

	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
		try {
			if(applicationContext.getBean(TaskFlowActionService.class)==null) {
				registService(registry);
			}
		} catch (Exception e) {
			registService(registry);
		}
		
		try {
			if(applicationContext.getBean(ApplicationNettyClosedListener.class)==null) {
				registBean(ApplicationNettyClosedListener.class,registry);
			}
		} catch (Exception e) {
			registBean(ApplicationNettyClosedListener.class,registry);
		}
	}
	
	private GenericBeanDefinition registBean( Class<?> intertface, BeanDefinitionRegistry registry) {
		return registBean(intertface.getSimpleName(),intertface,registry);
	}
	private GenericBeanDefinition registBean(String id, Class<?> intertface, BeanDefinitionRegistry registry) {
		BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(intertface);
		GenericBeanDefinition definition = (GenericBeanDefinition) builder.getRawBeanDefinition();
		definition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
		registry.registerBeanDefinition(id,definition);
		return definition;
	}
	
	/**
	 * 注册服务
	 * @param registry
	 * @author 王帆
	 * @date 2019年7月9日 上午10:53:18
	 */
	private void registService(BeanDefinitionRegistry registry) {
		try {
			ApplicationServiceRegistScanner applicationServiceRegistScanner=new ApplicationServiceRegistScanner(resourceLoader,registry);
			applicationServiceRegistScanner.regist(applicationContext.getResources("classpath*:com/sky/flow/service/*.class"));
		} catch (IOException | ClassNotFoundException e) {
			throw new IllegalAccessError(e.getMessage());
		}
	}

	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader=resourceLoader;
		
	}

}
