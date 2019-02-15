package com.sky.pub;

import java.util.List;

/**
 * 分页对象
 * @author 王帆
 * @date  2018年12月23日 下午10:09:30
 */
public class Page <T>{
	private List<T> list;
	private int total;
	private Integer current;
	private Integer pageSize;
	
	public Page() {}
	public Page(List<T> list,int total) {
		this.setList(list);
		this.setTotal(total);
	}
	public List<T> getList() {
		return list;
	}
	public Page<T> setList(List<T> list) {
		this.list = list;
		return this;
	}
	public int getTotal() {
		return total;
	}
	public Page<T> setTotal(int total) {
		this.total = total;
		return this;
	}
	public Integer getCurrent() {
		return current;
	}
	public void setCurrent(Integer current) {
		this.current = current;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
	public void setPageData(PageRequest<T> reqPage) {
		setPageData(reqPage.hasInit(),reqPage.getCurrent(),reqPage.getPageSize());
	}
	public void setPageData(BasePageRequest reqPage) {
		setPageData(reqPage.hasInit(),reqPage.getCurrent(),reqPage.getPageSize());
	}
	
	private void setPageData(boolean isInit,int current,int pageSize) {
		if(isInit) {
			this.current=current/pageSize;
		}else {
			this.current=current;
		}
		this.pageSize=pageSize;
	}
}
