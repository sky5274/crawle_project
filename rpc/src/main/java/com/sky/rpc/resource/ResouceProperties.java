package com.sky.rpc.resource;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import com.sky.rpc.provider.ProviderServer;
import com.sky.rpc.util.RpcSpringBeanUtil;

/**
 * 资源文件读写信息
 *<p>Title: ResourceProperties.java</p>
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
	
	/**
	 * 获取property 从system、rpc.properties、spring property中获取
	 * @param key
	 * @return
	 * @author 王帆
	 * @date 2019年7月18日 上午10:55:28
	 */
	public static String getProperty(String key) {
		String value=System.getProperty(key);
		if(StringUtils.isEmpty(value) && getDefProperties()!=null) {
			value=getDefProperties().getProperty(key);
		}
		if(StringUtils.isEmpty(value)) {
			if(RpcSpringBeanUtil.getApplicationContext()!=null) {
				value=RpcSpringBeanUtil.getEvnProperty(key);
			}
		}
		return value;
	}
}
