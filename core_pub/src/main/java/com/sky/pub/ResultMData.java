package com.sky.pub;

import java.util.LinkedList;
import java.util.List;

/**
 * result message data with mutilate msg
 * @author 王帆
 * @date  2019年7月11日 上午10:27:09
 */
public class ResultMData<T> extends ResultExceptionData<T>{

	/***/
	private static final long serialVersionUID = 1L;
	public ResultMData(ResultCode code , List<MsgPrinter> messageData) {
		super(code);
		this.messageData=messageData;
	}
	public ResultMData(String code ,String messsage, List<MsgPrinter> messageData) {
		super(code,messsage);
		this.messageData=messageData;
	}
	public ResultMData(String code , List<MsgPrinter> messageData) {
		super();
		this.setCode(code);
		this.messageData=messageData;
	}
	
	public ResultMData(ResultCode code, T data, List<MsgPrinter> msgdatas) {
		this(code,msgdatas);
		this.setData(data);
	}

	private String spilt=";<br/>";
	private List<MsgPrinter> messageData;

	public List<MsgPrinter> getMessageData() {
		return messageData;
	}

	public void setMessageData(List<MsgPrinter> messageData) {
		this.messageData = messageData;
	}
	
	public ResultMData<T> addMessageData(List<MsgPrinter> messageData) {
		if(this.messageData==null) {
			this.messageData=new LinkedList<>();
		}
		this.messageData.addAll(messageData);
		return this;
	}
	public ResultMData<T> addMessageData(MsgPrinter messageData) {
		if(this.messageData==null) {
			this.messageData=new LinkedList<>();
		}
		this.messageData.add(messageData);
		return this;
	}
	
	public void setSpilt(String spilt) {
		this.spilt = spilt;
	}
	
	@Override
	public String getMessage() {
		String str = super.getMessage();
		boolean isCodeMsg=ResultCode.valueOf(getCode()).getMsg().equals(str);
		StringBuilder msg=new StringBuilder(isCodeMsg?"":str);
		if(this.messageData!=null && !this.messageData.isEmpty()) {
			if(msg.length()>0) {
				msg.append(spilt);
			}
			int i=0;
			for(MsgPrinter m:this.messageData) {
				msg.append(m.print(i++)).append(spilt);
			}
		}
		return msg.toString();
	}
}
