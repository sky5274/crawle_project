package com.sky.task.init;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.sky.task.core.RpcConfig;
import com.sky.task.job.AbstractBaseJobcClient;

@Component
public class TaskJobAutoApplicationReadListener implements ApplicationListener<ApplicationPreparedEvent>,ApplicationContextAware{
	ApplicationContext applicationContext;
	String port;
	RpcConfig rpcConfig;
	private Log log=LogFactory.getLog(getClass());

	@Override
	public void onApplicationEvent(ApplicationPreparedEvent event) {
		log.info("task job regist node");
		Map<String, AbstractBaseJobcClient> jobClazzMaps = this.applicationContext.getBeansOfType(AbstractBaseJobcClient.class);
		port = this.applicationContext.getEnvironment().getProperty("server.port");
		rpcConfig = applicationContext.getBean(RpcConfig.class);
		for(String key:jobClazzMaps.keySet()) {
			try {
				regist(key,jobClazzMaps.get(key));
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	private void regist(String key, AbstractBaseJobcClient abstractBaseJobcClient) throws NumberFormatException, Exception {
		//注册  服务客户端
		rpcConfig.regist("/"+abstractBaseJobcClient.getClass().getName(), Integer.parseInt(port), "/"+key);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext=applicationContext;
	}

}

