package com.sky.flow.bean;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 流程属性
 * @author wangfan
 * @date 2021年1月2日 下午7:49:04
 */
@Data
public class FlowNodeAttrBean implements Serializable{
	/***/
	private static final long serialVersionUID = 1L;
	/**属性id*/
    private String id;
    /**属性key*/
    private String key;
    /**属性名称*/
    private String name;
    /**属性值*/
    private String value;
    /**流程id*/
    private String flowId;
    /**流程节点id*/
    private String nodeId;
    /**版本号*/
    private Integer version;
    /**时间戳*/
    private Date ts;
}