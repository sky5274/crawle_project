package com.sky.flow.bean;


import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 流程链接
 * @author 王帆
 * @date  2019年5月16日 下午2:12:50
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class FlowNodeLinkBean extends BaseTableBean{
    /** */
	private static final long serialVersionUID = 2904275074693626693L;
	/**链接id*/
	private String id;
	/**链接key*/
    private String key;
    private String flowId;
    /**链接条件*/
    private String condition;
    /**上级节点id*/
    private String upNodeId;
    /**下级节点id*/
    private String downNodeId;
   
}