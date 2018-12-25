package com.sky.pub;

import java.io.Serializable;
import java.util.Date;

public class BaseTableEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer createid;
	private Integer createName;
	private Date ts;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCreateid() {
		return createid;
	}
	public void setCreateid(Integer createid) {
		this.createid = createid;
	}
	public Integer getCreateName() {
		return createName;
	}
	public void setCreateName(Integer createName) {
		this.createName = createName;
	}
	public Date getTs() {
		return ts;
	}
	public void setTs(Date ts) {
		this.ts = ts;
	}
}
