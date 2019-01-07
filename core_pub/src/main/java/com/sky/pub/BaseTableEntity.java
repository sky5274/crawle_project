package com.sky.pub;

import java.io.Serializable;
import java.sql.Timestamp;


public class BaseTableEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer version;
	private Integer createid;
	private String createName;
//	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Timestamp  ts;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCreateid() {
		return createid==null?-1:createid;
	}
	public void setCreateid(Integer createid) {
		this.createid = createid;
	}
	public String getCreateName() {
		return createName==null? ((createid==null|| createid!=-1)? "管理员":null):createName;
	}
	public void setCreateName(String createName) {
		this.createName = createName;
	}
	public Timestamp getTs() {
		return ts;
	}
	public void setTs(Timestamp ts) {
		this.ts = ts;
	}
	public void setLongTs(Long ts) {
		this.ts = new Timestamp(ts);
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
}
