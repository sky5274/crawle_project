package com.sky.rpc.config;


import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import com.sky.rpc.annotation.EnableRpcRegistable;

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
		Map<String, Object> attrs = importingClassMetadata.getAnnotationAttributes(EnableRpcRegistable.class.getName());
		Object vals=attrs.get("value");
		Set<String> packegs=new HashSet<String>();
		packegs.add("com.sky.rpc");
		if( vals !=null ) {
			String [] li=(String[]) vals;
			if(li.length>0) {
				packegs.addAll(Arrays.asList(li));
			}
		}
		registScanner.scan(packegs.toArray(new String[packegs.size()]));
		
		//rpc provider\consumer  regist aciotn
		RpcReadyRegistInfoConfigration config=new RpcReadyRegistInfoConfigration();
		config.setRegistry(registry);
		config.init();
	}
}
