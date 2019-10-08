package com.sky.cm.trace;

import java.io.IOException;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpMethod;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.alibaba.fastjson.JSON;
import com.sky.cm.bean.RequestTraceBean;
import com.sky.cm.core.SkyConfigRequest;
import com.sky.cm.core.SkyConfigValue;
import com.sky.cm.utils.HttpUtil;
import com.sky.pub.util.SpringUtil;

/**
 * 系统接口调度监听
 * @author 王帆
 * @date  2019年3月15日 下午2:25:36
 */
public abstract class RequestTraceEventListen <T>{
	public static String groupId="jav-group-id";
	protected String is_group_start="is_group_start-";
	protected Log log=LogFactory.getLog(getClass());
	private RequestTraceBean trace;
	
	private static  String tracelocation=null;
	
	public static String getTraceLocation() {
		if(tracelocation==null) {
			tracelocation=SpringUtil.getBean(SkyConfigValue.class).getLocation();
		}
		return tracelocation;
	}
	
	private T request;
	public HttpServletRequest getHttpRequest() {
		ServletRequestAttributes requestAtrributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		if(requestAtrributes !=null) {
			return requestAtrributes.getRequest();
		}
		return null;
	}

	public void setRequest(T request) {
		this.request=request;
	}
	public abstract RequestTraceBean convert(T request) ;

	/**
	 * 接口调度开始注册
	 * 
	 * @author 王帆
	 * @date 2019年3月15日 下午2:26:09
	 */
	@SuppressWarnings("unchecked")
	public void recordTraceStart() {
		trace= convert(request);
		if(trace !=null && trace.getUrl()!=null && !isTraceUrl(trace)) {
			log.debug("trace regist start: "+trace.getUrl());
				try {
					HttpUtil.AsyncResponse(60*1000, SpringUtil.getBean(SkyConfigValue.class).getLocation()+SkyConfigRequest.trace_start.getUrl(), HttpMethod.POST, JSON.parseObject(JSON.toJSONString(trace),Map.class),null);
				} catch (IOException e) {
					log.warn(trace.getUrl()+" trace regist start failed", e);
				}
		}
	}
	
	
	/**
	 * 接口调度结束监听
	 * 
	 * @author 王帆
	 * @param param  调用结果
	 * @param obj 
	 * @date 2019年3月15日 下午2:26:30
	 */
	public void recordTraceEnd(Object param) {
		recordTraceEnd(200,param);
	}
	/**
	 * 接口调度结束监听
	 * 
	 * @author 王帆
	 * @param param 调用结果
	 * @param status  调用状态 
	 * @param obj 
	 * @date 2019年3月15日 下午2:26:30
	 */
	@SuppressWarnings("unchecked")
	public void recordTraceEnd(int status, Object param) {
		//trace = convert(request);
		if(trace !=null && trace.getUrl()!=null && !isTraceUrl(trace)) {
			log.debug("trace regist end: "+trace.getUrl());
			trace.setStatus(status);
			SkyConfigValue configValue = SpringUtil.getBean(SkyConfigValue.class);
			if(param !=null) {
				if(String.class.getSimpleName().equals(param.getClass().getSimpleName())) {
					trace.setResponseBody(param.toString());
				}else {
					trace.setResponseBody(JSON.toJSONString(param));
				}
			}
			try {
				HttpUtil.AsyncResponse(60*1000, configValue.getLocation()+SkyConfigRequest.trace_end.getUrl(), HttpMethod.POST, JSON.parseObject(JSON.toJSONString(trace),Map.class),null);
			} catch (IOException e) {
				log.warn(trace.getUrl()+" trace regist close failed", e);
			}
			HttpServletRequest req = getHttpRequest();
			if(req!=null) {
				String isgroupStart = (String) req.getSession().getAttribute(is_group_start+trace.getTraceId());
				if(!StringUtils.isEmpty(isgroupStart) &&"true".equals(isgroupStart)) {
					req.getSession().removeAttribute(groupId);
					req.getSession().removeAttribute("tracepid");
					req.getSession().removeAttribute(is_group_start+trace.getTraceId());
				}
			}
		}
	}

	private boolean isTraceUrl(RequestTraceBean trace) {
		return  trace !=null && trace.getUrl()!=null && (trace.getUrl().startsWith(getTraceLocation()) || trace.getUrl().endsWith("/error"));
	}
}
