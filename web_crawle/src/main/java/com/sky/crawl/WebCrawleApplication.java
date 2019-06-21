package com.sky.crawl;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import com.sky.pub.annotation.SpringStartApplication;

@EnableEurekaClient
@SpringStartApplication
@MapperScan(value= {"com.sky.crawl.data.*.dao"})
public class WebCrawleApplication {

	public static void main(String[] args) {
		 SpringApplication.run(WebCrawleApplication.class, args);
	}
	
}

