package com.sky.pub.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseConfigController {
	@Value("${config.level:1}")
	private Integer level;
	
	@RequestMapping("/config/config.js")
	public ResponseEntity<byte[]> getConfigJs(HttpServletRequest req) throws IOException {
		HttpHeaders headers = new HttpHeaders();    
		InputStream inputStream = getClass().getResourceAsStream("/static/js/com/config.js");
		BufferedReader read=new BufferedReader(new InputStreamReader(inputStream));
		StringBuilder str=new StringBuilder();
		String temp=null;
		while((temp=read.readLine())!=null) {
			str.append(temp);
		}
		headers.set(HttpHeaders.CONTENT_TYPE,"application/javascript");    
		headers.set("Accept-Ranges", "bytes"); 
		String context=str.toString().replace("#{contextPath}", req.getContextPath());
		if(level!=null && level>1) {
			context=context.replaceAll("level:1", "level:"+level);
		}
		return new ResponseEntity<byte[]>( context.getBytes(), headers, HttpStatus.CREATED) ; 
	}
}
