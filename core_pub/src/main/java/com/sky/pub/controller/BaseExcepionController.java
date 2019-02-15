package com.sky.pub.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;

import com.sky.pub.Result;
import com.sky.pub.ResultCode;
import com.sky.pub.common.exception.ResultException;
@SuppressWarnings("rawtypes")
public class BaseExcepionController {
	@ExceptionHandler(ResultException.class)
	public Result<?> getException(ResultException exp){
		return new Result(exp.getCode(), exp.getMsg()).fail();
	}
	@ExceptionHandler(Exception.class)
	public Result<?> getException(Exception exp){
		return new Result(ResultCode.UNKONW_EXCEPTION).fail();
	}
}
