package com.sky.auth.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.sky.auth.config.entity.UserDetailEntitiy;
import com.sky.auth.config.util.UserUtil;
import com.sky.pub.ResultCode;
import com.sky.pub.ResultUtil;

@Component
@ConditionalOnBean(ClientDetailsService.class)
public class DefAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	private Log logger = LogFactory.getLog(getClass());

	@Autowired
	private ClientDetailsService clientDetailsService;

	@Autowired
	private AuthorizationServerTokenServices authorizationServerTokenServices;


	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		logger.info("登录成功!");
		if(isAjax(request) || isJson(request)) {
			String header = request.getHeader("Authorization");
			String clientId =null;
			@SuppressWarnings("unused")
			String clientSecret = null;
			if(header==null) {
				clientId= request.getParameter("clientId");
				if(StringUtils.isEmpty(clientId)) {
					clientId=request.getParameter("client_id");
				}
				if(StringUtils.isEmpty(clientId) ) {
					response.getWriter().write(JSON.toJSONString(ResultUtil.getFailed(ResultCode.VALID,"请求投中无client信息")));
					return;
				}
			}else if (!header.startsWith("Basic")) {
					response.getWriter().write(JSON.toJSONString(ResultUtil.getFailed(ResultCode.VALID,"请求投中无client信息")));
					return;
				}else {
					logger.info("登录成功!,Authorization"+header);
					String[] tokens = this.extractAndDecodeHeader(header, request);
					assert tokens.length == 2;

					//获取clientId 和 clientSecret
					clientId = tokens[0];
					clientSecret = tokens[1];
				}


			//获取 ClientDetails
			ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);

			if (clientDetails == null){
				response.getWriter().write(JSON.toJSONString(ResultUtil.getFailed(ResultCode.VALID,"clientId 不存在"+clientId)));
				return;
				//判断  方言  是否一致
			}

			//密码授权 模式, 组建 authentication
			TokenRequest tokenRequest = new TokenRequest(new HashMap<>(),clientId,clientDetails.getScope(),"password");

			OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);
			OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request,authentication);
			
			OAuth2AccessToken token = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);
			Map<String , Object> resultMap=new HashMap<>();
			resultMap.put("token", token);
			resultMap.put("user", authentication.getPrincipal());
			
			//保存用户信息
			UserUtil.saveUser(JSON.parseObject(JSON.toJSONString(authentication.getPrincipal()), UserDetailEntitiy.class));
			//判断是json 格式返回 还是 view 格式返回
			//将 authention 信息打包成json格式返回
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(JSON.toJSONString(ResultUtil.getOk(ResultCode.OK,resultMap)));
		}else {
			super.onAuthenticationSuccess(request, response, authentication);
		}

	}

	/**
	 * 判断是否ajax请求
	 *
	 * @param request 请求对象
	 * @return true:ajax请求  false:非ajax请求
	 */
	private boolean isAjax(HttpServletRequest request) {
		return "XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"));
	}
	/**
	 * 是否是json格式请求
	 * @author  wangfan
	 * @time  2018年10月29日 下午4:14:30
	 * @param request
	 * @return
	 */
	private boolean isJson(HttpServletRequest request) {
		return "application/json".equalsIgnoreCase(request.getHeader("Content-Type"));
	}


	/**
	 *	 解码请求头
	 */
	private String[] extractAndDecodeHeader(String header, HttpServletRequest request) throws IOException {
		byte[] base64Token = header.substring(6).getBytes("UTF-8");

		byte[] decoded;
		try {
			decoded = Base64.decode(base64Token);
		} catch (IllegalArgumentException var7) {
			throw new BadCredentialsException("Failed to decode basic authentication token");
		}

		String token = new String(decoded, "UTF-8");
		int delim = token.indexOf(":");
		if (delim == -1) {
			throw new BadCredentialsException("Invalid basic authentication token");
		} else {
			return new String[]{token.substring(0, delim), token.substring(delim + 1)};
		}
	}
}
