package com.sky.sm.bean;

import com.sky.pub.BaseTableEntity;

/**
 * 	属性配置表对象
 * @author 王帆
 * @date  2019年3月7日 下午1:47:19
 */
public class PropertyValueEntity extends BaseTableEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    private String key;

    private String value;

    private String project;

    private String profile;

    private String versionCode;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key == null ? null : key.trim();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

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
}