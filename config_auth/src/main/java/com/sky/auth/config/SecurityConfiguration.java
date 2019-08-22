package com.sky.auth.config;

import javax.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
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
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(
				//"/auth/*",
				"/**/*.js",
				"/**/*.css",
				"/**/*.css.map",
				"/**.ico",
				"/**/*.jpg",
				"/**/*.png");
	}

	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//表单登录 方式
		http
			.formLogin()
				//登录需要经过的url请求
				//登录成功事件
				//登录失败事件
				.loginPage("/auth/login/page")
				.loginProcessingUrl("/auth/login")
				.successHandler(authenticationSuccessHandler)
				.failureHandler(authenticationFailHandler)
			.and()
				.logout()
			.and()
				//请求授权
				.authorizeRequests()
				.antMatchers("/say").hasAnyRole("USER")
				.antMatchers("/auth/**").permitAll()
				.antMatchers("/oauth/**").permitAll()
				//任何请求//需要身份认证
				.anyRequest().authenticated()
			.and()
			//关闭跨站请求防护
				.csrf().disable();
	}


	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
		.userDetailsService(userDetailsService)
		.passwordEncoder(bCryptPasswordEncoder);
	}
}

