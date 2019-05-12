package com.sky.pub;

import java.io.Serializable;

public class PageRequest<T> implements Serializable{
	/***/
	private static final long serialVersionUID = 1L;
	private Integer current=0;
	private Integer pageSize=10;
	private boolean isInit=false;
	private T data;
	public PageRequest() {}
	public PageRequest(int current,int pageSize) {
		this.current=current;
		this.pageSize=pageSize;
	}
	
	public Integer getCurrent() {
		return current;
	}
	public void setCurrent(int current) {
		this.current = current;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public PageRequest<T> initPage() {
		if(!isInit) {
			if(hasPageData()) {
				if(current<1) {
					current=0;
				}
				if(pageSize<0) {
					pageSize=10;
				}
				if(current>0) {
					current=(current-1)*pageSize;
				}
				isInit=true;
			}
		}
		return this;
	}
	
	public void initFlag(boolean flag) {
		this.isInit=flag;
	}
	
	public boolean hasInit() {
		return isInit;
	}
	public Boolean hasPageData() {
		return current !=null && pageSize !=null;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
}
