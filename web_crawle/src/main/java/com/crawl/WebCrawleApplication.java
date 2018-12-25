package com.crawl;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
@MapperScan(value= {"com.ceawl.data.*.dao","com.sky"})
public class WebCrawleApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebCrawleApplication.class, args);
	}

}

