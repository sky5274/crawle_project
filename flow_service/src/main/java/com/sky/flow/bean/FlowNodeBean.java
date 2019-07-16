package com.sky.flow.bean;


import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 流程节点
 * @author 王帆
 * @date  2019年5月16日 下午2:04:20
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class FlowNodeBean extends BaseTableBean{

	/***/
	private static final long serialVersionUID = 2038475158324085895L;
	/**流程节点id*/
	private String id;
	/**流程节点识别key*/
	private String key;
	/**流程节点名称*/
    private String name;
    /**流程节点类型:start,end,choose,container,base*/
    private String type;
    /**节点状态：0，正常；-1,作废*/
    private Integer status;
    /**流程id*/
    private String flowId;
    /**控制权限编码*/
    private String authCodes;
    
    /**流程链接属性*/
    private List<FlowNodeAttrBean> attrs;
    /**流程链接*/
    private List<FlowNodeLinkBean> links;
    /**流程事件*/
    private List<FlowNodeEventBean> events;

}