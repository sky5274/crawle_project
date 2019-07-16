package com.sky.flow.exception;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

/**
 * 流程异常
 * @author 王帆
 * @date  2019年5月16日 下午3:30:55
 */
public class FlowException extends Exception{

	/***/
	private static final long serialVersionUID = 1L;
	
	private Level level;
	private String code;
	private List<String> errorMessages;
	public FlowException(String message) {
		this(null, message,null);
	}
	public FlowException(String message,Level level) {
		this(null,message,level);
	}
	public FlowException(String code,String message,Level level) {
		this(null,message==null?null:Arrays.asList(message),level);
	}
	public FlowException(List<String> messages) {
		this(null,messages,null);
	}
	public FlowException(List<String> messages,Level level) {
		this(null,messages,level);
	}
	public FlowException(String code,List<String>messages,Level level) {
		super(messages.toString());
		this.code=code==null?"flow_-1":code;
		this.setErrorMessages(messages);
		this.level=level==null?Level.INFO:level;
	}
	
	public FlowException(String message, Throwable e) {
		super(e);
		this.code=code==null?"flow_-1":code;
		this.setErrorMessages(Arrays.asList(message));
		this.level=level==null?Level.INFO:level;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Level getLevel() {
		return level;
	}
	public void setLevel(Level level) {
		this.level = level;
	}
	public List<String> getErrorMessages() {
		return errorMessages;
	}
	public void setErrorMessages(List<String> errorMessages) {
		this.errorMessages = errorMessages;
	}
}
