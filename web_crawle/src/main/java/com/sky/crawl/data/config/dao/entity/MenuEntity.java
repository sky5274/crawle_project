package com.crawl.data.config.dao.entity;

import com.sky.pub.BaseTableEntity;

/**
 * 菜单对象
 * @author 王帆
 * @date  2018年12月25日 上午10:03:10
 */
public class MenuEntity extends BaseTableEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    private String code;
    
    private String name;

    private Integer pid;

    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }


	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}