package com.sky.pub.advice;

import java.time.LocalDate;
import java.time.LocalTime;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.stereotype.Component;

/**
 * redis方法追踪切面 
 * @author 王帆
 */
@Order(1)  
@Aspect  
@Component
public class RedisConnectAdvice {
	private Log log=LogFactory.getLog(RedisConnectAdvice.class);
	private Long times=null;
	private Long timee=null;
	String method=null;
	@Resource
	private RedisConnectionFactory connectFactory;
	
	@Pointcut("execution(* cn.microvideo.pub.service.impl.BaseRedis*+.*(..))")
	public void point(){}
	
	@Before("point()")
	public void before(JoinPoint joinpoint){
		times=System.currentTimeMillis();
		if(connectFactory instanceof JedisConnectionFactory) {
			JedisConnectionFactory connects=(JedisConnectionFactory)connectFactory;
			log.debug("ooo redis Connect to ["+connects.getHostName()+":"+connects.getPort()+
					",password="+connects.getPassword()+
					", database:"+connects.getDatabase()+
					",time:"+LocalDate.now()+" "+LocalTime.now()+
					"]");
		}else if(connectFactory instanceof LettuceConnectionFactory) {
			LettuceConnectionFactory connects=(LettuceConnectionFactory)connectFactory;
			log.debug("ooo redis Connect to ["+connects.getHostName()+":"+connects.getPort()+
					",password="+connects.getPassword()+
					", database:"+connects.getDatabase()+
					",time:"+LocalDate.now()+" "+LocalTime.now()+
					"]");
		}
		
	}

	@After("point()")
	public synchronized void after(JoinPoint joinPoint){
		
	}
	
	@AfterReturning(pointcut="point()",returning="val")
	public void getReturn(JoinPoint joinpoint,Object val){
		timee=System.currentTimeMillis();
		try {
			long cost=timee-times;
			log.debug("===> redis action: {value：   "+val+",cost:  "+cost+"ms }");
		} catch (Exception e) {
			// TODO: handle exception
		}
		timee=null;
		times=null;
		
	}
	
}

