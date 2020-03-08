package com.sky.sm.bean;

import com.sky.pub.BaseTableEntity;

/**
 * 	字典枚举配置表对象
 * @author 王帆
 * @date  2019年3月7日 下午1:47:19
 */
public class PropertyEnumEntity extends BaseTableEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    private String groupNo;

    private String groupName;
    //项目
    private String project;
    //项目环境
    private String profile;
    //对应项目版本
    private String versionCode;
   
    public String getProject() {
        return project;
    }
    public void setProject(String project) {
        this.project = project == null ? null : project.trim();
    }
    public String getProfile() {
        return profile;
    }
    public void setProfile(String profile) {
        this.profile = profile == null ? null : profile.trim();
    }
    public String getVersionCode() {
        return versionCode;
    }
    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode == null ? null : versionCode.trim();
    }
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getGroupNo() {
		return groupNo;
	}
	public void setGroupNo(String groupNo) {
		this.groupNo = groupNo;
	}
    
}