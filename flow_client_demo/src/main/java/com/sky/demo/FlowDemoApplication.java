package com.sky.demo;

import org.springframework.boot.SpringApplication;
import com.sky.pub.annotation.SpringStartApplication;
import com.sky.rpc.annotation.EnableRpcRegistAble;

@SpringStartApplication
@EnableRpcRegistAble("com.sky.demo")
public class FlowDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlowDemoApplication.class, args);
	}

}
