package com.sky.rpc.core;

import org.springframework.util.StringUtils;

public class RpcElement {
	private String id;
	private String interfaceName;
	private String target;
	private String group;
	private String version;
	private int timeout;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String writeUrl() {
		StringBuffer sbf=new StringBuffer();
		StringBuffer err=new StringBuffer();
		if(StringUtils.isEmpty(group)) {
			err.append(" group");
		}else {
			sbf.append("/"+group);
		}
		if(StringUtils.isEmpty(version)) {
			err.append(" version");
		}else {
			sbf.append("/"+version);
		}
//		if(!StringUtils.isEmpty(interfaceName)) {
//			sbf.append("/").append(interfaceName);
//		}
//		if(!StringUtils.isEmpty(target)) {
//			sbf.append("/").append(target);
//		}
		if(err.toString().length()>0) {
			throw new IllegalArgumentException(err.toString()+" must not empty");
		}
		return sbf.toString();
	}
	public String getInterfaceName() {
		return interfaceName;
	}
	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public int getTimeout() {
		return timeout;
	}
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
}
