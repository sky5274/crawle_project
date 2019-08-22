package com.sky.task.service;

import java.util.List;
import java.util.Set;

import com.sky.pub.Page;
import com.sky.pub.common.exception.ResultException;
import com.sky.task.entity.JobTaskEntity;
import com.sky.task.entity.TaskGroupEntity;
import com.sky.task.entity.req.JobTaskReqData;

/**
 * 		调度任务服务
 * @author 王帆
 * @date  2019年2月9日 下午1:27:50
 */
public interface JobTaskService {
	
	/**
	 * 	添加调度任务配置信息
	 * @param job
	 * @return
	 * @author 王帆
	 * @throws ResultException 
	 * @date 2019年2月9日 下午2:47:52
	 */
	public JobTaskEntity addJobTask(JobTaskEntity job) throws ResultException;
	
	/**
	 * 	修改调度任务信息
	 * @param job
	 * @return
	 * @author 王帆
	 * @date 2019年2月9日 下午2:48:26
	 */
	public boolean modifyJobTask(JobTaskEntity job) throws ResultException;
	/**
	 *   	作废调度任务
	 * @param job
	 * @return
	 * @author 王帆
	 * @date 2019年2月9日 下午2:48:48
	 */
	public boolean cancelJobTask(JobTaskEntity job);
	
	public TaskGroupEntity addTaskGroup(TaskGroupEntity group) throws ResultException;
	public boolean modifyTaskGroup(TaskGroupEntity group) throws ResultException;
	public boolean delTaskGroup(TaskGroupEntity group);
	
	/**
	 * 	运行任务调度
	 * @param job
	 * @return
	 * @author 王帆
	 * @throws ResultException 
	 * @date 2019年2月9日 下午2:46:48
	 */
	public boolean runJobTask(JobTaskEntity job) throws ResultException;
	
	/**
	 * 	暂停调度任务
	 * @param job
	 * @return
	 * @author 王帆
	 * @date 2019年2月9日 下午2:47:03
	 */
	public boolean pauseJobTask(JobTaskEntity job);
	
	/**
	 * 	关闭调度任务
	 * @param job
	 * @return
	 * @author 王帆
	 * @date 2019年2月9日 下午2:47:32
	 */
	public boolean closeJobTask(JobTaskEntity job);
	
	/**
	 * 	查询所有分组
	 * @return
	 * @author 王帆
	 * @date 2019年2月9日 下午2:44:21
	 */
	public List<TaskGroupEntity> queryGroup();
	
	/**
	 *	 分页查询调度任务
	 * @param jobPage
	 * @return
	 * @author 王帆
	 * @date 2019年2月9日 下午2:46:12
	 */
	public Page<JobTaskEntity> queryPageByTask(JobTaskReqData jobPage);
	
	/**
	 * 	根据主键id查询调度任务
	 * @param id
	 * @return
	 * @author 王帆
	 * @date 2019年2月16日 上午9:44:27
	 */
	public JobTaskEntity getJobTask(Integer id);
	
	/**
	 * 	根据任务id查询调度任务
	 * @param taskId
	 * 
	 * @return
	 * @author 王帆
	 * @date 2019年2月16日 上午9:45:03
	 */
	public JobTaskEntity getJobTask(String taskId);
	
	/**
	 * 	自动执行系统定义的调度任务
	 * 
	 * @author 王帆
	 * @date 2019年2月16日 上午9:49:50
	 */
	public void autoStartJobTask();
	
	/**
	 *	 自动更新调度任务的停止状态
	 * 
	 * @author 王帆
	 * @date 2019年2月16日 上午10:01:42
	 */
	public void autoStopJobTask();

	/**
	 * 根据类名获取节点的ip+port
	 * @param className
	 * @return
	 * @author 王帆
	 * @date 2019年2月17日 下午2:03:31
	 */
	public Set<String> getNodeIps(String groupId,String className);
}
