package com.sky.pub;

public class Result<T> {
	private String code;
	private String message;
	private boolean success=false;
	private T data;
	
	public Result(ResultCode code) {
		this.setCode(code.getCode());
		this.setMessage(code.getMsg());
		this.setSuccess(code==ResultCode.OK);
	}
	public Result(ResultCode code,Boolean success) {
		this.setCode(code.getCode());
		this.setMessage(code.getMsg());
		this.setSuccess(success);
	}
	public Result(ResultCode code,T data) {
		this.setCode(code.getCode());
		this.setMessage(code.getMsg());
		this.setSuccess(code==ResultCode.OK);
		this.setData(data);
	}
	public Result(ResultCode code,T data,Boolean success) {
		this.setCode(code.getCode());
		this.setMessage(code.getMsg());
		this.setSuccess(success);
		this.setData(data);
	}
	public Result(String code,String msg,T data) {
		this.setCode(code);
		this.setMessage(msg);
		this.setData(data);
	}
	public Result(String code,String msg,T data,Boolean success) {
		this.setCode(code);
		this.setMessage(msg);
		this.setSuccess(success);
		this.setData(data);
	}
	public Result(String code,String msg) {
		this.setCode(code);
		this.setMessage(msg);
	}
	public Result(String code,String msg,Boolean success) {
		this.setCode(code);
		this.setMessage(msg);
		this.setSuccess(success);
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
