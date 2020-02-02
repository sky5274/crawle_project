package com.sky.cm.bean;

import java.io.Serializable;

public class PropertyEnumValueBean implements Serializable{
	/***/
	private static final long serialVersionUID = 1L;
	private String enumNo;
	private String enumName;
	private String local;
	public String getEnumNo() {
		return enumNo;
	}
	public void setEnumNo(String enumNo) {
		this.enumNo = enumNo;
	}
	public String getEnumName() {
		return enumName;
	}
	public void setEnumName(String enumName) {
		this.enumName = enumName;
	}
	public String getLocal() {
		return local;
	}
	public void setLocal(String local) {
		this.local = local;
	}
}
