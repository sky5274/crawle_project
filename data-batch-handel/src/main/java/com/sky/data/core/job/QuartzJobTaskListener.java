package com.sky.data.core.job;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.JobListener;
import org.springframework.stereotype.Component;

import com.sky.data.dao.DataBatchTaskMapper;
import com.sky.data.entity.DataBatchTaskEntity;

@Component
public class QuartzJobTaskListener implements JobListener{
	@Resource
	private DataBatchTaskMapper jobTaskMapper;
	private Log log=LogFactory.getLog(getClass());
	@Override
	public String getName() {
		return "Quartz-data-batch-task-listener";
	}

	@Override
	public void jobExecutionVetoed(JobExecutionContext arg0) {
		
	}

	@Override
	public void jobToBeExecuted(JobExecutionContext job) {
		
	}

	@Override
	public void jobWasExecuted(JobExecutionContext job, JobExecutionException exp) {
		JobKey jobKey = job.getTrigger().getJobKey();
		String taskId = jobKey.getName();
		log.info(getName()+"toBe excute job:"+jobKey.toString());
		DataBatchTaskEntity nowtask = jobTaskMapper.selectByTaskId(taskId);
		
		if(exp!=null) {
			log.error(getName()+" has exception:"+exp.getMessage(),exp);
		}
		if(nowtask!=null) {
			DataBatchTaskEntity jobtask = new DataBatchTaskEntity();
			jobtask.setId(nowtask.getId());
			jobtask.setVersion(nowtask.getVersion());
			jobtask.setStatus((byte)2);
			jobtask.setRunTimes(nowtask.getRunTimes()+1);
			if(exp !=null) {
				jobtask.setRunErrTimes(nowtask.getRunErrTimes()+1);
				jobtask.setRunErrMsg(exp.getMessage());
			}
			jobTaskMapper.updateByPrimaryKeySelective(jobtask);
		}
	}

}
