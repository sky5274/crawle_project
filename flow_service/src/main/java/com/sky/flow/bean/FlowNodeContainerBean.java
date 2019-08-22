package com.sky.flow.bean;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class FlowNodeContainerBean extends FlowNodeBean{
	/***/
	private static final long serialVersionUID = 1L;
	
	private String containerId;
	/**节点容器*/
	private List<FlowNodeContainerBean> container;
	/**下一环节*/
	private List<FlowNodeContainerBean> next;
	
}
