package com.sky.rpc.config;


import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * rpc 配置服务注册
 * @author 王帆
 * @date  2019年7月9日 下午4:10:25
 */
public class RpcRegistConfigration implements ImportBeanDefinitionRegistrar{
	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		
		//regist the rpc class config scanner and do scan the component
		ApplicationRpcScannerRegister registScanner=new ApplicationRpcScannerRegister(registry);
		registScanner.scan("com.sky.rpc");
		
		//rpc provider\consumer  regist aciotn
		RpcReadyRegistInfoConfigration config=new RpcReadyRegistInfoConfigration();
		config.setRegistry(registry);
		config.init();
	}
}
