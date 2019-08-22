package com.sky.rpc.base;

import java.io.Serializable;

public class RpcBaseBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String requestId;
	public RpcBaseBean() {}
	public RpcBaseBean(String id) {
		this.requestId=id;
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
}
