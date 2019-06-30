package com.sky.rpc.proxy;

import java.net.InetSocketAddress;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.util.StringUtils;

import com.sky.rpc.resource.ResouceProperties;
import com.sky.rpc.base.RpcIp;
import com.sky.rpc.core.RpcProxy;

public class ProxyFactory<T> implements FactoryBean<T> {
	private Class<T> interfaceClass;
	private String interfaceImpl;
	private String mapperUrl;
	static	RpcIp ip = null;
	public Class<T> getInterfaceClass() {
		return interfaceClass;
	}
	
	public void setInterfaceClass(Class<T> interfaceClass) {
		this.interfaceClass = interfaceClass;
	}
	public T getObject() throws Exception {
		RpcIp defip = getDefServer();
		if(defip!=null) {
			return (T) RpcProxy.getRemoteProxyObj(interfaceClass,new InetSocketAddress(defip.getHost(),defip.getPort()),interfaceImpl);
		}else {
			return null;
		}
	}
	
	public RpcIp getDefServer() {
		if(ip==null) {
			String host = ResouceProperties.getProperty("flow.server.host");
			if(!StringUtils.isEmpty(host)) {
				ip=new RpcIp();
				ip.setHost(host);
				ip.setPort(Integer.parseInt(ResouceProperties.getProperty("flow.server.port")));
			}
		}
		return ip;
	}

	public Class<?> getObjectType() {
		return interfaceClass;
	}

	public boolean isSingleton() {
		// 单例模式
		return true;
	}
	
	public String getMapperUrl() {
		return mapperUrl;
	}
	public void setMapperUrl(String mapperUrl) {
		this.mapperUrl = mapperUrl;
	}
	public String getInterfaceImpl() {
		return interfaceImpl;
	}
	public void setInterfaceImpl(String interfaceImpl) {
		this.interfaceImpl = interfaceImpl;
	}
}