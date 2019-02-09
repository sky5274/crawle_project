package com.sky.task.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
public class TaskConfig {
	
	@Bean
	public SchedulerFactoryBean getJobConfig() {
		return new SchedulerFactoryBean();
	}
	
}
