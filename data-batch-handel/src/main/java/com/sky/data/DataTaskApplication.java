package com.sky.data;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import com.sky.pub.annotation.SpringStartApplication;

@SpringStartApplication
@MapperScan(value= {"com.sky.*.dao"})
public class DataTaskApplication {
	public static void main(String[] args) {
		SpringApplication.run(DataTaskApplication.class, args);
	}
}
