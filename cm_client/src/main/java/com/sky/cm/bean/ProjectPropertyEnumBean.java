package com.sky.cm.bean;

import java.io.Serializable;
import java.util.List;

public class ProjectPropertyEnumBean implements Serializable{

	/***/
	private static final long serialVersionUID = 1L;
	private String groupNo;

    private String groupName;
    //项目
    private String project;
    //项目环境
    private String profile;
    //对应项目版本
    private String versionCode;
	private String local;
	private List<PropertyEnumValueBean> enums;
	public List<PropertyEnumValueBean> getEnums() {
		return enums;
	}
	public void setEnums(List<PropertyEnumValueBean> enums) {
		this.enums = enums;
	}
	public String getLocal() {
		return local;
	}
	public void setLocal(String local) {
		this.local = local;
	}
	public String getGroupNo() {
		return groupNo;
	}
	public void setGroupNo(String groupNo) {
		this.groupNo = groupNo;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
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
	public String getVersionCode() {
		return versionCode;
	}
	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}
}
