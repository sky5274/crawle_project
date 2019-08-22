package com.sky.rpc.config;


import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import com.sky.rpc.regist.RpcConsumerRegistNodeHandel;
import com.sky.rpc.regist.RpcProviderRegistNodeHande;
import com.sky.rpc.util.RpcSpringBeanUtil;

/**
 * rpc 配置服务
 * @author 王帆
 * @date  2019年7月9日 下午4:10:25
 */
public class RpcRegistConfigration implements ImportBeanDefinitionRegistrar{
	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		 // 注册rpc netty  closed listener
		 registBean(ApplicationNettyClosedListener.class,registry);
		 //注册  rpc provider server start listener
		 registBean(ApplicationStartWithProviderServerListener.class,registry);
		 
		 //regist rpc node handel
		 registBean(RpcSpringBeanUtil.class,registry);
		 
		 registBean(RpcConsumerRegistNodeHandel.class,registry);
		 registBean(RpcProviderRegistNodeHande.class,registry);
		 //rpc provider\consumer  regist aciotn
		 RpcReadyRegistInfoConfigration config=new RpcReadyRegistInfoConfigration();
		 config.setRegistry(registry);
		 config.init();
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
}
