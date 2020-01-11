package com.sky.flow.pub.config;


import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import com.sky.rpc.resource.ResouceProperties;
import com.sky.rpc.zk.RpcConfig;
import com.sky.flow.annotation.RpcEvent;
import com.sky.flow.service.TaskFlowActionService;
import com.sky.rpc.provider.ProviderServer;
import com.sky.rpc.provider.netty.ProviderNettyServer;
import com.sky.rpc.provider.socket.ProviderSocketServer;

/**
 * spring  start listener
 * @author 王帆
 * @date  2019年6月7日 下午11:47:09
 */
@Component
public class ApplicationReadListener  implements ApplicationListener<ContextRefreshedEvent>,ApplicationContextAware{
	ApplicationContext applicationContext;
	Log log=LogFactory.getLog(getClass());
	static ProviderServer server;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext=applicationContext;
	}

	@Override
	public synchronized void onApplicationEvent(ContextRefreshedEvent event) {
		String version = ResouceProperties.getProperty("flow.server.version");
		int port = ProviderServer.getPort();
		if(!StringUtils.isEmpty(version)){
			Map<String, Object> beanmaps = applicationContext.getBeansWithAnnotation(RpcEvent.class);
			if(!beanmaps.values().isEmpty()) {
				for(Object b:beanmaps.values()) {
					try {
						RpcConfig.regist("/"+version, b, port);
					} catch (Exception e) {
						//throw new Error("flow event handel regist failed! event class:"+b.getClass().getName());
					}
				}
			}
		}
		boolean flag=false;
		try {
			Object bean = applicationContext.getBean(TaskFlowActionService.class);
			flag =bean !=null && !bean.getClass().getName().equals(TaskFlowActionService.class.getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//flow 服务或者flow  事件客户端
		if((flag || !StringUtils.isEmpty(version)) && !ProviderServer.isOpen()) {
			if(ProviderServer.isSocketServer()) {
				server=new ProviderSocketServer();
			}else {
				server=new ProviderNettyServer();
			}
			log.info("rpc flow server start in port:"+port);
			server.start();
		}
	}

}
