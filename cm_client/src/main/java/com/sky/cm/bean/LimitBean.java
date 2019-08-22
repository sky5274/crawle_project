package com.sky.cm.bean;

import java.io.Serializable;

import com.sky.cm.core.SkyConfigValue;

public class LimitBean implements Serializable{
	/***/
	private static final long serialVersionUID = -3686501024646747480L;
	
	
	private String serverName;
	private String profile;
	private String serverVersion;
	private String sessionId;
	private String url;
	private int count=5;
	private int limitCount=5;
	private int priod=1;
	public boolean isLimit() {
		return count>0 && count==1;
	}
	
	public LimitBean() {}
	public LimitBean(SkyConfigValue config,String url,String sessionId) {
		this.sessionId=sessionId;
		this.url=url;
		this.serverName=config.getServiceName();
		this.profile=config.getProfile();
		this.serverVersion=config.getVersion();
	}
	
	public LimitBean(int priod,int count) {
		this.priod=priod;
		this.setLimitCount(count);
		this.count=count;
	}
	public String getKey() {
		StringBuilder key=new StringBuilder();
		key.append(getServerName()).append("-").append(getProfile()).append("-").append(getServerVersion());
		key.append("/").append(getSessionId());
		key.append("$").append(url);
		return key.toString();
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getPriod() {
		return priod;
	}
	public void setPriod(int priod) {
		this.priod = priod;
	}

	public int getLimitCount() {
		return limitCount;
	}

	public void setLimitCount(int limitCount) {
		this.limitCount = limitCount;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getServerVersion() {
		return serverVersion;
	}

	public void setServerVersion(String serverVersion) {
		this.serverVersion = serverVersion;
	}
}
