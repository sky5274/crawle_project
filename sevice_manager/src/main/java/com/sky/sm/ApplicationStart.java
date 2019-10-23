package com.sky.sm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import com.sky.pub.annotation.SpringStartApplication;

@SpringStartApplication
@MapperScan("com.sky.sm.dao")
public class ApplicationStart {
	public static void main(String[] args) {
		SpringApplication.run(ApplicationStart.class, args);
	}
}
