package com.sky.demo;

import org.springframework.boot.SpringApplication;
import com.sky.pub.annotation.SpringStartApplication;
import com.sky.rpc.annotation.EnableRpcRegistable;

@SpringStartApplication
@EnableRpcRegistable("com.sky.demo")
public class FlowDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlowDemoApplication.class, args);
	}

}
