package com.sky.task.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Service;

import com.sky.pub.Page;
import com.sky.pub.PageRequest;
import com.sky.pub.ResultAssert;
import com.sky.pub.common.exception.ResultException;
import com.sky.pub.util.ComPubUtil;
import com.sky.task.core.QuartzManager;
import com.sky.task.dao.JobTaskEntityMapper;
import com.sky.task.dao.TaskGroupEntityMapper;
import com.sky.task.entity.JobTaskEntity;
import com.sky.task.entity.TaskGroupEntity;
import com.sky.task.service.JobTaskService;

@Service
public class JobTaskServiceImpl implements JobTaskService{
	@Resource
	private JobTaskEntityMapper jobTaskMapper;
	@Resource
	private TaskGroupEntityMapper taskGroupMapper;
	@Resource
	private QuartzManager quartzManager;
	private Log log=LogFactory.getLog(getClass());
	
	@Override
	public JobTaskEntity addJobTask(JobTaskEntity job) throws ResultException {
		job.setTaskId(ComPubUtil.getUuid());
		int size = jobTaskMapper.insertSelective(job);
		ResultAssert.isFalse(size==0, "调度任务创建失败");
		return job;
	}

	@Override
	public boolean modifyJobTask(JobTaskEntity job)throws ResultException  {
		return jobTaskMapper.updateByPrimaryKeySelective(job)>0;
	}
	

	@Override
	public boolean cancelJobTask(JobTaskEntity job) {
		return false;
	}

	@Override
	public TaskGroupEntity addTaskGroup(TaskGroupEntity group) throws ResultException {
		int size = taskGroupMapper.insertSelective(group);
		ResultAssert.isFalse(size==0, "任务分组创建失败");
		return group;
	}

	@Override
	public boolean modifyTaskGroup(TaskGroupEntity group) {
		return taskGroupMapper.updateByPrimaryKeySelective(group)>0;
	}

	@Override
	public boolean delTaskGroup(TaskGroupEntity group) {
		TaskGroupEntity newGroup = new TaskGroupEntity();
		newGroup.setId(group.getId());
		newGroup.setGroupId(group.getGroupId());
		newGroup.setStatus((byte)-1);
		return taskGroupMapper.updateByPrimaryKeySelective(newGroup)>0;
	}

	@Override
	public boolean runJobTask(JobTaskEntity job) {
		try {
			quartzManager.addJob(job);
		} catch (SchedulerException e) {
			log.error("调度任务添加异常",e);
			return false;
		}
		return true;
	}

	@Override
	public boolean pauseJobTask(JobTaskEntity job) {
		try {
			quartzManager.pauseJob(job);
		} catch (Exception e) {
			log.error("调度任务暂停异常",e);
			return false;
		}
		return true;
	}

	@Override
	public boolean closeJobTask(JobTaskEntity job) {
		return quartzManager.removeJob(job);
	}

	@Override
	public List<TaskGroupEntity> queryGroup() {
		return taskGroupMapper.queryAll();
	}

	@Override
	public Page<JobTaskEntity> queryPageByTask(PageRequest<JobTaskEntity> pageJob) {
		pageJob.initPage();
		Page<JobTaskEntity> page=new Page<>();
		page.setList(jobTaskMapper.queryListByParam(pageJob,pageJob.getBean()));
		page.setTotal(jobTaskMapper.accout(pageJob.getBean()));
		page.setPageData(pageJob);
		return page;
	}

	@Override
	public JobTaskEntity getJobTask(Integer id) {
		return jobTaskMapper.selectByPrimaryKey(id);
	}

	@Override
	public JobTaskEntity getJobTask(String taskId) {
		return jobTaskMapper.selectByTaskId(taskId);
	}
	
}
