package com.sky.pub.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
//import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@SpringBootApplication
//@EnableRedisHttpSession  
@ComponentScan(value= {"com.sky"})
public @interface SpringStartApplication {

}
