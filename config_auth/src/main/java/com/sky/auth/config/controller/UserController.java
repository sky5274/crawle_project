package com.sky.auth.config.controller;

import java.security.Principal;
import java.util.Enumeration;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.sky.auth.config.core.AuthTokenDefined;
import com.sky.auth.config.entity.UserDetailEntitiy;
import com.sky.auth.config.entity.UserEntity;
import com.sky.auth.config.service.UserService;
import com.sky.pub.Result;
import com.sky.pub.ResultCode;
import com.sky.pub.ResultUtil;
import com.sky.pub.common.exception.ResultException;

@RestController
@RequestMapping("auth")
public class UserController {
	@Resource
	private UserService userService;
	@Resource
	RestTemplate restTemplate;
	@Value("${own.config.client.id:dev}")
	private String clientId;
	@Value("${own.config.client.secret:dev}")
	private String clientSecret;

	@GetMapping("/info")
	public Result<Principal> getUserInfo(Principal user) {
		return ResultUtil.getOk(ResultCode.OK, user);
	}


	@RequestMapping("/login/regist")
	public ModelAndView  registPage(HttpServletRequest req) {
		return new ModelAndView("regist");
	}
	@RequestMapping("/login/page")
	public ModelAndView  loginPage(HttpServletRequest req) {
		ModelAndView view =new ModelAndView("login");
		Enumeration<String> names = req.getParameterNames();
		while(names.hasMoreElements()) {
			String name = names.nextElement();
			view.addObject(name, req.getParameter(name));
		}
		return view;
	}

	@RequestMapping("/login")
	public Result<UserDetailEntitiy> loginUser(UserEntity user,HttpServletRequest req) throws ResultException{

		String url="http://" + req.getServerName() //服务器地址  
		+ ":"   
		+ req.getServerPort()           //端口号 
		+"/oauth/token";
		MultiValueMap<String, String> requestEntity = new LinkedMultiValueMap<>();
		requestEntity.add("grant_type", "password");
		requestEntity.add("username", user.getUsername());
		requestEntity.add("password", user.getPassword());
		requestEntity.add("client_id", clientId);
		requestEntity.add("client_secret", clientSecret);
		//获取token
		ResponseEntity<String> response = restTemplate.postForEntity(url,requestEntity, String.class);
		String res_result = response.getBody();
		if(res_result.contains("\"error\"")&& res_result.contains("error_description")) {
			return ResultUtil.getFailed(ResultCode.FAILED, JSON.parseObject(res_result).get("error_description").toString());
		}
		UserDetailEntitiy userinfo = userService.login(user);
		userinfo.setToken(JSON.parseObject(res_result,AuthTokenDefined.class));
		return  ResultUtil.getOk(ResultCode.OK, userinfo);
	}

	@RequestMapping("/user/regist")
	public Result<UserEntity> registUser(UserEntity  user) throws ResultException{
		return ResultUtil.getOk(ResultCode.OK, userService.regist(user));
	}
}
