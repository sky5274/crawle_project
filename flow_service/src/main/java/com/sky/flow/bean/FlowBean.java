package com.sky.flow.bean;


import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 流程控制
 * @author 王帆
 * @date  2019年5月16日 下午2:02:01
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class FlowBean extends BaseTableBean{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**流程id*/
	private String id;
	/**流程名称*/
    private String name;
    /**流程起始节点id*/
    private String startId;
    /**流程起始节点名称*/
    private String startName;
    /**流程分组id*/
    private String groupId;
    /**流程分组名称*/
    private String groupName;
    /**流程状态：0，正常；-1作废*/
    private Integer status;
    /**流程节点*/
    private List<FlowNodeContainerBean> nodes;
//	public void setNodes(List<FlowNodeContainerBean> nodes) {
//		if(this.nodes==null) {
//			this.nodes=new LinkedList<>();
//		}
//		for(FlowNodeContainerBean n:nodes) {
//			this.nodes.add(n);
//		}
//	}
}