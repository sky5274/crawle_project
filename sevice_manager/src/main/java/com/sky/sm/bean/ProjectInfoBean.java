package com.sky.sm.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 挂载项目信息
 * @author 王帆
 * @date  2019年3月4日 下午3:27:38
 */
public class ProjectInfoBean implements Serializable{

	/***/
	private static final long serialVersionUID = 1L;
	private String serviceName;
	private String ip;
	private String desc;
	private String port;
	private String profile;
	private String version;
	private List<HttpUrlInfo> urls;
	
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getProfile() {
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
	public List<HttpUrlInfo> getUrls() {
		return urls;
	}
	public void setUrls(List<HttpUrlInfo> urls) {
		this.urls = urls;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public static class HttpUrlInfo implements Serializable{
		/***/
		private static final long serialVersionUID = 2L;
		private List<String> types;
		private List<String> paths;
		private HttpMethodInfo method;
		public List<String> getTypes() {
			return types;
		}
		public void setTypes(List<String> types) {
			this.types = types;
		}
		public List<String> getPaths() {
			return paths;
		}
		public void setPaths(List<String> paths) {
			this.paths = paths;
		}
		public HttpMethodInfo getMethod() {
			return method;
		}
		public void setMethod(HttpMethodInfo method) {
			this.method = method;
		}
		
	}
	public static class HttpMethodInfo implements Serializable{
		/***/
		private static final long serialVersionUID = 2L;
		private String methodName;
		private List<String> params;
		private String returnType;
		public String getMethodName() {
			return methodName;
		}
		public void setMethodName(String methodName) {
			this.methodName = methodName;
		}
		public List<String> getParams() {
			return params;
		}
		public void setParams(List<String> params) {
			this.params = params;
		}
		public String getReturnType() {
			return returnType;
		}
		public void setReturnType(String returnType) {
			this.returnType = returnType;
		}
	}
}
