package com.sky.pub;

import java.io.Serializable;

public class Result<T> implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String code;
	private String message;
	private boolean success=false;
	private T data;
	
	public Result() {}
	public Result(ResultCode code) {
		this(code,null,code==ResultCode.OK);
	}
	public Result(ResultCode code,Boolean success) {
		this(code,null,success);
	}
	public Result(ResultCode code,T data) {
		this(code,data,code==ResultCode.OK);
	}
	public Result(ResultCode code,T data,Boolean success) {
		this(code.getMsg(),code.getCode(),data,success);
	}
	public Result(String code,String msg,T data) {
		this(code,msg,data,ResultCode.OK.getCode().equals(code));
	}
	public Result(String code,String msg,T data,Boolean success) {
		this.setCode(code);
		this.setMessage(msg);
		this.setSuccess(success);
		this.setData(data);
	}
	public Result(String code,String msg) {
		this(code,msg,null,false);
	}
	public Result(String code,String msg,Boolean success) {
		this(code,msg,null,success);
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	public Result<T> ok() {
		this.success=true;
		return this;
	}
	public Result<T> fail() {
		this.success=false;
		return this;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
}
