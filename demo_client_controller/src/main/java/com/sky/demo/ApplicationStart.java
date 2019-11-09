package com.sky.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.sky.cm.annotation.EnableLimitValueConfig;
import com.sky.rpc.annotation.EnableRpcRegistAble;

@SpringBootApplication
@ComponentScan(value= {"com.sky","com.sky.transaction"})
@EnableRpcRegistAble("com.sky.demo")
@EnableLimitValueConfig
public class ApplicationStart {
	public static void main(String[] args) {
		SpringApplication.run(ApplicationStart.class, args);
	}
}
