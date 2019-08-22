package com.sky.task;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import com.sky.pub.annotation.SpringStartApplication;

@EnableEurekaClient
@SpringStartApplication
@MapperScan(value= {"com.sky.*.dao"})
public class TaskApplication {
	public static void main(String[] args) {
		SpringApplication.run(TaskApplication.class, args);
	}
}
