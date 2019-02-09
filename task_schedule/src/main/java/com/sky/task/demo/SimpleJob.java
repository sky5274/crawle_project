package com.sky.task.demo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.sky.task.job.AbstractBaseJobcClient;

@Component
public class SimpleJob implements AbstractBaseJobcClient{
	private Log log=LogFactory.getLog(getClass());
	@Override
	public Boolean excute(String param) {
		log.debug("simple task add");
		log.debug("params is :"+param);
		return true;
	}

}
