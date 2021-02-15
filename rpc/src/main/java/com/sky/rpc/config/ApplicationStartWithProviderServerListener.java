package com.sky.rpc.config;


import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import com.sky.rpc.util.RpcSpringBeanUtil;
import com.sky.rpc.zk.RpcConfig;
import com.sky.rpc.core.RpcElement;
import com.sky.rpc.core.RpcTypeContant;
import com.sky.rpc.provider.ProviderContant;
import com.sky.rpc.provider.ProviderServer;
import com.sky.rpc.provider.netty.ProviderNettyServer;
import com.sky.rpc.provider.socket.ProviderSocketServer;
import com.sky.rpc.regist.RpcRegistNodHandel;

/**
 * spring provider server start listener
 * @author 王帆
 * @date  2019年6月7日 下午11:47:09
 */
@Component
@SuppressWarnings("rawtypes")
public class ApplicationStartWithProviderServerListener  implements ApplicationListener<ContextRefreshedEvent>,ApplicationContextAware{
	ApplicationContext applicationContext;
	Log log=LogFactory.getLog(getClass());
	static ProviderServer server;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext=applicationContext;
		RpcSpringBeanUtil.setApplication(applicationContext);
	}

	@Override
	public synchronized void onApplicationEvent(ContextRefreshedEvent event) {
		Collection<RpcRegistNodHandel> rpcRegistHandels = applicationContext.getBeansOfType(RpcRegistNodHandel.class).values();
		
		String[] beannames = applicationContext.getBeanDefinitionNames();
		for(String name:beannames) {
			registRpcProviderNode(applicationContext.getBean(name).getClass(),rpcRegistHandels);
		}
		
		//flow 服务或者flow  事件客户端
		if(ProviderContant.hasProvider && !ProviderServer.isOpen() && ProviderSocketServer.canOpen()) {
			if(RpcTypeContant.rpcTypeLimit.indexOf(RpcTypeContant.getType())==0) {
				server=new ProviderSocketServer();
			}else {
				server=new ProviderNettyServer();
			}
			log.info("rpc flow server start in port:"+ProviderServer.getPort());
			server.start();
		}
	}

	/**
	 * 注册rpc provider 信息到zookeeper中
	 * @param clazz
	 * @author 王帆
	 * @param rpcRegistHandels 
	 * @date 2019年7月10日 下午5:08:24
	 */
	private void registRpcProviderNode(Class<?> clazz, Collection<RpcRegistNodHandel> rpcProviderRegistNodHandels) {
		for(RpcRegistNodHandel<?> handel:rpcProviderRegistNodHandels) {
			RpcElement ele = handel.getRpcNodeConfig(clazz);
			if(ele!=null) {
				ProviderContant.setHasProvider(true);
				// get the interfaces from the clazz, and set nodedata into zookeeper with the interface 
				registProviderNode(clazz,ele);
			}
		}
	}
	
	/**
	 * 注册rpc-bean  服务节点
	 * @param clazz
	 * @param ele
	 * @author wangfan
	 * @date 2020年1月21日 上午10:41:24
	 */
	private void registProviderNode(Class<?> clazz, RpcElement ele) {
		int port = ProviderServer.getPort();
		//rpc provider class interface info regist into zookeeper
		Class<?>[] interfaces = clazz.getInterfaces();
		if(interfaces !=null) {
			for(Class<?> intf:interfaces) {
				registProviderClassNode(ele,intf,clazz,port);
			}
		}
		
		//rpc provider supper class info regist into zookeeper
		List<Class<?>> superClasses = getAllSuperClass(clazz);
		if(!CollectionUtils.isEmpty(superClasses)) {
			for(Class<?> intf:superClasses) {
				if(!Object.class.getName().equals(intf.getName())) {
					registProviderClassNode(ele,intf,clazz,port);
				}
			}
		}
		
		//rpc provider class info regist into zookeeper
		registProviderClassNode(ele,clazz,clazz);
	}
	
	/**
	 *	具体执行注册服务提供节点事件
	 * @param ele
	 * @param intf
	 * @param clazz
	 * @param port
	 */
	private void registProviderClassNode(RpcElement ele, Class<?> intf, Class<?> clazz) {
		registProviderClassNode(ele,clazz,clazz);
	}
	
	/**
	 *	具体执行注册服务提供节点事件
	 * @param ele
	 * @param intf
	 * @param clazz
	 * @param port
	 */
	private void registProviderClassNode(RpcElement ele, Class<?> intf, Class<?> clazz, int port) {
		try {
			RpcConfig.regist(ele.writeUrl(), intf.getName(),clazz.getName());
		} catch (Exception e) {
			String msg=null;
			if(intf.getName().equals(clazz.getName())) {
				msg=String.format("regist provider class service error, class: [%s]",clazz.getName());
			}else {
				msg=String.format("regist provider class service error, class: [%s->%s]",intf.getName(),clazz.getName());
			}
			throw new IllegalArgumentException(msg, e);
		}
	}
	
	/**
	 * 获取类的父级方法
	 * @param clazz
	 * @return
	 */
	public static List<Class<?>> getAllSuperClass(Class<?> clazz) {
		List<Class<?>> supers=new LinkedList<Class<?>>();
		return getAllSuperClass(clazz,supers);
	}
	
	private static List<Class<?>> getAllSuperClass(Class<?> clazz, List<Class<?>> supers) {
		if(!Object.class.getName().equals(clazz.getName())) {
			Class<?> superClass = clazz.getSuperclass();
			supers.add(superClass);
			return getAllSuperClass(superClass,supers);
		}
		return supers;
	}
}
