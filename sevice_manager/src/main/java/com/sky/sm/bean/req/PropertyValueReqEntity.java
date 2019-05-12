package com.sky.sm.bean.req;

import com.sky.sm.bean.PropertyValueEntity;

/**
 * 	属性查询（分页）条件
 * @author 王帆
 * @date  2019年3月7日 下午2:36:11
 */
public class PropertyValueReqEntity extends PropertyValueEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String keyLike;
	private String projectLike;
	private String profileLike;
	private String versionCodeLike;
	public String getKeyLike() {
		return keyLike;
	}
	public void setKeyLike(String keyLike) {
		this.keyLike = keyLike;
	}
	public String getProjectLike() {
		return projectLike;
	}
	public void setProjectLike(String projectLike) {
		this.projectLike = projectLike;
	}
	public String getProfileLike() {
		return profileLike;
	}
	public void setProfileLike(String profileLike) {
		this.profileLike = profileLike;
	}
	public String getVersionCodeLike() {
		return versionCodeLike;
	}
	public void setVersionCodeLike(String versionCodeLike) {
		this.versionCodeLike = versionCodeLike;
	}
	
}
