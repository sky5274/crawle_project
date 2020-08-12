package com.sky.pub.advice;


import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.io.InputStreamSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import com.alibaba.fastjson.JSON;
import cn.microvideo.base.pub.util.ListUtils;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Advisor;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;


/** 
 * controller advice aop for annotation(requestmapper)
 * @author sky
 * @date 2020年8月12日 下午5:28:47
 */
@Component
public class ControllerAdvice {

	private static Log log=LogFactory.getLog(ControllerAdvice.class);
	private static String prefix="-->> ";
	private static List<String> excuteUrl=Arrays.asList("/error");
	private static Date times=null;
	private static Date timee=null;
	
	@Bean
    public Advisor dataSourceAdvisor(){
        Pointcut pointcut = new AnnotationMatchingPointcut(RequestMapping.class, true);
        Advice advice = new MethodAroundAdvice();
        return new DefaultPointcutAdvisor(pointcut, advice);
    }
	
	 private static class MethodAroundAdvice implements MethodBeforeAdvice, AfterReturningAdvice{
	    	
	        @Override
	        public void before(Method method, Object[] args, Object target) throws Throwable {
	        	HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	    		String url = req.getRequestURI().toString();
	    		if(excuteUrl.contains(url)) {
	    			return;
	    		}
	    		//获取request
	    		times=new Date();
	    		
	    		Map<String, Object> argMap=new HashMap<>(16);

	    		LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
	    		String[] params = u.getParameterNames(method);
	    		Object[] argList = args;
	    		for(int i=0;i<argList.length;i++){
	    			Object arg = argList[i];
	    			if(arg instanceof HttpServletRequest){
	    				argMap.put(params[i],req.getParameterMap());
	    				continue;
	    			}else if(arg instanceof MultipartFile){
	    				MultipartFile f = (MultipartFile) arg;
	    				argMap.put(params[i],f.getOriginalFilename());
	    				continue;
	    			}else if(arg instanceof HttpServletResponse || arg instanceof InputStreamSource){
	    				continue;
	    			}
	    			argMap.put(params[i], arg);
	    		}
	    		log.debug(String.format("%s %s   被%s 调用", prefix,method,ControllAdvice.getIpAddress(req)));
	    		String queryString = req.getQueryString();
	    		log.debug(String.format("%s url:%s %s", prefix,url+(StringUtils.isEmpty(queryString)?"":" ?"+queryString),ListUtils.isEmpty(argMap.keySet())?"":"  args:"+JSON.toJSONString(argMap)));
	        }

	        @Override
	        public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
	        	HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	    		String url = req.getRequestURI().toString();
	    		if(excuteUrl.contains(url)) {
	    			return;
	    		}
	    		timee=new Date();
	    		try {
	    			long cost=timee.getTime()-times.getTime();
	    			log.debug(String.format("%s %s  执行结束。 耗时:%dms", prefix,method.getName(),cost));
	    		} catch (Exception e) {
	    		}
	    		timee=null;
	    		times=null;
	        }
	    }

	
	/**
     * 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址;
     *
     * @param request
     * @return
     */
    public final static String getIpAddress(HttpServletRequest request) {
        // 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址
        String ip = request.getHeader("X-Forwarded-For");
        String unk="unknown";
        if (ip == null || ip.length() == 0 || unk.equalsIgnoreCase(ip)) {
            if (ip == null || ip.length() == 0 || unk.equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || unk.equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || unk.equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (ip == null || ip.length() == 0 || unk.equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED");
            }
            if (ip == null || ip.length() == 0 || unk.equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_CLUSTER_CLIENT_IP");
            }
            if (ip == null || ip.length() == 0 || unk.equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (ip == null || ip.length() == 0 || unk.equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_FORWARDED_FOR");
            }
            if (ip == null || ip.length() == 0 || unk.equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_FORWARDED");
            }
            if (ip == null || ip.length() == 0 || unk.equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_VIA");
            }
            if (ip == null || ip.length() == 0 || unk.equalsIgnoreCase(ip)) {
                ip = request.getHeader("REMOTE_ADDR");
            }
            if (ip == null || ip.length() == 0 || unk.equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
        } else if (ip.length() > 15) {
            String[] ips = ip.split(",");
            for (int index = 0; index < ips.length; index++) {
                String strIp = (String) ips[index];
                if (!("unknown".equalsIgnoreCase(strIp))) {
                    ip = strIp;
                    break;
                }
            }
        }
        return ip;
    }
}
