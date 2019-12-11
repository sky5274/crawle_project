package com.sky.flow.filter;


import com.sky.flow.bean.TaskFlowInfoBean;
import com.sky.flow.bean.TaskFlowNodeBean;
import com.sky.flow.exception.FlowException;


/**
 * 根据任务信息，流程信息,以及流程环节的信息过滤出任务的目标环节
 * @author 王帆
 * @date  2019年6月8日 上午10:00:40
 */
public interface TaskNodeFilter {
	
	/**
	 * 任务流程节点过滤
	 * @param info
	 * @param node
	 * @return
	 * @throws FlowException
	 * @author 王帆
	 * @date 2019年11月28日 下午3:07:29
	 */
	public TaskFlowNodeBean filter(TaskFlowInfoBean info,TaskFlowNodeBean node) throws FlowException;
}
