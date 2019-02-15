package com.sky.crawl.data.config.dao.entity;

import java.util.List;

/**
 * 菜单节点数据
 * @author 王帆
 * @date  2018年12月24日 下午2:19:16
 */
public class MenuNode extends MenuEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<MenuNode> child;

	public List<MenuNode> getChild() {
		return child;
	}

	public void setChild(List<MenuNode> child) {
		this.child = child;
	}
}
