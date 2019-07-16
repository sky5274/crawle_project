package com.sky.flow.event;

import com.sky.flow.bean.FlowNodeEventBean;
import com.sky.flow.bean.TaskFlowBean;
import com.sky.flow.bean.TaskFlowNodeBean;
import com.sky.flow.exception.FlowException;

/**
 * 流程事件引擎执行接口
 * @author 王帆
 * @date  2019年6月7日 下午5:24:53
 */
public interface FlowEventEngineHandel {
	public String type();
	
	/**执行任务*/
	public void invoke(FlowNodeEventBean event,TaskFlowNodeBean node,TaskFlowBean task) throws FlowException;
}
