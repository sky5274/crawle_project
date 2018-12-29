package com.crawl.data.config.dao.entity;

import com.sky.pub.BaseTableEntity;

/**
 * 表配置列名
 * @author 王帆
 * @date  2018年12月25日 上午9:55:47
 */
public class ConfigTableColumnEntity extends BaseTableEntity{
   

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer tableId;
	/**表编码（表名）*/
	private String tableCode;
	private String column;

    private String desc;

    private String type;


    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column == null ? null : column.trim();
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

	public String getTableCode() {
		return tableCode;
	}

	public void setTableCode(String tableCode) {
		this.tableCode = tableCode;
	}

	public Integer getTableId() {
		return tableId;
	}

	public void setTableId(Integer tableId) {
		this.tableId = tableId;
	}
}