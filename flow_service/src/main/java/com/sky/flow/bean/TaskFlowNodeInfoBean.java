package com.sky.flow.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class TaskFlowNodeInfoBean extends TaskFlowInfoBean{

	/***/
	private static final long serialVersionUID = -7937299907919967356L;
	
	private TaskFlowNodeBean activeNode;
	private FlowNodeBean flowNode;
}
