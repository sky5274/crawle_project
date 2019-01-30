package com.sky.auth.config;

import javax.annotation.Resource;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	@Resource
	private DefAuthenticationFailHandler authenticationFailHandler;
	@Resource
	private DefAuthenticationSuccessHandler authenticationSuccessHandler;
	@Resource
	private UserDetailsService userDetailsService;
	@Resource
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//表单登录 方式
		http.formLogin()
			.loginPage("/auth/login/page")
			//登录需要经过的url请求
			.loginProcessingUrl("/auth/login")
			.successHandler(authenticationSuccessHandler)
			.failureHandler(authenticationFailHandler)
			.and()
			//请求授权
			.authorizeRequests()
			.antMatchers("/auth/**").permitAll()
			.antMatchers(
            		"/**/*.js",
                    "/**/*.css",
                    "/**/*.jpg",
                    "/**/*.png"
            		).permitAll()
			//不需要权限认证的url
			//任何请求
			.anyRequest()
			//需要身份认证
			.authenticated()
			.and()
			//关闭跨站请求防护
			.csrf().disable();
	}


	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
	}
}

