package com.sky.auth.config.entity;

import java.io.Serializable;

/**
 * 权限数据
 * @author 王帆
 * @date  2019年4月15日 下午5:22:07
 */
public class AuthorityBean implements Serializable{

	/***/
	private static final long serialVersionUID = 1L;
	
	private String code;
	private String name;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
