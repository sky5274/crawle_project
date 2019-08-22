package com.sky.cm.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.sky.cm.enums.LimitType;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Limit {
	/**
	 * 资源的名字
	 * @return String
	 */
	String name() default "";

	/**
	 * 资源的key
	 * @return String
	 */
	String key() default "";

	/**
	 * Key的prefix
	 * @return String
	 */
	String prefix() default "LIMIT_Location";

	/**
	 * 给定的时间段
	 * 单位秒
	 * @return int
	 */
	int period() default 1;

	/**
	 * 最多的访问限制次数
	 * @return int
	 */
	int count() default 5;

	/**
	 * 类型
	 * @return LimitType
	 */
	LimitType limitType() default LimitType.LOCATION;
}
