package com.sky.auth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableEurekaClient
@ComponentScan(value= {"com.sky"})
@MapperScan(value= {"com.sky.auth.config.dao"})
public class AuthConfigApplication {
	public static void main(String[] args) {
		SpringApplication.run(AuthConfigApplication.class, args);
	}
}
