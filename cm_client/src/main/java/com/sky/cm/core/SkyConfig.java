package com.sky.cm.core;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.StandardServletEnvironment;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import com.alibaba.fastjson.JSON;
import com.sky.cm.bean.LimitBean;
import com.sky.cm.bean.ProjectPropertyBean;
import com.sky.cm.utils.HttpUtil;
import com.sky.pub.Result;
import com.sky.pub.util.SpringUtil;
import okhttp3.Response;

@Component
public class SkyConfig {
	@Autowired
	private SkyConfigValue configValue;
	@Autowired(required=false)
	private SessionLocaleResolver localResolver;
	@Autowired
	private SkyConfigCacheServiceImpl  skyConfigCacheServiceImpl;

	private int expressTime=5*60;

	Log log=LogFactory.getLog(getClass());

	public SkyConfigValue getConfig() {
		return configValue;
	}

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
		Environment env = SpringUtil.getEvn();
		Map<String, Object> propertyMap=new HashMap<String, Object>();
		if(env !=null && env instanceof StandardServletEnvironment) {
			StandardServletEnvironment senv = (StandardServletEnvironment)env;
			senv.getPropertySources().iterator()
				.forEachRemaining(s-> {
					Object source = s.getSource();
					if(source instanceof Map) {
						propertyMap.putAll((Map<String, Object>) source);
					}else if(source instanceof Properties) {
						Properties p=(Properties) source;
						p.keySet().stream().forEach(k-> propertyMap.put(k.toString(), p.get(k)));
					}
				});
		}
		System.err.println(propertyMap);
		body.put("environment", propertyMap);
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
		SkyConfigRequest propertyInfo = SkyConfigRequest.property;
		Map<String, Object> body = JSON.parseObject(JSON.toJSONString(configValue),Map.class);
		body.put("key", key);
		if(localResolver!=null) {
			Locale local =localResolver.resolveLocale(((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
			if(local !=null && !Locale.getDefault().equals(local)) {
				body.put("local", local.toString());
			}
		}else {
			localResolver=SpringUtil.getBean(SessionLocaleResolver.class);
		}
		
		T value=null;
		try {
			String result = httpCache(propertyInfo.getUrl(), propertyInfo.getMethod(), body);
			if(result!=null) {
				Result<T> res_result = JSON.parseObject(result, Result.class);
				if(res_result.isSuccess()) {
					value= res_result.getData();
				}else {
					log.warn(res_result.getMessage());
				}
			}
		} catch (Exception e) {
			log.warn(e.getMessage());
		}
		return value==null?defaultValue:value;
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

	public String httpCache(String url,HttpMethod method ,Map<String, Object> params) {
		String value=skyConfigCacheServiceImpl.doGet(url+"?"+JSON.toJSONString(params));
		if(StringUtils.isEmpty(value)) {
			value=http(url, method, params);
			if(!StringUtils.isEmpty(value)) {
				//设置url  redis存储时间5s
				skyConfigCacheServiceImpl.doStringSet(url, value, expressTime);
			}
		}
		return value;
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

	/**
	 * 	获取url对应的属性配置
	 * @param tempLimit
	 * @return
	 * @author 王帆
	 * @date 2019年3月12日 下午5:22:03
	 */
	@SuppressWarnings("unchecked")
	public LimitBean getLimit(LimitBean tempLimit) {
		Map<String, Object> params=JSON.parseObject(JSON.toJSONString(tempLimit), Map.class);
		String result = httpCache(SkyConfigRequest.limit.getUrl(),SkyConfigRequest.limit.getMethod(),params);
		if(result!=null) {
			Result<LimitBean> res_result = JSON.parseObject(result, Result.class);
			if(res_result.isSuccess()) {
				return res_result.getData();
			}else {
				log.warn(res_result.getMessage());
			}
		}
		return null;
	}
}
