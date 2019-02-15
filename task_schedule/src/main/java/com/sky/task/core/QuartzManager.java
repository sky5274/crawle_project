package com.sky.task.core;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Matcher;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.KeyMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import com.sky.task.entity.JobTaskEntity;

/**
 * 	quartz  JOB manager
 * @author 王帆
 * @date  2019年2月9日 下午4:14:54
 */
@Component
public class QuartzManager {
	@Autowired
	public  SchedulerFactoryBean schedulerFactoryBean ;
	@Autowired
	private QuartzJobTaskListener quartzJobTaskListener;


	/**
	 * 	添加任务
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	public  void addJob(JobTaskEntity job) throws SchedulerException {
		if (job == null) {
			return;
		}
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		TriggerKey triggerKey = TriggerKey.triggerKey(job.getTaskId(), job.getGroupId());
		Trigger trigger =  scheduler.getTrigger(triggerKey);
		// 不存在，创建一个
		if (null == trigger) {
			Class<? extends Job> clazz = JobTaskEntity.CONCURRENT_IS.equals(job.getIsConcurrent()) ? QuartzJobFactory.class
					: QuartzJobFactoryDisallowConcurrentExecution.class;
			JobDataMap newJobDataMap=new JobDataMap();
			newJobDataMap.put("taskId", job.getTaskId());
			newJobDataMap.put("class", job.getTargetClass());
			newJobDataMap.put("params", job.getJsonParams());
			newJobDataMap.put("scheduleModel", job.getScheduleModel());
			JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(job.getTaskId(), job.getGroupId()).setJobData(newJobDataMap).build();
			jobDetail.getJobDataMap().put("scheduleJob"+job.getGroupId(), job);
			if(job.getRunType()==null || job.getRunType()!=1 ||StringUtils.isEmpty(job.getCronExpression())) {
				trigger = TriggerBuilder
						.newTrigger()
						.withDescription(job.getTaskName())
						.withIdentity(job.getTaskId(), job.getGroupId())
						.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(3).withRepeatCount(0))
						.build();
			}else {
				trigger = TriggerBuilder
						.newTrigger()
						.withDescription(job.getTaskName())
						.withIdentity(job.getTaskId(), job.getGroupId())
						.withSchedule( CronScheduleBuilder.cronSchedule(job.getCronExpression()))
						.build();
			}
			//自定义的调度任务监听器
			if(quartzJobTaskListener !=null) {
				Matcher<JobKey> matcher = KeyMatcher.keyEquals(jobDetail.getKey());
				scheduler.getListenerManager().addJobListener(quartzJobTaskListener,matcher);
			}
			scheduler.scheduleJob(jobDetail, trigger);
		} else {
			CronTrigger crontrigger=(CronTrigger) trigger;
			// Trigger已存在，那么更新相应的定时设置
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
			// 按新的cronExpression表达式重新构建trigger
			crontrigger = crontrigger.getTriggerBuilder().withIdentity(triggerKey).usingJobData("data", job.getJsonParams()).withSchedule(scheduleBuilder).build();
			// 按新的trigger重新设置job执行
			scheduler.rescheduleJob(triggerKey, crontrigger);
		}
		if(!scheduler.isShutdown()) {
			scheduler.start();
		}
	}

	/**
	 * 	暂停定时任务
	 * @param job
	 * @author 王帆
	 * @date 2019年2月7日 下午8:30:27
	 */
	public void pauseJob(JobTaskEntity job) {
		try {
			Scheduler scheduler = schedulerFactoryBean.getScheduler();
			JobKey jobkey=new JobKey(job.getTaskId(), job.getGroupId());
			scheduler.pauseJob(jobkey);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @Description: 移除一个任务(使用默认的任务组名，触发器名，触发器组名)
	 * @param job
	 * @return 
	 * 
	 * @Title: QuartzManager.java
	 */
	public  boolean removeJob(JobTaskEntity job) {
		try {
			Scheduler scheduler = schedulerFactoryBean.getScheduler();
			TriggerKey triggerKey = TriggerKey.triggerKey(job.getTaskId(), job.getGroupId());
			JobKey jobkey=new JobKey(job.getTaskId(), job.getGroupId());
			scheduler.pauseJob(jobkey);
			scheduler.unscheduleJob(triggerKey);
			return scheduler.deleteJob(jobkey);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}



	/**
	 * @Description:启动所有定时任务
	 */
	public  void startJobs() {
		try {
			Scheduler scheduler = schedulerFactoryBean.getScheduler();
			scheduler.start();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @Description:关闭所有定时任务
	 */
	public  void shutdownJobs() {
		try {
			Scheduler scheduler = schedulerFactoryBean.getScheduler();
			if (!scheduler.isShutdown()) {
				scheduler.shutdown();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
