package com.sky.auth.config.client.service;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoRestTemplateFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sky.auth.config.entity.AuthorityContainerBean;
import com.sky.pub.Result;

/**
 * oauth  请求fegin服务
 * @author 王帆
 * @date  2019年4月15日 下午4:10:44
 */
@FeignClient("${security.oauth.name:config_auth}")
@ConditionalOnBean(UserInfoRestTemplateFactory.class)
public interface OauthClientService {
	
	@RequestMapping("/user/info")
	public Result<Principal> getUserInf(@RequestHeader("Authorization") String Authorization);
	
	@RequestMapping("/oauth/token")
	public String getToken(@RequestHeader("Authorization") String Authorization);
	
	@RequestMapping(value="/auth/login",method=RequestMethod.POST)
	public Result<Map<String, Object>> doLogin(@RequestHeader("Authorization") String Authorization,String username,String password);
	
	@RequestMapping(value="/permission/query/codes",method=RequestMethod.POST)
	public Result<AuthorityContainerBean> getAuthorityContainer(@RequestHeader("Authorization") String Authorization,@RequestBody List<String> authorities);
}
