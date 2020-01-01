package com.sky.cm.config.advice;


import java.lang.reflect.UndeclaredThrowableException;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.sky.cm.trace.HttpRequestEventListener;
import com.sky.pub.Result;
import com.sky.pub.ResultCode;

/**
 * 接口请求trace记录
 * @author 王帆
 * @date  2019年3月14日 下午4:23:48
 */
@Order(2)
@Aspect
@Component
public class RequestTraceAopListener {
	@Autowired
	HttpRequestEventListener httpRequestEventListener;
	
	@Around("execution(* *(..)) && @annotation(reqmap)")
	public Object arround(ProceedingJoinPoint pjp,RequestMapping reqmap) throws Throwable {
		httpRequestEventListener.setRequest( ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
		httpRequestEventListener.recordTraceStart();
		try {
			Object obj = pjp.proceed();
			httpRequestEventListener.recordTraceEnd(200,obj);
			return obj;
		} catch (Throwable e) {
			httpRequestEventListener.recordTraceEnd(500,JSON.toJSONString(new Result<>(ResultCode.UNKONW_EXCEPTION, e.getMessage())));
			if(UndeclaredThrowableException.class.getName().equals(e.getClass().getName())) {
				throw e.getCause();
			}
			throw e;
		}
	}
	
	
}
