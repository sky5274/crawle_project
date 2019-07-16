package com.sky.flow.bean;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class BaseTableBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**创建人编码*/
	private String createCode;
	/**创建人名称*/
    private String createName;
    /**版本*/
    private Integer version;
    /**时间戳*/
    private Date ts;
}
