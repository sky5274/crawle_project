package com.sky.cm.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * config value configration regist start
 * @author 王帆
 * @date  2019年11月9日 下午9:08:08
 */
public class ConfigValueRegistStart implements ImportBeanDefinitionRegistrar{
	private Log log=LogFactory.getLog(getClass());
	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		//扫描com.sky包文件实现自动配置
		ConfigClassPathMapperScanner scanner=new ConfigClassPathMapperScanner(registry);
		log.debug("limit-value config register start");
		scanner.scan("com.sky");
	}

}
