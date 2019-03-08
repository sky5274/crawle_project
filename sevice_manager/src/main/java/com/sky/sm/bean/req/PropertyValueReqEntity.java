package com.sky.sm.bean.req;

import com.sky.pub.PageRequest;
import com.sky.sm.bean.PropertyValueEntity;

/**
 * 	属性查询（分页）条件
 * @author 王帆
 * @date  2019年3月7日 下午2:36:11
 */
public class PropertyValueReqEntity extends PropertyValueEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String keyLike;
	private PageRequest page;
	public PageRequest getPage() {
		return page;
	}
	public void setPage(PageRequest page) {
		this.page = page;
	}
	public void initPage() {
		if(page !=null) {
			page=page.initPage();
		}
	}
	public String getKeyLike() {
		return keyLike;
	}
	public void setKeyLike(String keyLike) {
		this.keyLike = keyLike;
	}
	
}
