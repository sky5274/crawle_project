package com.sky.pub;

/**
 * 页面分仓请求
 * @author 王帆
 * @date  2018年12月23日 下午10:12:11
 */
public class BasePageRequest extends BaseTableEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer current;
	private Integer pageSize;
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
	
	public void initPage() {
		if(isPage()) {
			if(current<1) {
				current=0;
			}
			if(pageSize<0) {
				pageSize=10;
			}
			if(current>0) {
				current=(current-1)*pageSize;
			}
		}
	}
	
	public Boolean isPage() {
		return current !=null && pageSize !=null;
	}
}
