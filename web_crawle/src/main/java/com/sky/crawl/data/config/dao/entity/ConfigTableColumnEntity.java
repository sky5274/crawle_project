package com.sky.crawl.data.config.dao.entity;

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

	private String attr;

    private String remark;

    private String type;

    private Integer isNull;
    private Integer isPrimary;
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

	public String getAttr() {
		return attr;
	}

	public void setAttr(String attr) {
		this.attr = attr;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getIsNull() {
		return isNull;
	}

	public void setIsNull(Integer isNull) {
		this.isNull = isNull;
	}

	public Integer getIsPrimary() {
		return isPrimary;
	}

	public void setIsPrimary(Integer isPrimary) {
		this.isPrimary = isPrimary;
	}
}