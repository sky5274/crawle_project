package com.sky.cm.aop;



import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import com.sky.cm.annotation.Limit;


/** 
 * 
 * @author sky
 * @description 主要用于方法限流aop
 */  
@Order(1)  
@Aspect  
@Component
public class RequestLimitAdvice {

	private Log log=LogFactory.getLog(RequestLimitAdvice.class);
	private String prefix="-->> ";
	String method=null;

	@Around("execution(* *(..)) && @annotation(limit)")
	public Object before(ProceedingJoinPoint joinpoint,Limit limit){
		try {
			return joinpoint.proceed();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}



}
