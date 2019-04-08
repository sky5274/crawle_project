package com.sky.crawl.config;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import com.sky.auth.config.client.config.OauthClientWebSucurityConfig;

@Configuration
@EnableOAuth2Sso
@Order(-1)
public class OAuthClientConfig extends OauthClientWebSucurityConfig  {
	@Override
	public void configure(HttpSecurity http) throws Exception {
		//表单登录 方式
		http
		.headers().frameOptions().disable()
		.and()
		.authorizeRequests()
			.antMatchers("/config/main").hasAnyRole("USER")
			//不需要权限认证的url
			//任何请求
			.anyRequest()
			//需要身份认证
			.authenticated();
		System.err.println("test auth2 http");
		super.configure(http);
	}
}