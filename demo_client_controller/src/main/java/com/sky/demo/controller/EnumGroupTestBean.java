package com.sky.demo.controller;

import com.sky.cm.annotation.EnumField;

public class EnumGroupTestBean {
	private String enumNo;
	@EnumField(groupNo = "menu_test",relyFiled = "enumNo")
	private String enumName;
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
	
	
}
