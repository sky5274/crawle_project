package com.sky.flow.bean;

import java.io.Serializable;

import lombok.Data;

@Data
public class TaskFlowInfoBean implements Serializable{
	/***/
	private static final long serialVersionUID = 1L;
	
	private TaskFlowBean task;
	
	private FlowBean flow;
	
	private TaskFlowNodeBean nowNode;

}
