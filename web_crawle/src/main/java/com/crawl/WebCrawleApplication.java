package com.crawl;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(value= {"com.sky","com.crawl"})
@MapperScan(value= {"com.crawl.data.*.dao"})
public class WebCrawleApplication {

	public static void main(String[] args) {
		 SpringApplication.run(WebCrawleApplication.class, args);
	}
	
}

