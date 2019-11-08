package com.sky.test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.sky.container.controller.ContainerController;
import com.sky.container.service.impl.CloudDockerServiceImpl;
import com.sky.container.socket.ContainerMontiorSocketService;
import com.sky.container.socket.stream.StreamReadEvent;
import com.sky.container.util.CommandUtil;
import com.sky.pub.Result;
import com.sky.pub.controller.FileController;

import ch.qos.logback.core.util.FileUtil;

public class JavaCommandTest {
	public static void main(String[] args) throws IOException {
//		String command="docker version";
		String command="ipconfig";
		String info = CommandUtil.exec(command);
		System.out.println(info);
		
		List<String> colums=new LinkedList<String>();
		Process proc = CommandUtil.run(String.format("docker stats %s","824f04557aad"),null);
		CommandUtil.readStream(proc.getInputStream(), new StreamReadEvent() {

			@Override
			public void invoke(String str) throws IOException {
				if(!StringUtils.isEmpty(str)) {
					if(!str.contains("CONTAINER ID")) {
						colums.clear();
						String[] cols = str.split("   ");
						for(String s:cols) {
							if(!StringUtils.isEmpty(s)) {
								colums.add(s);
							}
						}
						System.err.println(JSON.toJSONString(ContainerMontiorSocketService.loadStats(colums)));
					}
				}
			}
		});
	}
	
}
