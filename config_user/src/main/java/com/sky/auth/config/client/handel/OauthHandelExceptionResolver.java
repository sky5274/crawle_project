package com.sky.auth.config.client.handel;

import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import com.sky.auth.config.client.controller.OauthClientController;
import com.sky.auth.config.client.service.OauthClientService;
import com.sky.auth.config.entity.AuthorityBean;
import com.sky.auth.config.entity.AuthorityContainerBean;
import com.sky.auth.config.entity.UserDetailEntitiy;
import com.sky.auth.config.util.UserUtil;
import com.sky.pub.Result;
import com.sky.pub.ResultUtil;
import com.sky.pub.util.ListUtils;
import com.sky.pub.util.SpringUtil;
import com.sky.pub.web.exception.SpringHandlerExceptionResolver;

@Configuration
@Order(Integer.MIN_VALUE)
public class OauthHandelExceptionResolver extends SpringHandlerExceptionResolver{

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		if(ex instanceof AccessDeniedException) {
			if(handler instanceof HandlerMethod ){
				if (handler != null) {
					HandlerMethod handlerMethod = (HandlerMethod)handler;
					Method method = handlerMethod.getMethod();
					if (method != null) {
						//如果定义了ExceptionHandler则返回相应的Map中的数据
						PreAuthorize auth = method.getAnnotation(PreAuthorize.class);
						ResponseBody annot = method.getAnnotation(ResponseBody.class);
						boolean isRespondBody=false;
						if(annot!=null){
							isRespondBody=true;
						}
						if(auth!=null){
							return super.errorResult("oauth_2",getWarnTips(auth,ex), request.getPathInfo(), ex, request, isRespondBody);
						}
						Secured secured = method.getAnnotation(Secured.class);
						if(secured !=null) {
							return super.errorResult("oauth_2",getWarnTips(secured,ex), request.getPathInfo(), ex, request, isRespondBody);
						}
					}
				}
			}
		}
		return null;
	}
	
	private String getWarnTips(Secured secured,Exception ex) {
		return getTips(getOutAuthorityContainerBean(secured.value()),ex);
	}

	private String getWarnTips(PreAuthorize auth, Exception ex) {
		String[] authCodes = getAuthorityCodes(auth.value());
		return getTips(getOutAuthorityContainerBean(authCodes),ex);
	}
	
	/**
	 * 根据获取缺失权限与异常，确定提示实现
	 * @param authorityContainerBean
	 * @param ex
	 * @return
	 * @author 王帆
	 * @date 2019年4月17日 下午3:33:48
	 */
	private String getTips(AuthorityContainerBean authorityContainerBean, Exception ex) {
		if(authorityContainerBean==null) {
			return ex.getMessage();
		}
		StringBuilder str=new StringBuilder("您未授权  ");
		List<String> templist=new LinkedList<>();
		boolean flag=true;
		if(!ListUtils.isEmpty(authorityContainerBean.getAuths())) {
			for(AuthorityBean au:authorityContainerBean.getAuths()) {
				templist.add(au.getName());
			}
			str.append("权限：").append(String.join(",", templist));
			templist.clear();
			flag=false;
		}
		if(!ListUtils.isEmpty(authorityContainerBean.getRoles())) {
			for(AuthorityBean role:authorityContainerBean.getRoles()) {
				templist.add(role.getName());
			}
			str.append("角色：").append(String.join(",", templist));
			flag=false;
		}
		return flag?str.toString():ex.getMessage();
	}
	
	/**
	 * 获取缺失的权限信息
	 * @param authCodes
	 * @return
	 * @author 王帆
	 * @date 2019年4月16日 下午2:33:58
	 */
	private AuthorityContainerBean getOutAuthorityContainerBean(String[] authCodes) {
		Result<AuthorityContainerBean> resultCode = getOutAuthorityDetail(getOutAuthorities(authCodes));
		ListUtils.isEmpty(resultCode.getData().getAuths());
		if(!resultCode.isSuccess() || (!ListUtils.isEmpty(resultCode.getData().getAuths()) && !ListUtils.isEmpty(resultCode.getData().getRoles()))) {
			return null;
		}else {
			return resultCode.getData();
		}
	}
	
	/**
	 * 获取权限编码的信息
	 * @param codes
	 * @author 王帆
	 * @return 
	 * @date 2019年4月16日 下午1:31:43
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Result<AuthorityContainerBean> getOutAuthorityDetail(List<String> codes) {
		Result<AuthorityContainerBean> result=null;
		if(!ListUtils.isEmpty(codes)) {
			HttpServletRequest req = UserUtil.getHttpRequest();
			Enumeration<String> headerNames = req.getHeaderNames();
			HttpHeaders requestHeaders = new HttpHeaders();
			while(headerNames.hasMoreElements()) {
				String header=headerNames.nextElement();
				 requestHeaders.add(header, req.getHeader(header));
			}
			HttpEntity requestEntity = new HttpEntity(codes, requestHeaders);
			String path="http://"+req.getServerName()+":"+req.getServerPort()+"/"+req.getContextPath()+"/permission/query/codes";
			try {
				OauthClientService oauthClientService=SpringUtil.getBean(OauthClientService.class);
				if(oauthClientService !=null) {
					 result= oauthClientService.getAuthorityContainer(OauthClientController.authorityCode, codes);
				}else {
					RestTemplate restTemplate=new RestTemplate();
					Result temp = restTemplate.postForEntity(path, requestEntity, Result.class).getBody();
					result=ResultUtil.copy(temp).ok();
					result.setData(JSON.parseObject(JSON.toJSONString(temp.getData()),AuthorityContainerBean.class));
				}
			} catch (Exception e) {
				RestTemplate restTemplate=new RestTemplate();
				Result temp = restTemplate.postForEntity(path, requestEntity, Result.class).getBody();
				result=ResultUtil.copy(temp).ok();
				result.setData(JSON.parseObject(JSON.toJSONString(temp.getData()),AuthorityContainerBean.class));
			}
			
		}
		return result;
	}
	
	/**
	 * 根据权限编码获取用户的权限编码
	 * @param codes
	 * @return
	 * @author 王帆
	 * @date 2019年4月15日 下午4:00:50
	 */
	private List<String> getOutAuthorities(String[] codes) {
		List<String> outAuthorities=new LinkedList<>();
		if(codes !=null && codes.length>0) {
			UserDetailEntitiy user = UserUtil.getUser();
			List<String> authorities=new LinkedList<>();
			for(GrantedAuthority au:user.getAuthorities()) {
				authorities.add(au.getAuthority());
			}
			for(String code:codes) {
				if(!StringUtils.isEmpty(code) && !authorities.contains(code)) {
					outAuthorities.add(code);
				}
			}
		}
		return outAuthorities;
	}
	
	/**
	 * 截取PreAuthorize 定义的权限控制编码，并删除引号
	 * @param controllerCode
	 * @return
	 * @author 王帆
	 * @date 2019年4月13日 上午10:22:34
	 */
	private String getAuthorityCode(String controllerCode) {
		return controllerCode.substring(controllerCode.indexOf("(")+1, controllerCode.length()-1).replaceAll("'", "");	
	}
	
	/**
	 * 判断方法注解，确定缺失的权限编码
	 * @param preAuthrizeValue
	 * @return
	 * @author 王帆
	 * @date 2019年4月17日 下午3:34:36
	 */
	private String[]  getAuthorityCodes(String preAuthrizeValue) {
		StringBuilder temp=new StringBuilder();
		if(!StringUtils.isEmpty(preAuthrizeValue)) {
			String[] authControllerCodes = preAuthrizeValue.split("and |or |AND |OR ");
			for(String authControllerCode:authControllerCodes) {
				String code = getAuthorityCode(authControllerCode.trim());
				if(authControllerCode.startsWith("hasAnyRole")) {
					String[] rolecodes = code.split(",");
					for(String role:rolecodes) {
						temp.append("ROLE_"+role).append(";");
					}
					continue;
				}
				if(authControllerCode.startsWith("hasRole")) {
					temp.append("ROLE_"+code).append(";");
					continue;
				}
				temp.append(code).append(";");
			}
		}
		if(temp.length()>0) {
			temp.substring(0, temp.length()-1);
		}
		return temp.toString().split(";");
	}
	

}
