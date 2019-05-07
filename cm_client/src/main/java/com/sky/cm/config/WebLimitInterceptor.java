package com.sky.cm.config;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import com.sky.cm.bean.LimitBean;
import com.sky.cm.core.RequestLimitBeanRedisServiceImpl;
import com.sky.cm.core.SkyConfig;
import com.sky.cm.core.SkyConfigValue;
import com.sky.pub.Result;
import com.sky.pub.common.exception.ResultException;

@Component
public class WebLimitInterceptor implements HandlerInterceptor {
	@Autowired
	private SkyConfig skyConfig;
	@Autowired
	private RequestLimitBeanRedisServiceImpl requestLimitService;
	
	private String getKey(HttpServletRequest req) {
		SkyConfigValue config = skyConfig.getConfig();
		StringBuilder key=new StringBuilder();
		key.append(config.getServiceName()).append("-").append(config.getProfile()).append("-").append(config.getVersion());
		key.append("/").append(req.getSession().getId());
		key.append("$").append(req.getRequestURI());
		return key.toString();
	}
	
	private List<String> unLimitUrl=Arrays.asList("/error");
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if(unLimitUrl.contains(request.getRequestURI()) || request.getRequestURI().toString().endsWith(".js")) {
			return true;
		}
		LimitBean limit = requestLimitService.isLimit(getKey(request));
		if(limit.isLimit()) {
			response.setCharacterEncoding("UTF-8");  
		    response.setContentType("application/json; charset=utf-8");  
		    PrintWriter out = null;  
		    try {  
		        out = response.getWriter();  
		        out.write(JSON.toJSONString(new Result<>("l-1", String.format("网络限流: %dtimes >> %d/s",limit.getLimitCount(),limit.getPriod()),false)));
		        out.flush();
		    } catch (IOException e) {  
		    } finally {  
		        if (out != null) {
		            out.close();  
		        }  
		    }  
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)	throws Exception {
	}

}
