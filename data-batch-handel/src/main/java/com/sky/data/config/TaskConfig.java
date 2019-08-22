package com.sky.data.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
public class TaskConfig {
	
	@Bean
	@Primary
	public SchedulerFactoryBean getSchedulerFactoryBean() {
		return new SchedulerFactoryBean();
	}
	
}
