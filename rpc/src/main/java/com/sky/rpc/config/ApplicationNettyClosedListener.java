package com.sky.rpc.config;


import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import com.sky.rpc.core.cilent.netty.RpcNettyClientHandel;
import io.netty.channel.Channel;

/**
 * spring  stop listener：close netty channel
 * @author 王帆
 * @date  2019年6月7日 下午11:47:09
 */

public class ApplicationNettyClosedListener  implements ApplicationListener<ContextClosedEvent>,ApplicationContextAware{
	ApplicationContext applicationContext;
	Log log=LogFactory.getLog(getClass());
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext=applicationContext;
	}

	@Override
	public void onApplicationEvent(ContextClosedEvent event) {
		Map<Long, Channel> map = RpcNettyClientHandel.getChannelMap();
		for(Channel channel:map.values()) {
			channel.close();
		}
		map.clear();
	}

}
