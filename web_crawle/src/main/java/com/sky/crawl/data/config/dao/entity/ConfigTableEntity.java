package com.sky.crawl.data.config.dao.entity;

import java.util.List;

import org.hibernate.validator.constraints.NotBlank;

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
    
    @NotBlank(message="表名称不能为空")
    private String tableCode;

    private String groupName;

    private String remark;

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

	public List<ConfigTableColumnEntity> getColumns() {
		return columns;
	}

	public void setColumns(List<ConfigTableColumnEntity> columns) {
		this.columns = columns;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}