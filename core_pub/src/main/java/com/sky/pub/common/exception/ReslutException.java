package com.sky.pub.common.exception;

import com.sky.pub.ResultCode;
/**
 * 通用结果异常
 * @author 王帆
 * @date  2018年12月23日 下午6:02:12
 */
public class ReslutException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String code;
	private String msg;
	public ReslutException(ResultCode code) {
		this.setCode(code.getCode());
		this.setMsg(code.getMsg());
	}
	public ReslutException(ResultCode code,String message) {
		this.setCode(code.getCode());
		this.setMsg(message);
	}
	public ReslutException(ResultCode code,String message,Exception e) {
		super(e);
		this.setCode(code.getCode());
		this.setMsg(message);
	}
	public ReslutException(ResultCode code,Exception e) {
		super(e);
		this.setCode(code.getCode());
		this.setMsg(code.getMsg());
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
