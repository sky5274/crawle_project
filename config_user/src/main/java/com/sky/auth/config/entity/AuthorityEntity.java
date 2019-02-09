package com.sky.auth.config.entity;

import org.springframework.security.core.GrantedAuthority;

import com.sky.pub.BaseTableEntity;

/**
 * 	权限对象
 * @author 王帆
 * @date  2019年1月25日 下午12:00:09
 */
public class AuthorityEntity extends BaseTableEntity implements GrantedAuthority{

		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String authcode;
	private String authname;

	public String getAuthcode() {
		return authcode;
	}

	public void setAuthcode(String authcode) {
		this.authcode = authcode;
	}

	public String getAuthname() {
		return authname;
	}

	public void setAuthname(String authname) {
		this.authname = authname;
	}

	public String getAuthority() {
		return authcode;
	}

}
