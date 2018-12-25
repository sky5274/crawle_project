package com.crawl.data.config.dao.entity;

import java.util.List;

import com.sky.pub.BasePageRequest;

/**
 * 表配置主表
 * @author 王帆
 * @date  2018年12月25日 上午9:55:26
 */
public class ConfigTableEntity extends BasePageRequest{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


    private String name;

    private String tableCode;

    private String group;

    private String desc;

    private List<ConfigTableColumnEntity> columns;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getTableCode() {
        return tableCode;
    }

    public void setTableCode(String tableCode) {
        this.tableCode = tableCode == null ? null : tableCode.trim();
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group == null ? null : group.trim();
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc == null ? null : desc.trim();
    }

	public List<ConfigTableColumnEntity> getColumns() {
		return columns;
	}

	public void setColumns(List<ConfigTableColumnEntity> columns) {
		this.columns = columns;
	}
}