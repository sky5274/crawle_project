package com.sky.sm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;


@Configurable
public class WebBeanConfig {
	@Autowired(required=false)
	private RedisTemplate redisTemplate;

	@Bean
	public RedisTemplate redisTemplateInit() {
		System.err.println("redis");
		// key序列化
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		//val实例化
		redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
		// hashkey序列化
		redisTemplate.setHashKeySerializer(new StringRedisSerializer());
		//hashval实例化
		redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

		return redisTemplate;
	}
}
