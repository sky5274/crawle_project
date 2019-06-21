package com.sky.auth.config.entity;

import java.io.Serializable;
import java.util.Set;

public class AuthorityContainerBean implements Serializable{

	/***/
	private static final long serialVersionUID = 1L;
	
	private Set<AuthorityBean> auths;
	private Set<AuthorityBean> roles;
	public Set<AuthorityBean> getAuths() {
		return auths;
	}
	public void setAuths(Set<AuthorityBean> auths) {
		this.auths = auths;
	}
	public Set<AuthorityBean> getRoles() {
		return roles;
	}
	public void setRoles(Set<AuthorityBean> roles) {
		this.roles = roles;
	}
	
}
