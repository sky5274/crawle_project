package com.sky.task.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.sky.task.service.JobTaskService;

@Component
public class TaskApplicationReadListener implements ApplicationListener<ApplicationReadyEvent>,ApplicationContextAware{
	ApplicationContext applicationContext;
	private Log log=LogFactory.getLog(getClass());
	
	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		log.info("task schedule application start listener");
		JobTaskService jobtaskService = applicationContext.getBean(JobTaskService.class);
		jobtaskService.autoStartJobTask();
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext=applicationContext;
	}

}
