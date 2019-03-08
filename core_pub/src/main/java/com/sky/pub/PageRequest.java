package com.sky.pub;

public class PageRequest{
	private Integer current;
	private Integer pageSize;
	private boolean isInit=false;
	public PageRequest() {}
	public PageRequest(int current,int pageSize) {
		super();
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
	
	public PageRequest initPage() {
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
}
