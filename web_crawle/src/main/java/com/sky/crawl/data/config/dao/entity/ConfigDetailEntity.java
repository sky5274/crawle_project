package com.crawl.data.config.dao.entity;

import com.sky.pub.BaseTableEntity;

/**
 * 配置细节
 * @author 王帆
 * @date  2018年12月25日 上午10:00:01
 */
public class ConfigDetailEntity extends BaseTableEntity{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1262107644103426637L;

	private Integer pid;

    private String key;

    private String value;

    private String desc;

    private String type;

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc == null ? null : desc.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

}