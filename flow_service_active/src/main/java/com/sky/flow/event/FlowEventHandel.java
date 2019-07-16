package com.sky.flow.event;

import com.sky.flow.bean.TaskFlowNodeBean;

public interface FlowEventHandel {
	
	public void invoke(TaskFlowNodeBean node);
}
