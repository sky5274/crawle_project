package com.sky.flow.bean;


import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 任务流程详细信息（包含当前任务的流程环节信息）
 * @author 王帆
 * @date  2019年6月30日 下午9:08:47
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class TaskFlowDetailBean extends TaskFlowBean{

	/***/
	private static final long serialVersionUID = 1L;
	
	private FlowNodeContainerBean node;

}
