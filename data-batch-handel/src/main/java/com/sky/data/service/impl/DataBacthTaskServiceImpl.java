package com.sky.data.service.impl;

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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.sky.pub.Page;
import com.sky.pub.ResultAssert;
import com.sky.pub.ResultCode;
import com.sky.pub.common.exception.ResultException;
import com.sky.pub.util.ComPubUtil;
import com.sky.pub.util.ListUtils;
import com.sky.rpc.zk.RpcConfig;
import com.sky.rpc.zk.RpcConfig.nodeData;
import com.sky.data.dao.DataBatchTaskMapper;
import com.sky.data.core.job.QuartzManager;
import com.sky.data.dao.BatchTaskGroupMapper;
import com.sky.data.entity.DataBatchTaskEntity;
import com.sky.data.entity.BatchTaskGroupEntity;
import com.sky.data.entity.req.DataBatchReqData;
import com.sky.data.service.DataBatchTaskService;

@Service
@Transactional(rollbackFor=Exception.class)
public class DataBacthTaskServiceImpl implements DataBatchTaskService{
	@Resource
	private DataBatchTaskMapper	dataTaskMapper;
	@Resource
	private BatchTaskGroupMapper taskGroupMapper;
	@Resource
	private QuartzManager quartzManager;
	private Log log=LogFactory.getLog(getClass());

	@Override
	public DataBatchTaskEntity addDataTask(DataBatchTaskEntity task) throws ResultException {
		if(StringUtils.isEmpty(task.getTaskId())) {
			task.setTaskId(ComPubUtil.getUuid());
		}
		//基础校验
		baseValidDataTask(task);
		int size = dataTaskMapper.insertSelective(task);
		if(task.getExt()!=null) {
			dataTaskMapper.insertTaskExt(task.getExt());
		}
		ResultAssert.isTure(size==0, "批处理数据任务创建失败");
		return task;
	}
	
	/**
	 * 基础校验批处理数据任务数据合法性
	 * @param task
	 * @throws ResultException
	 * @author 王帆
	 * @date 2019年2月16日 下午4:23:22
	 */
	private void baseValidDataTask(DataBatchTaskEntity task) throws ResultException {
		if(StringUtils.isEmpty(task.getTaskId())) {
			throw new ResultException(ResultCode.VALID, "任务校验：批处理数据任务缺少任务编码");
		}
		if(StringUtils.isEmpty(task.getGroupId())) {
			throw new ResultException(ResultCode.VALID, "任务校验：批处理数据任务缺少任务分组编码");
		}
		if(task.getRunType() !=null && task.getRunType()==1 && StringUtils.isEmpty(task.getCronExpression())) {
			throw new ResultException(ResultCode.VALID, "任务校验：非单次执行的任务缺少时间表达式");
		}
	}
	
	/**
	 * 判断是否是json格式数据
	 * @param str
	 * @return
	 * @author 王帆
	 * @date 2019年2月16日 下午4:23:51
	 */
	@SuppressWarnings("unused")
	private boolean isJson(String str) {
		try {
			JSON.parse(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean modifyDataTask(DataBatchTaskEntity task)throws ResultException  {
		//基础校验
		baseValidDataTask(task);
		int size=0;
		if(task.getExt()!=null) {
			size=dataTaskMapper.updateTaskExt(task.getExt());
		}
		return size>0 && dataTaskMapper.updateByPrimaryKeySelective(task)>0;
	}


	@Override
	public boolean cancelDataTask(DataBatchTaskEntity task) {
		return false;
	}

	@Override
	public BatchTaskGroupEntity addTaskGroup(BatchTaskGroupEntity group) throws ResultException {
		int size = taskGroupMapper.insertSelective(group);
		ResultAssert.isTure(size==0, "任务分组创建失败");
		return group;
	}

	@Override
	public boolean modifyTaskGroup(BatchTaskGroupEntity group) {
		return taskGroupMapper.updateByPrimaryKeySelective(group)>0;
	}

	@Override
	public boolean delTaskGroup(BatchTaskGroupEntity group) {
		BatchTaskGroupEntity newGroup = new BatchTaskGroupEntity();
		newGroup.setId(group.getId());
		newGroup.setGroupId(group.getGroupId());
		newGroup.setStatus((byte)-1);
		return taskGroupMapper.updateByPrimaryKeySelective(newGroup)>0;
	}

	@Override
	public boolean runDataTask(DataBatchTaskEntity task) throws ResultException {
		try {
			//基础校验
			baseValidDataTask(task);
			validNowNodeLimit(task);
			quartzManager.addJob(task);
		} catch (SchedulerException e) {
			log.error("批处理数据任务添加异常",e);
			return false;
		}
		return true;
	}
	
	private void validNowNodeLimit(DataBatchTaskEntity task) throws ResultException {
//		if(!StringUtils.isEmpty(task.getLimitTargetNode())) {
//			Set<String> nodes = getNodeIps(task.getGroupId(),task.getTargetClass());
//			Set<String> temp=new HashSet<>();
//			for(String ip:nodes) {
//				if(task.getLimitTargetNode().contains(ip)) {
//					temp.add(ip);
//				}
//			}
//			if(ListUtils.isEmpty(temp)) {
//				throw new ResultException(ResultCode.VALID,String.format("系统当前的  %s 对应节点 %s不在限制节点%s 内",task.getTargetClass(),nodes.toString(),task.getLimitTargetNode()));
//			}
//		}
	}
	
	@Override
	public boolean pauseDataTask(DataBatchTaskEntity task) {
		try {
			setDataTaskStatus(task,1);
			quartzManager.pauseJob(task);
		} catch (Exception e) {
			log.error("批处理数据任务暂停异常",e);
			return false;
		}
		return true;
	}

	@Override
	public boolean closeDataTask(DataBatchTaskEntity task) {
		setDataTaskStatus(task,0);
		return quartzManager.removeJob(task);
	}
	
	/**
	 * 变更批处理数据任务的任务状态
	 * @param task
	 * @param status
	 * @author 王帆
	 * @date 2019年2月16日 下午4:25:10
	 */
	private void setDataTaskStatus(DataBatchTaskEntity task,int status) {
		task.setStatus((byte)status);
		dataTaskMapper.updateByPrimaryKeySelective(task);
	}

	@Override
	public List<BatchTaskGroupEntity> queryGroup() {
		return taskGroupMapper.queryAll();
	}

	@Override
	public Page<DataBatchTaskEntity> queryPageByTask(DataBatchReqData pageJob) {
		pageJob.initPage();
		Page<DataBatchTaskEntity> page=new Page<>();
		page.setList(dataTaskMapper.queryListByParam(pageJob));
		page.setTotal(dataTaskMapper.accout(pageJob));
		page.setPageData(pageJob);
		return page;
	}

	@Override
	public DataBatchTaskEntity getDataTask(Integer id) {
		return dataTaskMapper.selectByPrimaryKey(id);
	}

	@Override
	public DataBatchTaskEntity getDataTask(String taskId) {
		return dataTaskMapper.selectByTaskId(taskId);
	}

	@Override
	public void autoStartDataTask() {
		DataBatchReqData reqdata=new DataBatchReqData();
		reqdata.setRunType((byte)1);
		List<DataBatchTaskEntity> tasks = dataTaskMapper.queryListByParam(reqdata);
		for(DataBatchTaskEntity task:tasks) {
			try {
				runDataTask(task);
			} catch (ResultException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void autoStopDataTask() {
		Scheduler scheduler = quartzManager.getScheduler();
		try {
			List<JobExecutionContext> tasks = scheduler.getCurrentlyExecutingJobs();
			List<DataBatchTaskEntity> list=new LinkedList<>();
			for(JobExecutionContext task:tasks) {
				JobKey taskkey = task.getJobDetail().getKey();
				DataBatchTaskEntity job = new DataBatchTaskEntity();
				job.setTaskId(taskkey.getName());
				job.setGroupId(taskkey.getGroup());
				list.add(job);
			}
			//更新批处理数据任务为停止状态
			if(!ListUtils.isEmpty(list)) {
				dataTaskMapper.updateTaskToClosed(list);
			}
			//关闭所有批处理数据任务
			quartzManager.shutdownJobs();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public Set<String> getNodeIps(String groupId,String className) {
		List<nodeData> nodes = RpcConfig.getRcpNodeDatas(groupId+"/"+className);
		Set<String> sets=new HashSet<>();
		for(nodeData node:nodes) {
			sets.add(node.getIp()+":"+node.getPort());
		}
		return sets;
	}

}
