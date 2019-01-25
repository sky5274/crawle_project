package com.crawl;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.sky.pub.util.SpringUtil;

@SpringBootApplication
@EnableEurekaClient
@ComponentScan(value= {"com.sky","com.crawl"})
@MapperScan(value= {"com.crawl.data.*.dao"})
public class WebCrawleApplication {

	public static void main(String[] args) {
		 ConfigurableApplicationContext application = SpringApplication.run(WebCrawleApplication.class, args);
		 SpringUtil.setApplicationContext(application);
	}
	
}

