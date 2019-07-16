package com.sky.flow.rpc.proxy;

import java.net.InetSocketAddress;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.util.StringUtils;

import com.sky.rpc.resource.ResouceProperties;
import com.sky.rpc.base.RpcIp;
import com.sky.rpc.core.RpcProxy;

public class FlowServceProxyFactory<T> implements FactoryBean<T> {
	private Class<T> interfaceClass;
	static	RpcIp ip = null;
	public Class<T> getInterfaceClass() {
		return interfaceClass;
	}
	
	public void setInterfaceClass(Class<T> interfaceClass) {
		this.interfaceClass = interfaceClass;
	}
	public T getObject() throws Exception {
		RpcIp defip = getDefServer();
		return (T) RpcProxy.getRemoteProxyObj(interfaceClass,new InetSocketAddress(defip.getHost(),defip.getPort()));
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

	@Override
	public Class<T> getObjectType() {
		return interfaceClass;
	}

	public boolean isSingleton() {
		// 单例模式
		return false;
	}
}