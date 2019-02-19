package com.sky.task.core;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.JobListener;
import org.springframework.stereotype.Component;

import com.sky.task.dao.JobTaskEntityMapper;
import com.sky.task.entity.JobTaskEntity;

@Component
public class QuartzJobTaskListener implements JobListener{
	@Resource
	private JobTaskEntityMapper jobTaskMapper;
	private JobTaskEntity jobtask;
	private Log log=LogFactory.getLog(getClass());
	@Override
	public String getName() {
		return "Quartz-jobtask-data-listener";
	}

	@Override
	public void jobExecutionVetoed(JobExecutionContext arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void jobToBeExecuted(JobExecutionContext job) {
		JobKey jobKey = job.getTrigger().getJobKey();
		String taskId = jobKey.getName();
		log.info(getName()+"toBe excute job:"+jobKey.toString());
		jobtask = jobTaskMapper.selectByTaskId(taskId);
	}

	@Override
	public void jobWasExecuted(JobExecutionContext job, JobExecutionException exp) {
		JobKey jobKey = job.getTrigger().getJobKey();
		log.info(getName()+"was excuted job:"+jobKey.toString());
		if(exp!=null) {
			log.error(getName()+" has exception:"+exp.getMessage(),exp);
		}
		if(jobtask!=null) {
			jobtask.setRunTimes(jobtask.getRunTimes()+1);
			if(exp !=null) {
				jobtask.setRunErrTimes(jobtask.getRunErrTimes());
				jobtask.setRunErrMsg(exp.getMessage());
			}
			jobTaskMapper.updateByPrimaryKeySelective(jobtask);
		}
	}

}