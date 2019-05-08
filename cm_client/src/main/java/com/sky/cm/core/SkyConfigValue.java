package com.sky.cm.core;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.sky.pub.util.SpringUtil;

/**
 * 	项目信息收集配置
 * @author 王帆
 * @date  2019年3月5日 下午2:24:55
 */
@Component
@ConfigurationProperties(prefix="sky.client.config")
public class SkyConfigValue {
	private Long readTimeout=60*1000L;					//http读取时间
	private String location="http://localhost:9000";	//请求地址
	private String serviceName;							//项目名称
	private String profile;								//项目环境
	private boolean enablelimit=true;					//项目服务限流
	private String version;								//项目版本
	private String desc;								//项目说明
	public Long getReadTimeout() {
		return readTimeout;
	}
	public void setReadTimeout(Long readTimeout) {
		this.readTimeout = readTimeout;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getServiceName() {
		if(serviceName==null) {
			setServiceName(SpringUtil.getEvnProperty("spring.application.name"));
		}
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getProfile() {
		if(profile==null) {
			setProfile(SpringUtil.getEvnProperty("spring.profiles.active"));
		}
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public boolean isEnablelimit() {
		return enablelimit;
	}
	public void setEnablelimit(boolean enablelimit) {
		this.enablelimit = enablelimit;
	}

}
