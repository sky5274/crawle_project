package com.sky.auth.config.client.intercepter;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


/**
 * oauth  client itercepter
 * @author 王帆
 * @date  2019年3月29日 下午8:35:37
 */
@Component
@ConfigurationProperties(prefix="security.oauth2.client")
public class ClientConfigIntercepter implements HandlerInterceptor {
	private String clientId;
	private String clientSecret;
	
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if(isOauthClient()) {
			setHeader(request,response);
		}
		return true;
	}
	
	/**
	 * 设置请求头与返回消息头
	 * @param request
	 * @param response
	 * @author 王帆
	 * @date 2019年3月29日 下午8:45:54
	 */
	private void setHeader(HttpServletRequest request, HttpServletResponse response) {
		String encode = getEncode();
		response.setHeader("Authorization", "Basic "+encode);
	}
	
	private String getEncode() {
		String temp=clientId+":"+clientSecret;
		try {
			return new String(Base64.getEncoder().encode(temp.getBytes()),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}

	/**
	 * 	是否是客户端
	 * @return
	 * @author 王帆
	 * @date 2019年3月29日 下午8:44:04
	 */
	private boolean isOauthClient() {
		return !(StringUtils.isEmpty(clientId) || StringUtils.isEmpty(clientSecret));
	}
	

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
			String url=request.getRequestURL().toString();
			try {
				request.getSession().setAttribute("contextPath",url.substring(0,url.indexOf(request.getServletPath())));
			} catch (Exception e) {
			}
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		response.setHeader("X-Frame-Options", "ALLOW-FROM");
	}

}

