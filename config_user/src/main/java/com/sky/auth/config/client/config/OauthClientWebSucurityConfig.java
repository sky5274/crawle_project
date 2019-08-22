package com.sky.auth.config.client.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoRestTemplateFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.web.client.RestTemplate;

public class OauthClientWebSucurityConfig extends WebSecurityConfigurerAdapter {
	
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		 http
		 .headers().frameOptions().disable()
		 .and()
		 .csrf().disable()
         .exceptionHandling()
         .and()
         .requestMatchers().antMatchers(
 				"/do/login",
 				"/**/*.js",
 				"/**/*.css",
 				"/**/*.css.map",
 				"/**.ico",
 				"/**/*.jpg",
 				"/**/*.png")
         .and()
         .authorizeRequests()
         .antMatchers("info","/do/login","/oauth/token","*/auth/login").permitAll()
         .anyRequest().authenticated()
         .and()
         .httpBasic();
	}
	
//	@Bean
//	@ConditionalOnMissingBean(RestTemplate.class)
//	public OAuth2RestTemplate oauthRestTemple( UserInfoRestTemplateFactory UserInfoRestTemplateFactory) {
//		OAuth2RestTemplate restTemplate = UserInfoRestTemplateFactory.getUserInfoRestTemplate();
////		restTemplate.
//		return restTemplate;
//	}
}
