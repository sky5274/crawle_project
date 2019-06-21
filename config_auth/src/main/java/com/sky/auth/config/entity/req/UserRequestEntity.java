package com.sky.auth.config.entity.req;

import java.util.List;

import com.sky.auth.config.entity.UserEntity;

/**
 * 用户请求对象
 * @author 王帆
 * @date  2019年4月9日 下午4:00:00
 */
public class UserRequestEntity extends UserEntity{

	/***/
	private static final long serialVersionUID = 5590571255566239602L;
	
	private Integer noMatch;
	private String newPassword;
	private List<String> roleCodes;

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public List<String> getRoleCodes() {
		return roleCodes;
	}

	public void setRoleCodes(List<String> roleCodes) {
		this.roleCodes = roleCodes;
	}

	public Integer getNoMatch() {
		return noMatch;
	}

	public void setNoMatch(Integer noMatch) {
		this.noMatch = noMatch;
	}

}
