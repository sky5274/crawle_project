package com.sky.flow.filter;


import com.sky.flow.bean.TaskFlowInfoBean;
import com.sky.flow.bean.TaskFlowNodeBean;
import com.sky.flow.exception.FlowException;


/**
 * 根据任务信息，流程信息,以及下一环节的信息过滤出任务的下一环节
 * @author 王帆
 * @date  2019年6月8日 上午10:00:40
 */
public interface TaskNodeFilter {
	
	public TaskFlowNodeBean filter(TaskFlowInfoBean info,TaskFlowNodeBean node) throws FlowException;
}
