package com.sky.auth.config.entity;

import java.io.Serializable;

public class AuthTokenDefined implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//{"access_token":"0fbcbcb2-1c24-44b9-89b2-81a67698db1c","token_type":"bearer","refresh_token":"9db8ae33-3d31-4db6-8388-a7bc83a463bf","expires_in":28578,"scope":"all read writer"}
	private String access_token;
	private String token_type;
	private String refresh_token;
	private String scope;
	private Integer expires_in;

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public String getToken_type() {
		return token_type;
	}

	public void setToken_type(String token_type) {
		this.token_type = token_type;
	}

	public String getRefresh_token() {
		return refresh_token;
	}

	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}

	public Integer getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(Integer expires_in) {
		this.expires_in = expires_in;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getScope() {
		return scope;
	}
	
}
