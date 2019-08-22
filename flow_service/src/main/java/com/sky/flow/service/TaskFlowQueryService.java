package com.sky.flow.service;

import java.util.List;

import com.sky.flow.bean.TaskFlowBean;
import com.sky.flow.bean.TaskFlowDetailBean;
import com.sky.flow.bean.TaskFlowNodeBean;
import com.sky.flow.exception.FlowException;
import com.sky.flow.pub.Page;
import com.sky.flow.pub.PageRequest;

/**
 * 	任务流程信号线服务
 * @author 王帆
 * @date  2019年5月16日 下午2:50:25
 */
public interface TaskFlowQueryService {
	
	/**
	 * 查询流程任务
	 * @param task
	 * @return
	 * @author 王帆
	 * @date 2019年5月16日 下午4:03:30
	 */
	public List<TaskFlowBean> queryTask(TaskFlowBean task);
	
	/**
	 * 分页查询流程任务
	 * @param taskpage
	 * @return
	 * @author 王帆
	 * @date 2019年5月16日 下午4:03:47
	 */
	public Page<TaskFlowBean> queryTaskByPage(PageRequest<TaskFlowBean> taskpage);
	
	/**
	 * 根据任务id查询任务流程的节点
	 * @param taskId
	 * @return
	 * @author 王帆
	 * @date 2019年5月16日 下午4:16:36
	 */
	public List<TaskFlowNodeBean> queryTaskDetails(String  taskId);
	
	/**
	 * 	动态查询节点信息
	 * @param node
	 * @return
	 * @author 王帆
	 * @date 2019年5月16日 下午4:17:02
	 */
	public List<TaskFlowNodeBean> queryTaskDetails(TaskFlowNodeBean node);
	
	/**
	 * 根据renwid查询任务流程的当前的节点
	 * @param taskId
	 * @return
	 * @author 王帆
	 * @date 2019年5月16日 下午4:17:26
	 */
	public TaskFlowNodeBean queryTaskNowNode(String taskId);
	
	/**
	 * 根据流程任务节点id查询节点信息
	 * @param taskId
	 * @param nodeId
	 * @return
	 * @author 王帆
	 * @date 2019年5月16日 下午4:18:29
	 */
	public TaskFlowNodeBean queryTaskNode(String taskId,String nodeId);

	/**
	 * 根据任务id查询流程任务主表信息
	 * @param taskId
	 * @return
	 * @author 王帆
	 * @date 2019年6月1日 下午3:33:37
	 */
	public TaskFlowBean queryTask(String taskId,String opretorCode);
	
	/**
	 * 根据任务id查询流程任务的所有信息
	 * @param taskId
	 * @return
	 * @author 王帆
	 * @throws FlowException 
	 * @date 2019年6月1日 下午3:34:12
	 */
	public TaskFlowDetailBean queryTaskInfo(String taskId ,String opretorCode) throws FlowException;
}
