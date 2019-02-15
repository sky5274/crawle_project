package com.sky.pub.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseConfigController {
	@RequestMapping("/config/config.js")
	public ResponseEntity<byte[]> getConfigJs(HttpServletRequest req) throws IOException {
		HttpHeaders headers = new HttpHeaders();    
		File file = new File(getClass().getResource("/static/js/com/config.js").getFile());
		BufferedReader read=new BufferedReader(new FileReader(file));
		StringBuilder str=new StringBuilder();
		String temp=null;
		while((temp=read.readLine())!=null) {
			str.append(temp);
		}
		headers.set(HttpHeaders.CONTENT_TYPE,"application/javascript");    
		headers.set("Accept-Ranges", "bytes"); 
		String url=req.getRequestURL().toString();
		String context=str.toString().replace("#{contextPath}", "\""+url.substring(0,url.indexOf(req.getServletPath()))+"\"");
		return new ResponseEntity<byte[]>( context.getBytes(), headers, HttpStatus.CREATED) ; 
	}
}
