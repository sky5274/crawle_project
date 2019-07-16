package com.sky.flow.bean;


import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 流程分组
 * @author 王帆
 * @date  2019年5月16日 下午2:03:18
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class FlowGroupBean extends BaseTableBean{
    /***/
	private static final long serialVersionUID = 1L;
	 /**分组id*/
	private String id;
	/**分组编码*/
	private String code;
	 /**分组名称*/
    private String name;
}