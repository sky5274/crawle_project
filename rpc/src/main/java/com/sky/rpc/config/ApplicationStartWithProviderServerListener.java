package com.sky.rpc.config;


import java.util.Collection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import com.sky.rpc.resource.ResouceProperties;
import com.sky.rpc.util.RpcSpringBeanUtil;
import com.sky.rpc.zk.RpcConfig;
import com.alibaba.fastjson.JSON;
import com.sky.rpc.core.RpcElement;
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
		if(ProviderContant.hasProvider && !ProviderServer.isOpen()) {
			if(ResouceProperties.isSocketServer()) {
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
	
	private void registProviderNode(Class<?> clazz, RpcElement ele) {
		Class<?>[] interfaces = clazz.getInterfaces();
		int port = ProviderServer.getPort();
		//rpc provider class interface info regist into zookeeper
		for(Class<?> intf:interfaces) {
			try {
				RpcConfig.regist(ele.writeUrl(), intf.getName(),clazz.getName(), port);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//rpc provider class info regist into zookeeper
		try {
			RpcConfig.regist(ele.writeUrl(), clazz, port);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
