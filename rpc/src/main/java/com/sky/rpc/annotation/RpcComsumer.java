package com.sky.rpc.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * rpc consumer annotation
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Autowired(required=false)
public @interface RpcComsumer {
	String version() default "";
	String group() default "";
	int timeout() default 3000;
	
	Class<?>[] target() default {};
}
