package com.sky.table.bean;

import java.io.Serializable;
import java.util.Date;

public class ParamBean implements Serializable,Cloneable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String type;
	private String lable;
	private Object value;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public void setParamType(Object obj) {
		if(obj instanceof Integer) {
			this.type="int";
		}else if(obj instanceof Long) {
			this.type="long";
		}else if(obj instanceof Float) {
			this.type="float";
		}else if(obj instanceof Double) {
			this.type="double";
		}else if(obj instanceof Boolean) {
			this.type="boolean";
		}else if(obj instanceof Date) {
			this.type="date";
		}else {
			this.type="string";
		}
	}
	
	 @Override
	public String toString() {
		return value+"("+(type==null?"object":type)+")";
	}
	public boolean equals(ParamBean obj) {
		return obj!=null && this.type!=null && this.type.equals(obj.getValue());
	}
	public String getLable() {
		return lable;
	}
	public void setLable(String lable) {
		this.lable = lable;
	}
	@Override
	public ParamBean clone() throws CloneNotSupportedException{
		Object object = super.clone();
		return (ParamBean) object;
	}
}
