package com.sky.crawl.data.config.dao.entity;


import com.sky.pub.BaseTableEntity;

/**
 * 爬虫url过滤规则
 * @author 王帆
 * @date  2019年1月17日 上午9:59:09
 */
public class CrawlerConfigFilterEntity extends BaseTableEntity{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	private String configCode;
	
	private String name;

    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

	public String getConfigCode() {
		return configCode;
	}

	public void setConfigCode(String configCode) {
		this.configCode = configCode;
	}

}