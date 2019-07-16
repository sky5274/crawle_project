package com.sky.flow.pub;

import java.util.List;

import com.sky.pub.Result;
import com.sky.pub.ResultCode;

public class ResultDTO<T> extends Result<T>{
	
	/***/
	private static final long serialVersionUID = 1L;
	private List<String> messages;
	
	public ResultDTO(ResultCode code,List<String> messages) {
		super(code);
		this.messages=messages;
	}
	public ResultDTO(String code,List<String> messages) {
		super(code,messages.toString());
		this.messages=messages;
	}

	public List<String> getMessages() {
		return messages;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}
	
	
	public ResultDTO<T> ok() {
		super.ok();
		return this;
	}
	public ResultDTO<T> fail() {
		super.fail();
		return this;
	}

}
