package com.sky.cm.config;

import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.alibaba.fastjson.JSON;
import com.sky.cm.core.SkyConfig;

/**
 * spring web 接口注册服务监听
 * @author 王帆
 * @date  2019年3月3日 下午7:52:21
 */
@Component
public class ApplicationRequestMappingRegistListener  implements ApplicationListener<ApplicationReadyEvent>,ApplicationContextAware{
	ApplicationContext applicationContext;
	Log log=LogFactory.getLog(getClass());
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext=applicationContext;
	}

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		SkyConfig config = this.applicationContext.getBean(SkyConfig.class);
		log.info("project load ");
		
		RequestMappingHandlerMapping requestMappingHandlerMapping =this.applicationContext.getBean(RequestMappingHandlerMapping.class);
		
		Map<RequestMappingInfo, HandlerMethod> mappers = requestMappingHandlerMapping.getHandlerMethods();
		config.registProject(mappers);
		System.err.println(JSON.toJSONString(config.getProperties()));
	}

}
