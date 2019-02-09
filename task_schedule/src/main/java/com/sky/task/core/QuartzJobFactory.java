package com.sky.task.core;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class QuartzJobFactory implements Job{

	@Override
	public void execute(JobExecutionContext args) throws JobExecutionException {
		JobDataMap jobData = args.getMergedJobDataMap();
		String targetClass = jobData.getString("class");
		String params = jobData.getString("params");
	}
	
}
