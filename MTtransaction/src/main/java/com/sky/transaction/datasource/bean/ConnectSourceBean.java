package com.sky.transaction.datasource.bean;

import com.sky.transaction.datasource.event.ConnectSourceEvent;

/**
 * 多种数据源链接定义
 * @author 王帆
 * @date  2019年9月20日 上午10:35:28
 */
public abstract class ConnectSourceBean<T> implements ConnectSourceEvent{
	private T connect;
	private int status=0;	//0：待处理；1：已处理；-1：以回滚
	public ConnectSourceBean(T conn) {
		this.connect=conn;
	}
	
	/**
	 *   获取数据链接
	 * @return
	 * @author 王帆
	 * @date 2019年9月20日 上午10:35:59
	 */
	public T getConnect() {
		return connect;
	}
	
	public abstract void init();
	
	/**
	 * 	设置 数据源链接
	 * @param connect
	 * @author 王帆
	 * @date 2019年9月20日 上午10:39:11
	 */
	public void setConnect(T connect) {
		this.connect = connect;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}
