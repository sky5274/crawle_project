package com.sky.rpc.base;

public class RpcException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public RpcException(String string) {
		super(string);
	}
	public RpcException(String string,Throwable e) {
		super(string,e);
	}

}
