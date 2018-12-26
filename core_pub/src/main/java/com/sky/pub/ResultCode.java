package com.sky.pub;

public enum ResultCode {
	
	OK("0","OK"),
	FAILED("-1","FAILED");
	private String code;
	private String msg;
	ResultCode(String code,String msg) {
		this.setCode(code);
		this.setMsg(msg);
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
