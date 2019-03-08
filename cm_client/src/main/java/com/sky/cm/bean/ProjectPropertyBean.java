package com.sky.cm.bean;

import java.util.List;

/**
 * 项目属性对象
 * @author 王帆
 * @date  2019年3月8日 下午2:16:49
 */
public class ProjectPropertyBean {
	private String project;
	private String profile;
	private String version;
	private List<PropertyValueBean> properties;
	public String getProject() {
		return project;
	}
	public void setProject(String project) {
		this.project = project;
	}
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public List<PropertyValueBean> getProperties() {
		return properties;
	}
	public void setProperties(List<PropertyValueBean> properties) {
		this.properties = properties;
	}
}
