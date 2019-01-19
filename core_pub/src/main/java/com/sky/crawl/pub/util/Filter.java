package com.crawl.pub.util;

import java.util.List;

/**
 * 对集合进行过滤定义父节点关键、当前节点、子节点方法
 * @author 王帆
 *2018年4月2日 下午3:24:30
 * @param <T>
 */
public interface Filter <T>{
	/**定义当前节点的父节点id*/
	public Object getPid(T t);
	/**定义当前节点的id*/
	public Object getId(T t);
	/**定义当前节点的子节点*/
	public void setChild(T t,List<T> child);
}
