package com.sky.cm.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * project enum field annotaion
 * @author wangfan
 * @date 2020年2月1日 下午10:16:18
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface EnumField {
	/**
	 * 枚举分组编码
	 * @return
	 * @author wangfan
	 * @date 2020年2月1日 下午10:18:46
	 */
	String groupNo(); 
	/**
	 * 依赖字段
	 * @return
	 * @author wangfan
	 * @date 2020年2月1日 下午10:19:13
	 */
	String relyFiled();  
}
