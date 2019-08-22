package com.sky.auth.config.entity;

import java.util.List;
import java.util.Set;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 	用户的信息与权限
 * @author 王帆
 * @date  2019年1月25日 下午12:02:36
 */
public class UserDetailEntitiy extends UserEntity implements UserDetails{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private  boolean enabled=true;
	private  boolean accountNonExpired=true;
	private  boolean credentialsNonExpired=true;
	private  boolean accountNonLocked=true;
	private Set<GrantedAuthority> authorities;
	private Set<AuthorityEntity> permissions;
	private List<String> roles;
	private AuthTokenDefined token;
	public List<String> getRoles() {
		return roles;
	}
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}
	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}
	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}
	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}
	public Set<GrantedAuthority> getAuthorities() {
		return authorities;
	}
	public void setAuthorities(Set<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}
	public AuthTokenDefined getToken() {
		return token;
	}
	public void setToken(AuthTokenDefined token) {
		this.token = token;
	}
	public Set<AuthorityEntity> getPermissions() {
		return permissions;
	}
	public void setPermissions(Set<AuthorityEntity> permissions) {
		this.permissions = permissions;
	}
	
}
