package com.sky.rpc.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import com.sky.rpc.config.RpcRegistConfigration;

/**
 * rpc auto regist config annotations
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(RpcRegistConfigration.class)
public @interface EnableRpcRegistable {
	String[] value() default "";
}
