package com.sky.cm.bean;

import java.io.Serializable;

/**
 * http request trace info
 * @author 王帆
 * @date  2019年3月14日 下午2:11:02
 */
public class RequestTraceBean implements Serializable{
	/***/
	private static final long serialVersionUID = 1L;
	private String url;
	private String type;
	private String requestBody;
	private String responseBody;
	private String headers;
	private String groupId;
	private String traceId;
	private String tracePId;
	private String sessionId;
	private Integer status;
	private Integer cost;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getRequestBody() {
		return requestBody;
	}
	public void setRequestBody(String requestBody) {
		this.requestBody = requestBody;
	}
	public String getHeaders() {
		return headers;
	}
	public void setHeaders(String headers) {
		this.headers = headers;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getTraceId() {
		return traceId;
	}
	public void setTraceId(String traceId) {
		this.traceId = traceId;
	}
	public Integer getCost() {
		return cost;
	}
	public void setCost(Integer cost) {
		this.cost = cost;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getTracePId() {
		return tracePId;
	}
	public void setTracePId(String tracePId) {
		this.tracePId = tracePId;
	}
	public String getResponseBody() {
		return responseBody;
	}
	public void setResponseBody(String responseBody) {
		this.responseBody = responseBody;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
}
