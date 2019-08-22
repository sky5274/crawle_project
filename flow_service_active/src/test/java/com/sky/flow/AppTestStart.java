package com.sky.flow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan("com.sky")
public class AppTestStart {
	public static void main(String[] args) {
		SpringApplication.run(AppTestStart.class, args);
	}
}
