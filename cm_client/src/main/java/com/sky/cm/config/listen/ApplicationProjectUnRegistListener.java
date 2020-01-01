package com.sky.cm.config.listen;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;
import com.sky.cm.core.SkyConfig;


/**
 * spring web 接口注册服务监听
 * @author 王帆
 * @date  2019年3月3日 下午7:52:21
 */
@Component
public class ApplicationProjectUnRegistListener  implements ApplicationListener<ContextClosedEvent>,ApplicationContextAware{
	ApplicationContext applicationContext;
	Log log=LogFactory.getLog(getClass());
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext=applicationContext;
	}

	@Override
	public void onApplicationEvent(ContextClosedEvent event) {
		log.info("project load off");
		SkyConfig config = this.applicationContext.getBean(SkyConfig.class);
		config.dumpProject();
	}

}
