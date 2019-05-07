package com.sky.auth.config.client.controller;


import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Base64;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoRestTemplateFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSON;
import com.sky.auth.config.entity.UserDetailEntitiy;
import com.sky.auth.config.util.UserUtil;
import com.sky.pub.Result;

@RestController
@ConditionalOnBean(UserInfoRestTemplateFactory.class)
public class OauthClientController {
	@Autowired
	private com.sky.auth.config.client.service.OauthClientService OauthClientService;
	@Value("${security.basic.url:http://localhost:8980}")
	private String baseUrl;
	
	@Value("${security.oauth2.client.client-id}")
	private String clientId;
	@Value("${security.oauth2.client.client-secret}")
	private String clientSecret;
	
	public static String authorityCode=null;
	
	@RequestMapping("info")
	public Result<Principal> getUserInfo() {
		Result<Principal> userInfo = OauthClientService.getUserInf(getAutorityCode());
		if(StringUtils.isEmpty(userInfo)) {
			UserUtil.saveUser(JSON.parseObject(JSON.toJSONString(userInfo.getData()), UserDetailEntitiy.class));
		}
		return userInfo;
	}
	private String getEncode() {
		String temp=clientId+":"+clientSecret;
		try {
			return new String(Base64.getEncoder().encode(temp.getBytes()),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}
	
	private String getAutorityCode() {
		if(StringUtils.isEmpty(authorityCode)) {
			authorityCode="Basic "+getEncode();
		}
		return  authorityCode;
	}
	
	@RequestMapping("/do/token")
	public String getToken(UserDetailEntitiy user) {
		return OauthClientService.getToken(getAutorityCode());
	}
	@RequestMapping("/do/login")
	public Result<Map<String, Object>> getUserLogin(UserDetailEntitiy user) {
		return OauthClientService.doLogin(getAutorityCode(), user.getUsername(), user.getPassword());
	}
}
