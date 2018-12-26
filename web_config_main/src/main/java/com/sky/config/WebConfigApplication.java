package com.sky.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class WebConfigApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebConfigApplication.class, args);
	}

}

