package com.sky.auth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import com.sky.pub.annotation.SpringStartApplication;

@SpringStartApplication
@EnableEurekaClient
@MapperScan(value= {"com.sky.auth.config.dao"})
public class AuthConfigApplication {
	public static void main(String[] args) {
		SpringApplication.run(AuthConfigApplication.class, args);
	}
}
