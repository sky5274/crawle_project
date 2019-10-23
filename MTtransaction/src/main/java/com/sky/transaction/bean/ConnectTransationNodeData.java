package com.sky.transaction.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 	数据连接节点事务状态数据
 * @author 王帆
 * @date  2019年9月24日 上午10:45:33
 */
public class ConnectTransationNodeData implements Serializable{

	/***/
	private static final long serialVersionUID = 3147703494898756486L;
	public static Map<Integer, String> eventNameMap=new HashMap<Integer, String>();
	static {
		eventNameMap.put(0, "待处理");
		eventNameMap.put(1, "提交");
		eventNameMap.put(-1, "回滚");
	}
	
	private String nodeId;
	private String groupId;
	private String project;
	private String version;
	private Long timeOut;
	private Integer eventType;   //0:待处理；1：提交；-1：回滚
	private String eventName;
	public String getNodeId() {
		return nodeId;
	}
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public Long getTimeOut() {
		return timeOut;
	}
	public void setTimeOut(Long timeOut) {
		this.timeOut = timeOut;
	}
	public Integer getEventType() {
		return eventType;
	}
	public void setEventType(Integer eventType) {
		this.eventType = eventType;
		if(this.eventName==null) {
			this.eventName=eventNameMap.get(eventType);
		}
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public String getProject() {
		return project;
	}
	public void setProject(String project) {
		this.project = project;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
}
