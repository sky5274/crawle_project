package com.sky.auth.config.entity;

import java.util.List;

import com.sky.pub.BaseTableEntity;

/**
 * 	角色定义
 * @author 王帆
 * @date  2019年1月25日 下午2:15:42
 */
public class RoleEntity extends BaseTableEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String code;
	private String name;
	private List<AuthorityEntity> auths;
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
	public List<AuthorityEntity> getAuths() {
		return auths;
	}
	public void setAuths(List<AuthorityEntity> auths) {
		this.auths = auths;
	}
}
