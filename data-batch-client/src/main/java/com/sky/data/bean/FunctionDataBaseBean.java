package com.sky.data.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class FunctionDataBaseBean implements Serializable{

	/***/
	private static final long serialVersionUID = 1L;
	private int size;
	private String name;
	private BigDecimal value;
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public BigDecimal getValue() {
		return value;
	}
	public void setValue(BigDecimal value) {
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
