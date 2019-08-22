package com.sky.data.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.sky.data.service.DataBatchTaskService;

@Component
public class TaskApplicationReadListener implements ApplicationListener<ApplicationReadyEvent>,ApplicationContextAware{
	ApplicationContext applicationContext;
	private Log log=LogFactory.getLog(getClass());
	
	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		log.info("task schedule application start listener");
		DataBatchTaskService jobtaskService = applicationContext.getBean(DataBatchTaskService.class);
		jobtaskService.autoStartDataTask();
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext=applicationContext;
	}

}
