package com.sky.task.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

import com.sky.task.service.JobTaskService;

@Component
public class TaskApplicationClosedListener implements ApplicationListener<ContextClosedEvent>,ApplicationContextAware{
	ApplicationContext applicationContext;
	private Log log=LogFactory.getLog(getClass());
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext=applicationContext;
	}

	@Override
	public void onApplicationEvent(ContextClosedEvent event) {
		log.info("task schedule application context close listener");
		JobTaskService jobtaskService = applicationContext.getBean(JobTaskService.class);
		jobtaskService.autoStopJobTask();;
	}

}
