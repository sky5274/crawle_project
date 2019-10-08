package com.sky.pub.common.exception;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.logging.LogLevel;
import org.springframework.util.StringUtils;

import com.sky.pub.ResultCode;
/**
 * 通用结果异常
 * @author 王帆
 * @date  2018年12月23日 下午6:02:12
 */
public class ResultException extends Exception{
	private Log log=LogFactory.getLog(getClass());
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LogLevel level=LogLevel.INFO;
	private String code;
	private String msg;
	private Object data;
	public ResultException(ResultCode code) {
		this(code.getCode(),code.getMsg(),code==ResultCode.UNKONW_EXCEPTION? LogLevel.INFO:LogLevel.ERROR);
	}
	public ResultException(Object data,ResultCode code) {
		this(code.getCode(),code.getMsg(),code==ResultCode.UNKONW_EXCEPTION? LogLevel.INFO:LogLevel.ERROR,data);
	}
	public ResultException(ResultCode code,LogLevel level) {
		this(code.getCode(),code.getMsg(),level);
	}
	public ResultException(ResultCode code,LogLevel level,Exception e) {
		this(code.getCode(),code.getMsg(),level,e);
	}
	public ResultException(ResultCode code,String message) {
		this(code.getCode(),StringUtils.isEmpty(message)? code.getMsg():message);
	}
	public ResultException(ResultCode code,String message,Object data) {
		this(code.getCode(),StringUtils.isEmpty(message)? code.getMsg():message,code==ResultCode.UNKONW_EXCEPTION? LogLevel.INFO:LogLevel.ERROR,data);
	}
	public ResultException(ResultCode code,String message,LogLevel level) {
		this(code.getCode(),StringUtils.isEmpty(message)? code.getMsg():message,level);
	}
	public ResultException(ResultCode code,String message,Object data,Exception e) {
		this(code.getCode(),StringUtils.isEmpty(message)? code.getMsg():message,code!=ResultCode.UNKONW_EXCEPTION? LogLevel.INFO:LogLevel.ERROR,data,e);
	}
	public ResultException(ResultCode code,String message,Exception e) {
		this(code.getCode(),StringUtils.isEmpty(message)? code.getMsg():message,e);
	}
	public ResultException(ResultCode code,String message,LogLevel level,Exception e) {
		this(code.getCode(),StringUtils.isEmpty(message)? code.getMsg():message,level,e);
	}
	public ResultException(String code,String message) {
		this(code,message,LogLevel.INFO);
	}
	public ResultException(String code,String message,Exception e) {
		this(code,message,LogLevel.INFO,e);
	}
	public ResultException(String code,String message,LogLevel level) {
		this(code,message,level,null);
	}
	public ResultException(String code,String message,LogLevel level,Exception e) {
		this(code,message,level,null,e);
	}
	public ResultException(String code,String message,LogLevel level,Object data) {
		super(message);
		this.setData(data);
		this.setCode(code);
		this.setMsg(message);
		this.setLevel(level);
	}
	public ResultException(String code,String message,LogLevel level,Object data,Exception e) {
		super(e);
		this.setData(data);
		this.setCode(code);
		this.setMsg(message);
		this.setLevel(level);
	}
	
	/**
	 * 调用格式：  new ReslutException("msg:{} time:{}",ResultCode.ok,"hello",new Date())
	 * @param code
	 * @param message
	 */
	public ResultException(String message,ResultCode code,Object...args) {
		if(message!=null) {
				for(Object arg:args) {
					message.replace("{}", arg.toString());
				}
			this.setMsg(message);
		}else {
			this.setMsg(null);
		}
		this.setCode(code.getCode());
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
	
	public LogLevel getLevel() {
		return level;
	}
	public void setLevel(LogLevel level) {
		this.level = level;
	}
	@Override
	public void printStackTrace() {
		switch (level) {
		case TRACE:
			log.trace(this.msg);
			break;
		case DEBUG:
			log.debug(this.msg);
			break;
		case INFO:
			log.info(this.msg);
			break;
		case WARN:
			log.warn(this.msg,this);
			break;
		case ERROR:
			log.error(this.msg,this);
			break;

		default:
			break;
		}
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
}
