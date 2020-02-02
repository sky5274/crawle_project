package com.sky.sm.bean;

import java.util.List;

public class ProjectPropertyEnumBean extends PropertyEnumEntity{

	/***/
	private static final long serialVersionUID = 1L;
	
	private String local;
	private List<PropertyEnumValueBean> enums;
	public List<PropertyEnumValueBean> getEnums() {
		return enums;
	}
	public void setEnums(List<PropertyEnumValueBean> enums) {
		this.enums = enums;
	}
	public String getLocal() {
		return local;
	}
	public void setLocal(String local) {
		this.local = local;
	}

}
