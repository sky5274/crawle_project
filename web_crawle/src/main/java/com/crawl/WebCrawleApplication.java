package com.crawl;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableEurekaClient
@ComponentScan
@MapperScan(value= {"com.crawl.data.*.dao","com.sky"})
public class WebCrawleApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebCrawleApplication.class, args);
	}

}

