package com.sky.sm.bean;

import java.sql.Timestamp;
import java.util.List;

/**
 * 	链路对外展示数据结构
 * @author 王帆
 * @date  2019年3月20日 上午9:31:38
 */
public class TraceRecordBean {
	private String traceId;
	
	private String tracePId;

    private String groupId;

    private String url;
    
    private Integer status;

    private String type;

    private String requestBody;
    
    private String responseBody;

    private String headers;

    private String sessionId;

    private Long cost;
    
    private Timestamp time;
    
    private List<TraceRecordBean> traces;

    
	public List<TraceRecordBean> getTraces() {
		return traces;
	}

	public void setTraces(List<TraceRecordBean> traces) {
		this.traces = traces;
	}

	public String getTraceId() {
		return traceId;
	}

	public void setTraceId(String traceId) {
		this.traceId = traceId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

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

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public Long getCost() {
		return cost;
	}

	public void setCost(Long cost) {
		this.cost = cost;
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

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}
    
	
}
