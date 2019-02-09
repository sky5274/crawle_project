package com.sky.task.service;

import java.util.List;

import com.sky.pub.Page;
import com.sky.task.entity.JobTaskEntity;
import com.sky.task.entity.TaskGroupEntity;

/**
 * 		调度任务服务
 * @author 王帆
 * @date  2019年2月9日 下午1:27:50
 */
public interface JobTaskService {
	
	public JobTaskEntity addJobTask(JobTaskEntity job);
	public JobTaskEntity modifyJobTask(JobTaskEntity job);
	public boolean cancelJobTask(JobTaskEntity job);
	
	public boolean runJobTask(JobTaskEntity job);
	public boolean pauseJobTask(JobTaskEntity job);
	public boolean coloseJobTask(JobTaskEntity job);
	
	public List<TaskGroupEntity> queryGroup();
}
