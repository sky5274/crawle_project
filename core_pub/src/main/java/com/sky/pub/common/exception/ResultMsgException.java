package com.sky.pub.common.exception;

import java.util.List;

import org.springframework.boot.logging.LogLevel;

import com.sky.pub.MsgPrinter;
import com.sky.pub.ResultCode;

public class ResultMsgException extends ResultException{

	/***/
	private static final long serialVersionUID = 1L;
	private List<MsgPrinter> msgDatas;
	
	public ResultMsgException(ResultCode code, List<MsgPrinter> msgDatas) {
		super(code);
		this.setMsgDatas(msgDatas);
	}
	public ResultMsgException(ResultCode code, List<MsgPrinter> msgDatas,LogLevel level) {
		super(code, level);
		this.setMsgDatas(msgDatas);
	}
	public ResultMsgException(ResultCode code, List<MsgPrinter> msgDatas,LogLevel level, Exception e) {
		super(code, level, e);
		this.setMsgDatas(msgDatas);
	}

	public List<MsgPrinter> getMsgDatas() {
		return msgDatas;
	}

	public void setMsgDatas(List<MsgPrinter> msgDatas) {
		this.msgDatas = msgDatas;
	}

}
