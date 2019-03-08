package com.sky.cm.core;

import org.springframework.http.HttpMethod;

public enum SkyConfigRequest {
	regist("/project/regist",HttpMethod.PUT),
	property("/project/property"),
	properties("/project/query/properties"),
	load_off("/project/dump");
	private String url;
	private HttpMethod method;
	SkyConfigRequest(String url) {
		this.url=url;
	}
	SkyConfigRequest(String url,HttpMethod method) {
		this.url=url;
		this.method=method;
	}
	public String getUrl() {
		return url;
	}
	public HttpMethod getMethod() {
		return method;
	}
}
