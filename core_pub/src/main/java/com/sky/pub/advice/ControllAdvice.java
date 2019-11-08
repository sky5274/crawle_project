package com.sky.pub.advice;


import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.InputStreamSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.sky.pub.util.ListUtils;


/** 
 * 
 * @author sky
 * @description 主要用于调试control的过程
 * <br><br>
 * 把这个类声明为一个切面： 
 * 1. 使用注解“@Component”把该类放入到IOC容器中 
 * 2. 使用注解“@Aspect”把该类声明为一个切面 
 *  
 * 设置切面的优先级: 
 * 3. 使用注解“@Order(number)”指定前面的优先级，值越小，优先级越高 
 */  
@Order(1)  
@Aspect  
@Component
public class ControllAdvice {

	private Log log=LogFactory.getLog(ControllAdvice.class);
	private String prefix="-->> ";
	private List<String> excuteUrl=Arrays.asList("/error");
	private Date times=null;
	private Date timee=null;
	String method=null;
	@Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping) || execution(* *..controller.*.*(..))")
	public void point(){}

	@Before("point()")
	public void before(JoinPoint joinpoint){
		HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String url = req.getRequestURI().toString();
		if(excuteUrl.contains(url)) {
			return;
		}
		//获取request
		times=new Date();
		method=joinpoint.getSignature().getDeclaringTypeName()+"."+joinpoint.getSignature().getName();
		
		Map<String, Object> argMap=new HashMap<>();
		MethodSignature msd = (MethodSignature) joinpoint.getSignature();

		LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
		String[] params = u.getParameterNames(msd.getMethod());
		Object[] argList = joinpoint.getArgs();
		for(int i=0;i<argList.length;i++){
			Object arg = argList[i];
			if(arg instanceof HttpServletRequest){
				argMap.put(params[i],req.getParameterMap());
				continue;
			}else if(arg instanceof MultipartFile){
				MultipartFile f = (MultipartFile) arg;
				argMap.put(params[i],f.getOriginalFilename());
				continue;
			}else if(arg instanceof HttpServletResponse || arg instanceof InputStreamSource){
				continue;
			}
			argMap.put(params[i], arg);
		}
		log.info(prefix+method+"  被"+req.getRemoteAddr()+"调用");
		String queryString = req.getQueryString();
		log.info(prefix+"url:"+url+(StringUtils.isEmpty(queryString)?"":" ?"+queryString)+(ListUtils.isEmpty(argMap.keySet())?"":"  args:"+JSON.toJSONString(argMap)));
	}
	@After("point()")
	public synchronized void after(JoinPoint joinPoint){
		HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String url = req.getRequestURI().toString();
		if(excuteUrl.contains(url)) {
			return;
		}
		timee=new Date();
		try {
			long cost=timee.getTime()-times.getTime();
			log.info(prefix+joinPoint.getSignature().getName()+"  执行结束。 耗时:  "+cost+"ms;");
		} catch (Exception e) {
		}
		timee=null;
		times=null;
	}

	//	@AfterReturning(pointcut="point()",returning="val")
	//	public void getReturn(JoinPoint joinpoint,Object val){
	//		log.debug("方法："+joinpoint.getSignature().getName()+"  执行结果：   "+val);
	//	}



}
