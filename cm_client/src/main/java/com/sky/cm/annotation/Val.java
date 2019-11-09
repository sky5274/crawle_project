package com.sky.cm.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * config-value 注解
 * @author 王帆
 * @date  2019年11月9日 下午9:19:36
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Autowired(required=false)
public @interface Val {
	String value();  //支持两种属性配置${key:defv}\val
}
