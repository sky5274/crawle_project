package com.sky.auth.config.client.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import com.sky.auth.config.client.intercepter.ClientConfigIntercepter;

@Configuration
@ConfigurationProperties(prefix="security.oauth2.client")
public class WebIntercepterConfig  extends WebMvcConfigurerAdapter{
	private String clientId;
	private String clientSecret;
	@Autowired
	private ClientConfigIntercepter clientConfigIntercepter;
	
	@Override
    public void addInterceptors(InterceptorRegistry registry) {
		if(!StringUtils.isEmpty(clientId) && !StringUtils.isEmpty(clientSecret)) {
			registry.addInterceptor(clientConfigIntercepter).addPathPatterns("/**");
		}
    }
}
