package com.sky.rpc.base;


public class Result <T> extends RpcBaseBean{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean hasExp=false;
	private T data;
	private Throwable exp;
	private Class<?> throwClazz;
	public Result() {};
	public Result(String id,T res){
		super(id);
		this.data=res;
	}
	
	public Result(String id,Throwable e) {
		super(id);
		this.hasExp=true;
		this.exp=e;
		this.setThrowClazz(e.getClass());
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
	public Class<?> getThrowClazz() {
		return throwClazz;
	}
	public void setThrowClazz(Class<?> throwClazz) {
		this.throwClazz = throwClazz;
	}
}
