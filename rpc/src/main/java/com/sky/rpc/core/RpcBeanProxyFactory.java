package com.sky.rpc.core;


import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.zookeeper.KeeperException;
import org.springframework.beans.factory.FactoryBean;
import com.alibaba.fastjson.JSON;
import com.sky.rpc.zk.RpcConfig;
import com.sky.rpc.zk.RpcConfig.nodeData;

public class RpcBeanProxyFactory<T> implements FactoryBean<T> {
	private static  Log log=LogFactory.getLog(RpcBeanProxyFactory.class);
	private Class<T> interfaceClass;
	private RpcElement node;
	public Class<T> getInterfaceClass() {
		return interfaceClass;
	}
	public void setInterfaceClass(Class<T> interfaceClass) {
		this.interfaceClass = interfaceClass;
	}
	public T getObject() throws Exception {
		nodeData ip = getRandomIp();
		if(ip!=null) {
			log.debug("rpc proxy get random server:"+JSON.toJSONString(ip));
			return (T) RpcProxy.getRemoteProxyObj(interfaceClass,new InetSocketAddress(ip.getIp(),ip.getPort()),ip.getClassName(),node.getTimeout());
		}else {
			throw new Exception("rpc system get no interface server");
		}
	}

	public Class<?> getObjectType() {
		return interfaceClass;
	}

	public boolean isSingleton() {
		// 单例模式
		return true;
	}
	
	public nodeData getRandomIp() throws KeeperException, InterruptedException, IOException {
		return RpcConfig.getRandomServer(node.writeUrl()+"/"+interfaceClass.getName(),node.getTarget());
	}
	public RpcElement getNode() {
		return node;
	}
	public void setNode(RpcElement node) {
		this.node = node;
	}
}
