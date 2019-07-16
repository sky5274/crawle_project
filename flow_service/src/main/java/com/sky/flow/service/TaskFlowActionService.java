package com.sky.flow.service;


import com.sky.flow.bean.BaseTableBean;
import com.sky.flow.bean.FlowNodeBean;
import com.sky.flow.bean.TaskFlowBean;
import com.sky.flow.bean.TaskFlowNodeBean;
import com.sky.flow.call.TaskFlowCallBack;
import com.sky.flow.exception.FlowException;

/**
 * 	流程任务相关service
 * @author 王帆
 * @date  2019年5月16日 下午3:37:49
 */
public interface TaskFlowActionService {
	
	/**
	 * 提前获取uuid
	 * @return
	 * @author 王帆
	 * @date 2019年5月16日 下午4:19:23
	 */
	public String getPreTaskId();
	
	/**
	 * 开启流程任务
	 * @param task
	 * @param node
	 * @return
	 * @throws FlowException
	 * @author 王帆
	 * @date 2019年5月16日 下午4:20:21
	 */
	public TaskFlowBean startTask(TaskFlowBean task,TaskFlowNodeBean node) throws FlowException;
	
	/**
	 * 回滚流程任务
	 * @param taskId
	 * @return
	 * @throws FlowException
	 * @author 王帆
	 * @date 2019年5月16日 下午4:20:39
	 */
	public Boolean callBackTask(String taskId,BaseTableBean user) throws FlowException;
	
	/**
	 * 根据流程任务以及参数自主执行
	 * @param taskdetail
	 * @param taskId
	 * @param params
	 * @return
	 * @throws FlowException
	 * @author 王帆
	 * @date 2019年5月16日 下午4:21:07
	 */
	public TaskFlowNodeBean addTaskDetail(String taskId,TaskFlowNodeBean taskdetail,Object params) throws FlowException;
	
	/**
	 * 根据用户的推荐的任务流程节点执行流程任务
	 * @param detail
	 * @return
	 * @throws FlowException
	 * @author 王帆
	 * @date 2019年5月16日 下午4:22:41
	 */
	public TaskFlowNodeBean addTaskDetail(TaskFlowNodeBean detail) throws FlowException;
	
	
	public TaskFlowNodeBean addTaskDetail(TaskFlowNodeBean detail,TaskFlowCallBack call) throws FlowException;
	
	/**
	 * 根据流程任务id查询流程任务当前的流程节点
	 * @param taskId
	 * @return
	 * @author 王帆
	 * @throws FlowException 
	 * @date 2019年5月16日 下午4:23:30
	 */
	public FlowNodeBean getNowFlowNode(String taskId) throws FlowException;
	
}
