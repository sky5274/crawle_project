package com.sky.transaction.prepare.handle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.sky.transaction.datasource.factory.ConnectTransactionNodeFactory;

/**
 * http  transaction prepare handele
 * @author 王帆
 * @date  2019年10月8日 上午10:36:03
 */
@Configuration
public class WebTransactionIntercepterConfigPreHandle  extends WebMvcConfigurerAdapter{
	
	@Override
    public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new TransactionIntercepter()).addPathPatterns("/**");
    }
	
	static class TransactionIntercepter implements HandlerInterceptor{

		@Override
		public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
			String groupId = request.getHeader(ConnectTransactionNodeFactory.groupIdKey);
			String nodeId = request.getHeader(ConnectTransactionNodeFactory.nodeIdKey);
			if(StringUtils.isEmpty(groupId)) {
				ConnectTransactionNodeFactory.setThreadGroupId(groupId);
			}
			if(StringUtils.isEmpty(nodeId)) {
				ConnectTransactionNodeFactory.setThreadNodeId(nodeId);
			}
			return true;
		}

		@Override
		public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,ModelAndView modelAndView) throws Exception {
			
		}

		@Override
		public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,Exception ex) throws Exception {
			
		}
		
	}
}
