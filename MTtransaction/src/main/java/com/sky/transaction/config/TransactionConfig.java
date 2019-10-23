package com.sky.transaction.config;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.sky.transaction.control.handle.ConnectSubmitHandle;
import com.sky.transaction.control.handle.HttpSubmitPullHandle;
import com.sky.transaction.control.handle.HttpSubmitPushListenerHandel;
import com.sky.transaction.control.handle.RedisSubmitPullHandle;
import com.sky.transaction.control.handle.RedisSubmitPushListenerHandle;
import com.sky.transaction.util.SpringMTUtil;

/**
 * 	分布式服务配置
 * @author 王帆
 * @date  2019年9月26日 上午9:48:21
 */
@Configuration
@EnableCaching
public class TransactionConfig {

	@Value("${com.transacton.platform:http}")
	private String platfrom;
	@Value("${com.transacton.platType:pull}")
	private String platType;
	@Value("${com.transacton.url:http://localhost:9000}")
	private String platfromUrl;
	@Value("${com.transacton.topic:topic}")
	private String topic;
	@Value("${com.transacton.timeOut:30000}")
	private Long timeOut;

	@Bean
	public ConnectSubmitHandle getConnectSubmitHandle() {
		if("redis".equals(platfrom)) {
			if("push".equals(platType)) {
				return new RedisSubmitPushListenerHandle(timeOut);
			}else {
				return new RedisSubmitPullHandle(timeOut);
			}
		}else if("http".equals(platfrom) && !StringUtils.isEmpty(platfromUrl)){
			if("push".equals(platType)) {
				return new HttpSubmitPushListenerHandel(platfromUrl,timeOut);
			}else {
				return new HttpSubmitPullHandle(platfromUrl,timeOut);
			}
		}
		return null;
	}

	@Bean
	public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,MessageListenerAdapter listenerAdapter) {

		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		// 可以添加多个 messageListener，配置不同的交换机
		container.addMessageListener(listenerAdapter, new PatternTopic(topic));
		return container;
	}

	/**
	 * 消息监听器适配器，绑定消息处理器，利用反射技术调用消息处理器的业务方法
	 * @param receiver
	 * @return
	 */
	@Bean
	public MessageListenerAdapter listenerAdapter() {
		return new MessageListenerAdapter(new RedisSubmitPushListenerHandle(timeOut), "excuteNode");
	}

	@SuppressWarnings("rawtypes")
	@Bean
	public RedisTemplate getRedisTemplate(RedisConnectionFactory connectionFactory) {
		List<RedisTemplate> templates = SpringMTUtil.getBeans(RedisTemplate.class);
		if(CollectionUtils.isEmpty(templates)) {
			return new StringRedisTemplate(connectionFactory);
		}
		return templates.get(0);
	}
}
