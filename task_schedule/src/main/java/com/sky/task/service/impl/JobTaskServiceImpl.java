package com.sky.task.service.impl;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.sky.pub.Page;
import com.sky.pub.ResultAssert;
import com.sky.pub.ResultCode;
import com.sky.pub.common.exception.ResultException;
import com.sky.pub.util.ComPubUtil;
import com.sky.pub.util.ListUtils;
import com.sky.task.core.QuartzManager;
import com.sky.task.core.RpcConfig;
import com.sky.task.core.RpcConfig.NodeData;
import com.sky.task.dao.JobTaskEntityMapper;
import com.sky.task.dao.TaskGroupEntityMapper;
import com.sky.task.entity.JobTaskEntity;
import com.sky.task.entity.TaskGroupEntity;
import com.sky.task.entity.req.JobTaskReqData;
import com.sky.task.service.JobTaskService;

@Service
public class JobTaskServiceImpl implements JobTaskService{
	@Resource
	private JobTaskEntityMapper jobTaskMapper;
	@Resource
	private TaskGroupEntityMapper taskGroupMapper;
	@Resource
	private QuartzManager quartzManager;
	@Resource
	private RpcConfig rpcConfig;
	private Log log=LogFactory.getLog(getClass());

	@Override
	public JobTaskEntity addJobTask(JobTaskEntity job) throws ResultException {
		job.setTaskId(ComPubUtil.getUuid());
		//基础校验
		baseValidJobTask(job);
		int size = jobTaskMapper.insertSelective(job);
		ResultAssert.isTure(size==0, "调度任务创建失败");
		return job;
	}
	
	/**
	 * 基础校验调度任务数据合法性
	 * @param job
	 * @throws ResultException
	 * @author 王帆
	 * @date 2019年2月16日 下午4:23:22
	 */
	private void baseValidJobTask(JobTaskEntity job) throws ResultException {
		if(StringUtils.isEmpty(job.getTaskId())) {
			throw new ResultException(ResultCode.VALID, "任务校验：调度任务缺少任务编码");
		}
		if(StringUtils.isEmpty(job.getGroupId())) {
			throw new ResultException(ResultCode.VALID, "任务校验：调度任务缺少任务分组编码");
		}
		if(job.getRunType() !=null && job.getRunType()==1 && StringUtils.isEmpty(job.getCronExpression())) {
			throw new ResultException(ResultCode.VALID, "任务校验：非单次执行的任务缺少时间表达式");
		}
		if(StringUtils.isEmpty(job.getTargetClass())) {
			throw new ResultException(ResultCode.VALID, "任务校验：调度任务缺失执行任务类");
		}
		if(!StringUtils.isEmpty(job.getJsonParams()) && !isJson(job.getJsonParams())) {
			throw new ResultException(ResultCode.VALID, "任务校验：调度任务传参必须为json");
		}
	}
	
	/**
	 * 判断是否是json格式数据
	 * @param str
	 * @return
	 * @author 王帆
	 * @date 2019年2月16日 下午4:23:51
	 */
	private boolean isJson(String str) {
		try {
			JSON.parse(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean modifyJobTask(JobTaskEntity job)throws ResultException  {
		//基础校验
		baseValidJobTask(job);
		return jobTaskMapper.updateByPrimaryKeySelective(job)>0;
	}


	@Override
	public boolean cancelJobTask(JobTaskEntity job) {
		return false;
	}

	@Override
	public TaskGroupEntity addTaskGroup(TaskGroupEntity group) throws ResultException {
		int size = taskGroupMapper.insertSelective(group);
		ResultAssert.isTure(size==0, "任务分组创建失败");
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
	public boolean runJobTask(JobTaskEntity job) throws ResultException {
		try {
			//基础校验
			baseValidJobTask(job);
			validNowNodeLimit(job);
			quartzManager.addJob(job);
		} catch (SchedulerException e) {
			log.error("调度任务添加异常",e);
			return false;
		}
		return true;
	}
	
	private void validNowNodeLimit(JobTaskEntity job) throws ResultException {
		if(!StringUtils.isEmpty(job.getLimitTargetNode())) {
			Set<String> nodes = getNodeIps(job.getGroupId(),job.getTargetClass());
			Set<String> temp=new HashSet<>();
			for(String ip:nodes) {
				if(job.getLimitTargetNode().contains(ip)) {
					temp.add(ip);
				}
			}
			if(ListUtils.isEmpty(temp)) {
				throw new ResultException(ResultCode.VALID,String.format("系统当前的  %s 对应节点 %s不在限制节点%s 内",job.getTargetClass(),nodes.toString(),job.getLimitTargetNode()));
			}
		}
	}
	
	@Override
	public boolean pauseJobTask(JobTaskEntity job) {
		try {
			setJobTaskStatus(job,1);
			quartzManager.pauseJob(job);
		} catch (Exception e) {
			log.error("调度任务暂停异常",e);
			return false;
		}
		return true;
	}

	@Override
	public boolean closeJobTask(JobTaskEntity job) {
		setJobTaskStatus(job,0);
		return quartzManager.removeJob(job);
	}
	
	/**
	 * 变更调度任务的任务状态
	 * @param job
	 * @param status
	 * @author 王帆
	 * @date 2019年2月16日 下午4:25:10
	 */
	private void setJobTaskStatus(JobTaskEntity job,int status) {
		job.setStatus((byte)status);
		jobTaskMapper.updateByPrimaryKeySelective(job);
	}

	@Override
	public List<TaskGroupEntity> queryGroup() {
		return taskGroupMapper.queryAll();
	}

	@Override
	public Page<JobTaskEntity> queryPageByTask(JobTaskReqData pageJob) {
		pageJob.initPage();
		Page<JobTaskEntity> page=new Page<>();
		page.setList(jobTaskMapper.queryListByParam(pageJob));
		page.setTotal(jobTaskMapper.accout(pageJob));
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

	@Override
	public void autoStartJobTask() {
		JobTaskReqData reqdata=new JobTaskReqData();
		reqdata.setRunType((byte)1);
		List<JobTaskEntity> jobs = jobTaskMapper.queryListByParam(reqdata);
		for(JobTaskEntity job:jobs) {
			try {
				runJobTask(job);
			} catch (ResultException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void autoStopJobTask() {
		Scheduler scheduler = quartzManager.getScheduler();
		try {
			List<JobExecutionContext> jobs = scheduler.getCurrentlyExecutingJobs();
			List<JobTaskEntity> list=new LinkedList<>();
			for(JobExecutionContext job:jobs) {
				JobKey jobkey = job.getJobDetail().getKey();
				JobTaskEntity task = new JobTaskEntity();
				task.setTaskId(jobkey.getName());
				task.setGroupId(jobkey.getGroup());
				list.add(task);
			}
			//更新调度任务为停止状态
			if(!ListUtils.isEmpty(list)) {
				jobTaskMapper.updateTaskToClosed(list);
			}
			//关闭所有调度任务
			quartzManager.shutdownJobs();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public Set<String> getNodeIps(String groupId,String className) {
		List<NodeData> nodes = rpcConfig.getAllNodeDataByClassName(groupId,className);
		Set<String> sets=new HashSet<>();
		for(NodeData node:nodes) {
			sets.add(node.getIp()+":"+node.getPort());
		}
		return sets;
	}

}
