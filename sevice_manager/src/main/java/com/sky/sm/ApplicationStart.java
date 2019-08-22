package com.sky.sm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import com.sky.pub.annotation.SpringStartApplication;

@EnableEurekaClient
@SpringStartApplication
@MapperScan("com.sky.sm.dao")
public class ApplicationStart {
	public static void main(String[] args) {
		SpringApplication.run(ApplicationStart.class, args);
	}
}
