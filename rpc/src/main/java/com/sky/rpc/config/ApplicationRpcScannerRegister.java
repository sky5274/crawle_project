package com.sky.rpc.config;


import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;

/**
 * rpc conponent scanner register
 * @author 王帆
 * @date  2020年1月16日 下午9:25:32
 */
public class ApplicationRpcScannerRegister extends ClassPathBeanDefinitionScanner{
	public ApplicationRpcScannerRegister(BeanDefinitionRegistry registry) {
		super(registry);
	}
}
