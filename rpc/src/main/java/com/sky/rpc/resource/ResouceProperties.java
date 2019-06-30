package com.sky.rpc.resource;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.springframework.util.ResourceUtils;

/**
 * 资源文件读写信息
 *<p>Title: ResouceProperties.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2017</p>
 * <p>Company: </p>
 * @author sky
 * @date 2018年10月28日
 */
public class ResouceProperties {
	static Properties properties;
	
	public static Properties getDefProperties() {
		if(properties==null) {
			try {
				properties=new Properties();
				properties.load(new FileInputStream(ResourceUtils.getFile("classpath:rpc.properties")));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return properties;
	}
	
	public static String getProperty(String key) {
		return getDefProperties().getProperty(key);
	}
	
	public static boolean isSocketServer() {
		return "socket".equals(getProperty("rpc.server.type"));
	}
}
