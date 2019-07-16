package com.sky.flow.bean;


import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 节点事件
 * @author 王帆
 * @date  2019年5月16日 下午2:13:18
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class FlowNodeEventBean extends BaseTableBean{
    /***/
	private static final long serialVersionUID = -2033573622207327463L;
	/**事件id*/
	private String id;
	private String flowId;
	private String nodeId;
	/**事件key*/
    private String key;
    /**事件名称*/
    private String name;
    /**事件排序*/
    private Integer order;
    /**事件类型：in-进入;out-离开节点;user-用户事件;back-回滚事件;其他自定义事件*/
    private String type;
    /**事件执行类型：js/class/method*/
    private String eventType;
    /**事件执行内容*/
    private String content;

}