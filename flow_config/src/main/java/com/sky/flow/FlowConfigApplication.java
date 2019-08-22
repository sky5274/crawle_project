package com.sky.flow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import com.sky.pub.annotation.SpringStartApplication;

@SpringStartApplication
@EnableAutoConfiguration
public class FlowConfigApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlowConfigApplication.class, args);
	}

}
