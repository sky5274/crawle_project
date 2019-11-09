package com.sky.cm.config;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;

/**
 * 包扫描注册scanner
 * @author 王帆
 * @date  2019年11月9日 下午9:17:04
 */
public class ConfigClassPathMapperScanner extends ClassPathBeanDefinitionScanner{

	public ConfigClassPathMapperScanner(BeanDefinitionRegistry registry) {
		super(registry);
	}

}
