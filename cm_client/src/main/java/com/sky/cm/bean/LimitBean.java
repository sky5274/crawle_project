package com.sky.cm.bean;

import java.io.Serializable;

public class LimitBean implements Serializable{
	/***/
	private static final long serialVersionUID = -3686501024646747480L;
	private String key;
	private int count=5;
	private int limitCount=5;
	private int priod=1;
	public boolean isLimit() {
		return count>0 && count==1;
	}
	
	public LimitBean() {}
	public LimitBean(String key) {
		this.key=key;
	}
	public LimitBean(String key,int priod,int count) {
		this.key=key;
		this.priod=priod;
		this.setLimitCount(count);
		this.count=count;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getPriod() {
		return priod;
	}
	public void setPriod(int priod) {
		this.priod = priod;
	}

	public int getLimitCount() {
		return limitCount;
	}

	public void setLimitCount(int limitCount) {
		this.limitCount = limitCount;
	}
}
