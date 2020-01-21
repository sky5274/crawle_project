package com.sky.flow.rpc.regist;

import java.io.IOException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import com.sky.flow.rpc.proxy.FlowServceProxyFactory;
import com.sky.rpc.base.RpcIp;
import com.sky.rpc.core.RpcTypeContant;
import com.sky.rpc.provider.ProviderContant;

/**
 * service application scanner regist
 * @author 王帆
 * @date  2019年7月9日 上午11:05:53
 */
public class ApplicationServiceRegistScanner {
	private Log log=LogFactory.getLog(getClass());
	ResourceLoader resourceLoader;
	BeanDefinitionRegistry register;

	public ApplicationServiceRegistScanner(ResourceLoader resourceLoader2, BeanDefinitionRegistry registry) {
		this.register=registry;
		this.resourceLoader=resourceLoader2;
	}

	public void regist(Resource[] resources) throws IOException, ClassNotFoundException {
		
		@SuppressWarnings("rawtypes")
		FlowServceProxyFactory proxy=new FlowServceProxyFactory();
        RpcIp server = proxy.getDefServer();
    	log.info("regist flow service >> url: "+server.getHost()+":"+server.getPort());
         MetadataReaderFactory metaReader = new CachingMetadataReaderFactory(resourceLoader);
         if(resources.length>0) {
        	 ProviderContant.setHasProvider(true);
         }
         for (Resource r : resources) {
             MetadataReader reader = metaReader.getMetadataReader(r);
             registBean(Class.forName(reader.getClassMetadata().getClassName()),r);
         }
    }
    
    private void registBean(Class<?> intertface,Resource resource) {
    	BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(intertface);
		GenericBeanDefinition definition = (GenericBeanDefinition) builder.getRawBeanDefinition();
		definition.getPropertyValues().add("interfaceClass", intertface);

		definition.setBeanClassName(FlowServceProxyFactory.class.getName());
		definition.setResource(resource);
		definition.setAutowireCandidate(true);
		definition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
		BeanDefinitionHolder definitionHolder=new BeanDefinitionHolder(definition,intertface.getSimpleName(),new String[] {intertface.getName(),"flow_"+intertface.getName()}) ;
		BeanDefinitionReaderUtils.registerBeanDefinition(definitionHolder, register);
	}

	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader=resourceLoader;
	}

	public BeanDefinitionRegistry getRegister() {
		return register;
	}

	public void setRegister(BeanDefinitionRegistry register) {
		this.register = register;
	}
	
}
