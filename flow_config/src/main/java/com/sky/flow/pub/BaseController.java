package com.sky.flow.pub;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.sky.flow.exception.FlowException;
import com.sky.pub.Result;
import com.sky.pub.ResultCode;
import com.sky.pub.common.SpringHandlerExceptionResolver;

@SuppressWarnings({"unchecked","rawtypes"})
public class BaseController {
	protected Log log=LogFactory.getLog(getClass());
	@ExceptionHandler(FlowException.class)
	public ResultDTO<?> getException(FlowException exp){
		return new ResultDTO(exp.getCode(), exp.getErrorMessages()).fail();
	}
	@ExceptionHandler(Exception.class)
	public Result<?> getException(Exception exp){
		log.error(exp.getMessage(), exp);
		return new Result(ResultCode.UNKONW_EXCEPTION,SpringHandlerExceptionResolver.getExceptionStrace(exp)).fail();
	}
}
