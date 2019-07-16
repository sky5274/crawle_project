package com.sky.flow.event;

import com.sky.flow.bean.TaskFlowNodeBean;
import com.sky.flow.exception.FlowException;

public interface FlowEventExcuteHandel {
	
	public void excute(TaskFlowNodeBean node) throws FlowException;
}
