package com.sky.cm.core;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import com.alibaba.fastjson.JSON;
import com.sky.cm.bean.ProjectPropertyBean;
import com.sky.cm.utils.HttpUtil;
import com.sky.pub.Result;
import com.sky.pub.util.SpringUtil;

import okhttp3.Response;

@Component
public class SkyConfig {
	@Autowired
	SkyConfigValue configValue;
	Log log=LogFactory.getLog(getClass());
	
	public void registProject(Map<RequestMappingInfo, HandlerMethod> mappers) {
		List<Map<String, Object>> mapperHandlerList=new LinkedList<>();
		for(RequestMappingInfo reqmap:mappers.keySet()) {
			HandlerMethod mapperHandler = mappers.get(reqmap);
			Map<String, Object> temp=new HashMap<>();
			temp.put("types", reqmap.getMethodsCondition().getMethods());
			temp.put("paths", reqmap.getPatternsCondition().getPatterns());
			
			Map<String, Object> methodInfoMap =new HashMap<>();
			methodInfoMap.put("methodName", mapperHandler.getBeanType().getName()+"."+mapperHandler.getMethod().getName());
			methodInfoMap.put("params", mapperHandler.getMethod().getParameterTypes());
			methodInfoMap.put("returnType", mapperHandler.getMethod().getReturnType().getName());
			temp.put("method",methodInfoMap);
			mapperHandlerList.add(temp);
		}
		@SuppressWarnings("unchecked")
		Map<String, Object> body = JSON.parseObject(JSON.toJSONString(configValue),Map.class);
		body.put("urls", mapperHandlerList);
		body.put("port", SpringUtil.getEvnProperty("server.port"));
		SkyConfigRequest regist = SkyConfigRequest.regist;
		log.debug("regist result: "+http(regist.getUrl(), regist.getMethod(), body));
	}
	@SuppressWarnings("unchecked")
	public void dumpProject() {
		SkyConfigRequest dump = SkyConfigRequest.load_off;
		Map<String, Object> body = JSON.parseObject(JSON.toJSONString(configValue),Map.class);
		http(dump.getUrl(), dump.getMethod(), body);
	}
	
	public String getProperty(String key) {
		return getProperty(key,String.class);
	}
	public String getProperty(String key,String defaultvalue) {
		return getProperty(key,String.class,defaultvalue);
	}
	public <T> T getProperty(String key,Class<T> type) {
		return getProperty(key,type,null);
	}
	@SuppressWarnings("unchecked")
	public <T> T getProperty(String key,Class<T> type,T defaultValue) {
		SkyConfigRequest dump = SkyConfigRequest.property;
		Map<String, Object> body = JSON.parseObject(JSON.toJSONString(configValue),Map.class);
		body.put("key", key);
		String result = http(dump.getUrl(), dump.getMethod(), body);
		if(result!=null) {
			Result<T> res_result = JSON.parseObject(result, Result.class);
			if(res_result.isSuccess()) {
				return res_result.getData();
			}else {
				log.warn(res_result.getMessage());
			}
		}
		return defaultValue;
	}
	@SuppressWarnings("unchecked")
	public List<ProjectPropertyBean> getProperties() {
		SkyConfigRequest dump = SkyConfigRequest.properties;
		Map<String, Object> body = JSON.parseObject(JSON.toJSONString(configValue),Map.class);
		String result = http(dump.getUrl(), dump.getMethod(), body);
		if(result!=null) {
			Result<List<ProjectPropertyBean>> res_result = JSON.parseObject(result, Result.class);
			if(res_result.isSuccess()) {
				return res_result.getData();
			}else {
				log.warn(res_result.getMessage());
			}
		}
		return null;
	}
	
	public String http(String url,HttpMethod method ,Map<String, Object> params) {
		url=configValue.getLocation()+url;
		try {
			Response response = HttpUtil.getResponse(configValue.getReadTimeout(),url, method, params);
			return response.body().string();
		} catch (IOException e) {
			log.warn("request mapper load off to "+url+ " error,message:"+e.getMessage(), e);
		}
		return null;
	}
}
