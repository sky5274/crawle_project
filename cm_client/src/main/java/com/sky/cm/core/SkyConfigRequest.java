package com.sky.cm.core;

import org.springframework.http.HttpMethod;

public enum SkyConfigRequest {
	regist("/project/regist",HttpMethod.PUT),
	load_off("/project/dump"),
	property("/property/get"),
	properties("/property/by/project"),
	limit("/http/limit"),
	trace_start("/http/trace/start",HttpMethod.POST),
	trace_end("/http/trace/end",HttpMethod.POST)
	;
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
