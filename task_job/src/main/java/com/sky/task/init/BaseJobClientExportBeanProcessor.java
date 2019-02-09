package com.sky.task.init;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.remoting.httpinvoker.HttpInvokerServiceExporter;
import org.springframework.stereotype.Component;
import com.sky.task.core.RpcConfig;
import com.sky.task.job.AbstractBaseJobcClient;

@Component
public class BaseJobClientExportBeanProcessor implements ApplicationContextAware,BeanFactoryPostProcessor{
	ApplicationContext applicationContext;
	DefaultListableBeanFactory beanFactory;
	private Log log=LogFactory.getLog(getClass());
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		this.beanFactory= (DefaultListableBeanFactory) beanFactory;;
		Map<String, AbstractBaseJobcClient> jobClazzMaps = this.applicationContext.getBeansOfType(AbstractBaseJobcClient.class);
		if(jobClazzMaps!=null && !jobClazzMaps.isEmpty()) {
			String port = this.applicationContext.getEnvironment().getProperty("server.port");
			RpcConfig rpcConfig = applicationContext.getBean(RpcConfig.class);

			for(String key:jobClazzMaps.keySet()) {
				try {
					regist(key,jobClazzMaps.get(key),port,rpcConfig);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
			log.debug("get export");
		}

	}

	private void regist(String key, AbstractBaseJobcClient abstractBaseJobcClient, String port, RpcConfig rpcConfig) throws NumberFormatException, Exception {
		//注册httpinvoke  export服务
		String url = registHTTPExport(key,abstractBaseJobcClient);
		//注册  服务客户端
		rpcConfig.regist("/"+abstractBaseJobcClient.getClass().getName(), Integer.parseInt(port), url);
	}

	/**
	 *   	注册httpInvoker SERVICE
	 * @param abstractBaseJobcClient
	 * @return
	 * @author 王帆
	 * @param key 
	 * @date 2019年2月8日 上午11:19:53
	 */
	private String registHTTPExport(String key, AbstractBaseJobcClient abstractBaseJobcClient) {
		String url="/"+key;
		BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(HttpInvokerServiceExporter.class);
		// 设置属性userService,此属性引用已经定义的bean:userService,这里userService已经被spring容器管理了.
		beanDefinitionBuilder.addPropertyValue("service", abstractBaseJobcClient);
		beanDefinitionBuilder.addPropertyValue("serviceInterface", AbstractBaseJobcClient.class);
		AbstractBeanDefinition definition = beanDefinitionBuilder.getRawBeanDefinition();
		definition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_NO);
		// 注册bean
		this.beanFactory.registerBeanDefinition(url,definition);
		return url;
	}


	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext=applicationContext;
	}

}
