package com.sky.cm.config;


import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import com.sky.cm.annotation.Limit;
import com.sky.cm.bean.LimitBean;
import com.sky.cm.core.RequestLimitBeanRedisServiceImpl;
import com.sky.cm.core.SkyConfig;
import com.sky.pub.Result;

@Component
public class WebLimitInterceptor implements HandlerInterceptor {
	@Autowired
	private SkyConfig skyConfig;
	@Autowired
	private RequestLimitBeanRedisServiceImpl requestLimitService;
	
	private static List<String> resourceUrlEndWithList=Arrays.asList(".js",".css",".img",".png",".jpeg",".xls",".word");
	
	private boolean isResourceUrl(String url) {
		boolean flag=false;
		if(!StringUtils.isEmpty(url)) {
			for(String endWith:resourceUrlEndWithList) {
				flag=url.endsWith(endWith);
				if(flag) {
					break;
				}
			}
		}
		return flag;
	}
	
	private List<String> unLimitUrl=Arrays.asList("/error");
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handlerMethod)
			throws Exception {
		if(unLimitUrl.contains(request.getRequestURI()) || isResourceUrl(request.getRequestURI().toString())) {
			return true;
		}
		Limit limitdef=null;
		if(handlerMethod instanceof HandlerMethod ){
			if (handlerMethod != null) {
				HandlerMethod handler = (HandlerMethod)handlerMethod;
				Method method = handler.getMethod();
				if (method != null) {
					//如果定义了ExceptionHandler则返回相应的Map中的数据
					limitdef = method.getAnnotation(Limit.class);
				}
			}
		}
		//服务限流：false 或限流注解无时，不限流
		if(!skyConfig.getConfig().isEnablelimit() && limitdef==null) {
			return true;
		}
		LimitBean limit = requestLimitService.isLimit(request.getRequestURL().toString(),request.getSession().getId(),limitdef);
		boolean flag = limit !=null && limit.isLimit();
		if( flag) {
			response.setCharacterEncoding("UTF-8");  
		    response.setContentType("application/json; charset=utf-8");  
		    PrintWriter out = null;  
		    try {  
		        out = response.getWriter();  
		        out.write(JSON.toJSONString(new Result<>("l-1", String.format("网络限流: %d/%d  (times/s)",limit.getLimitCount(),limit.getPriod()),false)));
		        out.flush();
		    } catch (IOException e) {  
		    } finally {  
		        if (out != null) {
		            out.close();  
		        }  
		    }  
		}
		return !flag;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)	throws Exception {
	}

}
