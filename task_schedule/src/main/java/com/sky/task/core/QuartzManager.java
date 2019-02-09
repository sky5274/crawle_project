package com.sky.task.core;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.sky.task.entity.JobTaskEntity;

public class QuartzManager implements ApplicationContextAware{
	private static ApplicationContext appCtx;
	public static SchedulerFactoryBean schedulerFactoryBean = null;


	/**
	 * 	添加任务
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	public static void addJob(JobTaskEntity job) throws SchedulerException {
		if (job == null || !JobTaskEntity.STATUS_RUNNING.equals(job.getStatus())) {
			return;
		}
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		TriggerKey triggerKey = TriggerKey.triggerKey(job.getTaskId(), job.getGroupId());
		CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
		// 不存在，创建一个
		if (null == trigger) {
			Class clazz = JobTaskEntity.CONCURRENT_IS.equals(job.getIsConcurrent()) ? QuartzJobFactory.class
					: QuartzJobFactoryDisallowConcurrentExecution.class;
			JobDataMap newJobDataMap=new JobDataMap();
			newJobDataMap.put("class", job.getTargetClass());
			newJobDataMap.put("params", job.getJsonParams());
			JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(job.getTaskId(), job.getGroupId()).setJobData(newJobDataMap).build();
			jobDetail.getJobDataMap().put("scheduleJob"+job.getGroupId(), job);
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
			trigger = TriggerBuilder
					.newTrigger()
					.withDescription(job.getTaskName().toString())
					.withIdentity(job.getTaskId(), job.getGroupId())
					.withSchedule(scheduleBuilder).build();
			scheduler.scheduleJob(jobDetail, trigger);
		} else {
			// Trigger已存在，那么更新相应的定时设置
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
			// 按新的cronExpression表达式重新构建trigger
			trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).usingJobData("data", job.getJsonParams()).withSchedule(scheduleBuilder).build();
			// 按新的trigger重新设置job执行
			scheduler.rescheduleJob(triggerKey, trigger);
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
	public static boolean removeJob(JobTaskEntity job) {
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
	public static void startJobs() {
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
	public static void shutdownJobs() {
		try {
			Scheduler scheduler = schedulerFactoryBean.getScheduler();
			if (!scheduler.isShutdown()) {
				scheduler.shutdown();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		appCtx=applicationContext;
	}
}
