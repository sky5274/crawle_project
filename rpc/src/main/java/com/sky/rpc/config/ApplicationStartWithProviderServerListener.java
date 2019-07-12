package com.sky.rpc.config;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import com.sky.rpc.resource.ResouceProperties;
import com.sky.rpc.util.RpcSpringBeanUtil;
import com.sky.rpc.provider.ProviderContant;
import com.sky.rpc.provider.ProviderServer;
import com.sky.rpc.provider.netty.ProviderNettyServer;
import com.sky.rpc.provider.socket.ProviderSocketServer;

/**
 * spring provider server start listener
 * @author 王帆
 * @date  2019年6月7日 下午11:47:09
 */
public class ApplicationStartWithProviderServerListener  implements ApplicationListener<ContextRefreshedEvent>,ApplicationContextAware{
	ApplicationContext applicationContext;
	Log log=LogFactory.getLog(getClass());
	static ProviderServer server;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext=applicationContext;
		RpcSpringBeanUtil.setApplicationContext(applicationContext);
	}

	@Override
	public synchronized void onApplicationEvent(ContextRefreshedEvent event) {
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

}
