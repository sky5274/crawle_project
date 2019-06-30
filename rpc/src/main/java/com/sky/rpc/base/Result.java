package com.sky.rpc.base;


public class Result <T> extends RpcBaseBean{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean hasExp=false;
	private T data;
	private Throwable exp;
	
	public Result(String id,T res){
		super(id);
		this.data=res;
	}
	
	public Result(String id,Throwable e) {
		super(id);
		hasExp=true;
		this.exp=e;
	}
	
	public boolean hasException() {
		return hasExp;
	}

	public T getData() {
		return data;
	}
	public Throwable getException() {
		return exp;
	}
}
