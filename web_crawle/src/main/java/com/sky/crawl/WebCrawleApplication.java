package com.sky.crawl;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import com.sky.pub.annotation.SpringStartApplication;

@SpringStartApplication
@MapperScan(value= {"com.sky.crawl.data.*.dao"})
public class WebCrawleApplication {

	public static void main(String[] args) {
		 SpringApplication.run(WebCrawleApplication.class, args);
	}
	
}

