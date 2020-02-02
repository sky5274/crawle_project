package com.sky.cm.trace;

import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.sky.cm.bean.RequestTraceBean;

@Component
public class HttpRequestEventListener extends RequestTraceEventListen<HttpServletRequest>{

	@Override
	public RequestTraceBean convert(HttpServletRequest req) {
		RequestTraceBean info=new RequestTraceBean();
		info.setUrl("http://"+req.getHeader("host")+req.getRequestURI());
		
		info.setType(req.getMethod());
		if("POST".equals(req.getMethod().toUpperCase())) {
			//获取post请求的的requestbody
			try {
				info.setRequestBody(org.springframework.util.StreamUtils.copyToString(req.getInputStream(), Charset.defaultCharset()));
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}else {
			info.setRequestBody(req.getQueryString());
		}
		info.setTraceId(UUID.randomUUID().toString());
		String pid=(String) req.getSession().getAttribute("tracepid");
		if(pid ==null) {
			req.getSession().setAttribute("tracepid",info.getTraceId());
		}else {
			info.setTracePId(pid);
		}
		info.setSessionId(req.getSession().getId());
		Map<String, String> headers=new HashMap<>();
		Enumeration<String> headernames = req.getHeaderNames();
		while(headernames.hasMoreElements()) {
			String name = headernames.nextElement();
			headers.put(name, req.getHeader(name));
		}
		info.setHeaders(JSON.toJSONString(headers));
		String info_groupid=req.getHeader(groupId);
		if(StringUtils.isEmpty(info_groupid)) {
			info_groupid= (String) req.getSession().getAttribute(groupId);
		}
		if(StringUtils.isEmpty(info_groupid)) {
			req.setAttribute(groupId, info.getTraceId());
			req.getSession().setAttribute(super.is_group_start+groupId, "true");
			info_groupid=info.getTraceId();
		}
		info.setGroupId(info_groupid);
		return info;
	}

}
