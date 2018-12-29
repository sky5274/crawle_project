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
}
