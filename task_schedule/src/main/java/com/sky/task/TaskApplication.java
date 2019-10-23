package com.sky.task;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import com.sky.pub.annotation.SpringStartApplication;

@SpringStartApplication
@MapperScan(value= {"com.sky.*.dao"})
public class TaskApplication {
	public static void main(String[] args) {
		SpringApplication.run(TaskApplication.class, args);
	}
}
