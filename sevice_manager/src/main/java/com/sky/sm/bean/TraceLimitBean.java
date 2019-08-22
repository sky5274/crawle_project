package com.sky.sm.bean;

import com.sky.pub.BaseTableEntity;

/**
 * limit配置信息
 * @author 王帆
 * @date  2019年5月8日 下午5:26:07
 */
public class TraceLimitBean extends BaseTableEntity{
	/***/
	private static final long serialVersionUID = -3686501024646747480L;
	//项目名称
	private String serverName;
	//项目环境
	private String profile;
	//项目版本
	private String serverVersion;
	private String sessionId;
	//路径
	private String url;
	//匹配路径
	private String matchUrl;
	//限制5次数
	private Integer count=5;
	private Integer limitCount=5;
	//持续时间
	private Integer priod=1;
	/**
	 * 项目-环境-版本相似等级
	 */
	private String likeLevel;
	//是否是全局配置
	private Integer isGlobal;
	public boolean isLimit() {
		return count>0 && count==1;
	}
	
	public TraceLimitBean() {}
	
	public TraceLimitBean(int priod,int count) {
		this.priod=priod;
		this.setLimitCount(count);
		this.count=count;
	}
	public String getKey() {
		StringBuilder key=new StringBuilder();
		key.append(getServerName()).append("-").append(getProfile()).append("-").append(getVersion());
		key.append("/").append(getSessionId());
		key.append("$").append(url);
		return key.toString();
	}
	public int getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public int getPriod() {
		return priod;
	}
	public void setPriod(Integer priod) {
		this.priod = priod;
	}

	public int getLimitCount() {
		return limitCount;
	}

	public void setLimitCount(Integer limitCount) {
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

	public String getMatchUrl() {
		return matchUrl;
	}

	public void setMatchUrl(String matchUrl) {
		this.matchUrl = matchUrl;
	}
	public Integer getIsGlobal() {
		return isGlobal;
	}

	public void setIsGlobal(Integer isGlobal) {
		this.isGlobal = isGlobal;
	}

	public String getLikeLevel() {
		return likeLevel;
	}

	public void setLikeLevel(String likeLevel) {
		this.likeLevel = likeLevel;
	}
}
