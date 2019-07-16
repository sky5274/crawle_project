package com.sky.flow.service.impl;

import com.sky.flow.bean.TaskFlowNodeBean;
import com.sky.flow.exception.FlowException;
import com.sky.flow.pub.TaskContants;

/**
 * 任务流程节点 事件
 * @author 王帆
 * @date  2019年6月2日 下午4:27:46
 */
public abstract class AbstratTaskFlowNodeAction {
	/**
	 * 添加节点前
	 * @param node
	 * @throws FlowException
	 * @author 王帆
	 * @date 2019年6月2日 下午4:35:45
	 */
	public abstract void preAddNode(TaskFlowNodeBean node) throws FlowException;
	
	/**
	 * 添加节点过程中
	 * @param node
	 * @return
	 * @throws FlowException
	 * @author 王帆
	 * @date 2019年6月2日 下午4:36:06
	 */
	public abstract TaskFlowNodeBean arroundAddNode(TaskFlowNodeBean node) throws FlowException;
	
	/**
	 * 添加节点完成后
	 * @param node
	 * @throws FlowException
	 * @author 王帆
	 * @date 2019年6月2日 下午4:36:58
	 */
	public abstract void afterAddNode(TaskFlowNodeBean node) throws FlowException;
	
	public TaskFlowNodeBean addNode(TaskFlowNodeBean node) throws FlowException{
		if(node.getParam() !=null) {
			Integer status = node.getParam().getInteger(TaskContants.TASK_STATUS);
			if(status==null) {
				node.putParams(TaskContants.TASK_STATUS, 0);
			}
		}
		preAddNode(node);
		node=arroundAddNode(node);
		afterAddNode(node);
		return node;
	}
	
}
