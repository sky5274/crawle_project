package com.sky.crawl.data.config.dao.entity;

import com.sky.pub.BaseTableEntity;

/**
 * 配置对象
 * @author 王帆
 * @date  2018年12月25日 上午10:01:26
 */
public class ConfigEntity extends BaseTableEntity{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;

    private String desc;

    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
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