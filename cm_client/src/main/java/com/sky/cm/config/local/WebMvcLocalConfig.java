package com.sky.cm.config.local;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
public class WebMvcLocalConfig extends WebMvcConfigurerAdapter{
	
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localInterceptor()).addPathPatterns("/**");
    }
	
	@Bean
	public LocaleResolver localResolver() {
		return new SessionLocaleResolver();
	}
	
	@Bean
	public LocaleControllerInterceptor localInterceptor() {
		LocaleControllerInterceptor localInterceptor = new LocaleControllerInterceptor();
		 localInterceptor.setLocaleResolver(localResolver());
		 return localInterceptor;
	}
}
