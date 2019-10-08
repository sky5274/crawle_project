package com.sky.transaction.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 方法级别事务注解
 * @author 王帆
 * @date  2019年9月20日 上午9:27:45
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface MTransaction {
	int timeout() ;	//超时时间		
	
	String[] dataSource() default {};		//限定数据源
}
