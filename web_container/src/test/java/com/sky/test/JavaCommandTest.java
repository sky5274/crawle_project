package com.sky.test;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import org.springframework.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.sky.container.socket.ContainerMontiorSocketService;
import com.sky.container.socket.stream.StreamReadEvent;
import com.sky.container.util.CommandUtil;

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
