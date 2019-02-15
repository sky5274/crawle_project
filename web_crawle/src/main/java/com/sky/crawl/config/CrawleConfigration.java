package com.sky.crawl.config;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * crawl  配置
 * @author 王帆
 * @date  2018年12月26日 下午4:50:12
 */
@Configuration
public class CrawleConfigration {
	
	@Bean
	public Validator getValidation() {
		ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
       return vf.getValidator();
	}
}
