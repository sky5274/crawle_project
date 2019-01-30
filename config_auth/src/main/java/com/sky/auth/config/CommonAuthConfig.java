package com.sky.auth.config;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.InMemoryClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CommonAuthConfig {
	@Value("${own.config.client.id:dev}")
	private String clientId;
	@Resource
	private RedisConnectionFactory redisConnectionFactory;
	@Resource
	private DataSource dataSource;

	@Bean
	public RedisTokenStore redisTokenStore(){
		Assert.notNull(redisConnectionFactory,"redis connect need not null");
		return new RedisTokenStore(redisConnectionFactory);
	}
    //token存储数据库
//  @Bean
//  public JdbcTokenStore jdbcTokenStore(){
//      return new JdbcTokenStore(getDataSource());
//  }
	
	@Primary
    @Bean
    public DefaultTokenServices defaultTokenServices() {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(redisTokenStore());
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setAccessTokenValiditySeconds(60*60*12); // token有效期自定义设置，默认12小时
        tokenServices.setRefreshTokenValiditySeconds(60 * 60 * 24 * 7);//默认30天，这里修改
        return tokenServices;
    }

	@Bean
	public ClientDetailsService clientDetails()  {
		return new JdbcClientDetailsService(dataSource);
	}
	@Bean
	public RestTemplate getRestTemplate()  {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setErrorHandler(new ThrowErrorHandler());
		return restTemplate;
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
