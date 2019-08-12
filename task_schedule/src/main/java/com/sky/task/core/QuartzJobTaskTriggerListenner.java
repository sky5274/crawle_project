package com.sky.task.core;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Trigger;
import org.quartz.Trigger.CompletedExecutionInstruction;
import org.quartz.TriggerListener;
import org.springframework.stereotype.Component;

import com.sky.task.dao.JobTaskEntityMapper;
import com.sky.task.entity.JobTaskEntity;

@Component
public class QuartzJobTaskTriggerListenner implements TriggerListener{
	@Resource
	private JobTaskEntityMapper jobTaskMapper;
	private Log log=LogFactory.getLog(getClass());
	
	@Override
	public String getName() {
		return "Quartz-jobtask-trigger-listener ";
	}

	
	/**
	 * (1)Trigger被激发 它关联的job即将被运行
	 */
	@Override
	public void triggerFired(Trigger arg0, JobExecutionContext arg1) {
		
	}
	
	/**
	 * (2)Trigger被激发 它关联的job即将被运行,先执行(1)，在执行(2) 如果返回TRUE 那么任务job会被终止
	 */
	@Override
	public boolean vetoJobExecution(Trigger arg0, JobExecutionContext job) {
		
		return false;
	}

	/**
	 * 3) 当Trigger错过被激发时执行,比如当前时间有很多触发器都需要执行，但是线程池中的有效线程都在工作，
     *  那么有的触发器就有可能超时，错过这一轮的触发。
	 */
	@Override
	public void triggerMisfired(Trigger arg0) {
		
	}

	@Override
	public void triggerComplete(Trigger arg0, JobExecutionContext job, CompletedExecutionInstruction arg2) {
		JobKey jobKey = job.getTrigger().getJobKey();
		String taskId = jobKey.getName();
		log.info(getName()+" triggerCompletejob:"+jobKey.toString());
		JobTaskEntity nowTask = jobTaskMapper.selectByTaskId(taskId);
		if(nowTask!=null) {
			JobTaskEntity jobtask=new JobTaskEntity();
			jobtask.setId(nowTask.getId());
			jobtask.setVersion(nowTask.getVersion());
			jobtask.setStatus((byte)0);
			jobTaskMapper.updateByPrimaryKeySelective(jobtask);
		}
	}
	

}
