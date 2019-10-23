package com.sky.transaction.datasource.bean;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * 链接资源分组
 * @author 王帆
 * @date  2019年9月22日 上午10:11:15
 */
public class ConnectSourceGroupBean implements Serializable{

	/***/
	private static final long serialVersionUID = -1434056547828089617L;
	
	private String id;
	private String parentId;
	private String groupId;
	private String threadKey;
	private Long timeOut;
	private Long expressTime;
	private Set<ConnectSourceBean<Object>> connects;
	
	public String nodeKey() {
		return String.format("%s:%s", groupId,id);
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getThreadKey() {
		return threadKey;
	}
	public void setThreadKey(String threadKey) {
		this.threadKey = threadKey;
	}
	public Set<ConnectSourceBean<Object>> getConnects() {
		return connects;
	}
	public void setConnects(Set<ConnectSourceBean<Object>> connects) {
		this.connects = connects;
	}
	public Long getTimeOut() {
		return timeOut;
	}
	public void setTimeOut(Long timeOut) {
		this.timeOut = timeOut;
	}
	
	public boolean contains(ConnectSourceBean<Object> connect) {
		if(this.connects!=null) {
			return this.connects.contains(connect);
		}
		return false;
	}
	public ConnectSourceGroupBean append(@SuppressWarnings("unchecked") ConnectSourceBean<Object> ...conns) {
		if(this.connects==null) {
			this.connects=new HashSet<ConnectSourceBean<Object>>();
		}
		for(ConnectSourceBean<Object> conn:conns) {
			if(!this.connects.contains(conn)) {
				this.connects.add(conn);
			}
		}
		return this;
	}
	public ConnectSourceGroupBean append(Collection<ConnectSourceBean<Object>> conns) {
		if(this.connects==null) {
			this.connects=new HashSet<ConnectSourceBean<Object>>();
		}
		this.connects.addAll(conns);
		return this;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	/**
	 * @return the expressTime
	 */
	public Long getExpressTime() {
		return expressTime;
	}
	/**
	 * @param expressTime the expressTime to set
	 */
	public void setExpressTime(Long expressTime) {
		this.expressTime = expressTime;
	}
	
}
