package com.sky.auth.config.entity.req;

import java.util.List;

import com.sky.auth.config.entity.RoleEntity;

public class RoleRequestEntity extends RoleEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userName;
	private String userId;
	private List<String> authorityCodes;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public List<String> getAuthorityCodes() {
		return authorityCodes;
	}
	public void setAuthorityCodes(List<String> authorityCodes) {
		this.authorityCodes = authorityCodes;
	}
	
}
