package com.sky.rpc.base;

import java.io.Serializable;

/**
 * rpc 节点记录
 *<p>Title: RcpIp.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2017</p>
 * <p>Company: </p>
 * @author sky
 * @date 2018年10月15日
 */
public class RpcIp implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String host;
	private int port;
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}

}
