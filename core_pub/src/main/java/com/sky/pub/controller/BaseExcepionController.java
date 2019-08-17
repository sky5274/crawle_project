package com.sky.pub.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.sky.pub.Result;
import com.sky.pub.ResultCode;
import com.sky.pub.ResultMData;
import com.sky.pub.common.exception.ResultException;
import com.sky.pub.common.exception.ResultMsgException;
import com.sky.pub.web.exception.SpringHandlerExceptionResolver;
@SuppressWarnings("rawtypes")
public class BaseExcepionController {
	protected Log log=LogFactory.getLog(getClass());
	@ExceptionHandler(ResultException.class)
	public Result<?> getException(ResultMsgException exp){
		return new ResultMData<>(exp.getCode(), exp.getMsgDatas()).fail();
	}
	@ExceptionHandler(ResultException.class)
	public Result<?> getException(ResultException exp){
		return new Result(exp.getCode(), exp.getMsg()).fail();
	}
	@SuppressWarnings("unchecked")
	@ExceptionHandler(Exception.class)
	public Result<?> getException(Exception exp){
		log.error(exp.getMessage(), exp);
		return new Result(ResultCode.UNKONW_EXCEPTION,SpringHandlerExceptionResolver.getExceptionStrace(exp)).fail();
	}
}
