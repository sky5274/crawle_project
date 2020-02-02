package com.sky.cm.core;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import com.alibaba.fastjson.JSON;
import com.sky.cm.annotation.EnumField;
import com.sky.cm.bean.LimitBean;
import com.sky.cm.bean.ProjectPropertyBean;
import com.sky.cm.bean.ProjectPropertyEnumBean;
import com.sky.cm.bean.PropertyEnumValueBean;
import com.sky.cm.utils.HttpUtil;
import com.sky.pub.Result;
import com.sky.pub.util.JavaReflectUtil;
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
	public  String releaseKey() {
		return skyConfigCacheServiceImpl.releaseKey();
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
	public <T> T getProperty(String key,Class<T> type,T defaultValue) {
		SkyConfigRequest propertyInfo = SkyConfigRequest.property;
		Map<String, Object> body = getProjectConfigValueMapParms();
		body.put("key", key);
		T value=null;
		try {
			String result = httpCache(propertyInfo.getUrl(), propertyInfo.getMethod(), body);
			value=getResultData(result, type);
		} catch (Exception e) {
			log.warn(e.getMessage());
		}
		return value==null?defaultValue:value;
	}
	public List<ProjectPropertyBean> getProperties() {
		SkyConfigRequest dump = SkyConfigRequest.properties;
		Map<String, Object> body = getProjectConfigValueMapParms();
		String result = http(dump.getUrl(), dump.getMethod(), body);
		return getResultDataList(result,ProjectPropertyBean.class);
	}

	/**
	 * 获取项目配置信息map
	 * @return
	 * @author wangfan
	 * @date 2020年1月31日 下午10:11:33
	 */
	private Map<String, Object> getProjectConfigValueMapParms() {
		@SuppressWarnings("unchecked")
		Map<String, Object> body = JSON.parseObject(JSON.toJSONString(configValue),Map.class);
		body.put("project", configValue.getServiceName());
		if(configValue.isEnableLocal()) {
			if(localResolver!=null) {
				localResolver=SpringUtil.getBean(SessionLocaleResolver.class);
			}
			Locale local =localResolver.resolveLocale(((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
			if(local !=null && !Locale.getDefault().equals(local)) {
				body.put("local", local.toString());
			}
		}
		return body;
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
		return getResultData(result,LimitBean.class);
	}

	private <T> T getResultData(String result,Class<T> clazz) {
		if(result!=null) {
			@SuppressWarnings("unchecked")
			Result<Object> res_result = JSON.parseObject(result, Result.class);
			if(res_result.isSuccess()) {
				return JSON.parseObject(JSON.toJSONString(res_result.getData()), clazz);
			}else {
				log.warn(res_result.getMessage());
			}
		}
		return null;
	}
	private <T> List<T> getResultDataList(String result,Class<T> clazz) {
		if(result!=null) {
			@SuppressWarnings("unchecked")
			Result<Object> res_result = JSON.parseObject(result, Result.class);
			if(res_result.isSuccess()) {
				return JSON.parseArray(JSON.toJSONString(res_result.getData()), clazz);
			}else {
				log.warn(res_result.getMessage());
			}
		}
		return null;
	}

	public void initProjectEnums() {
		SkyConfigRequest dump = SkyConfigRequest.property_enums;
		Map<String, Object> body = getProjectConfigValueMapParms();
		body.put("versionCode",configValue.getVersion());
		body.remove("version");
		String result =httpCache(dump.getUrl(), dump.getMethod(), body);
		List<ProjectPropertyEnumBean> proEnums= getResultDataList(result, ProjectPropertyEnumBean.class);
		if(!CollectionUtils.isEmpty(proEnums)) {
			proEnums.stream().forEach(pe->{
				skyConfigCacheServiceImpl.saveEnumValue(pe.getGroupNo(), JSON.toJSONString(pe.getEnums()));
			});
		}
	}

	/**
	 * 获取项目配置枚举信息
	 * @param groupNo
	 * @return
	 * @author wangfan
	 * @date 2020年2月1日 下午10:09:47
	 */
	public List<PropertyEnumValueBean> getProjectEnums(String groupNo) {
		String enumstr = skyConfigCacheServiceImpl.getEnumValue(groupNo);
		if(!StringUtils.isEmpty(enumstr)) {
			return JSON.parseArray(enumstr, PropertyEnumValueBean.class);
		}
		return null;
	}

	/**
	 * 根据枚举组合枚举编码获取枚举信息
	 * @param groupNo
	 * @param enumNo
	 * @return
	 * @author wangfan
	 * @date 2020年2月1日 下午10:10:30
	 */
	public PropertyEnumValueBean getProjectEnum(String groupNo,String enumNo) {
		List<PropertyEnumValueBean> enmus = getProjectEnums(groupNo);
		if(!CollectionUtils.isEmpty(enmus) && !StringUtils.isEmpty(enumNo)) {
			for(PropertyEnumValueBean e:enmus) {
				if(enumNo.equals(e.getEnumNo())) {
					return e;
				}
			}
		}
		return null;
	}
	public String getProjectEnumName(String groupNo,String enumNo) {
		PropertyEnumValueBean enmu = getProjectEnum(groupNo,enumNo);
		if(enmu !=null) {
			return enmu.getEnumName();
		}
		return null;
	}

	/**
	 * 获取类中字段属性枚举map分部
	 * @param clazz
	 * @return
	 * @author wangfan
	 * @date 2020年2月1日 下午10:50:04
	 */
	public Map<Field, List<PropertyEnumValueBean>> getProjectEnumMap(Class<?> clazz) {
		Map<Field, List<PropertyEnumValueBean>> enumMap=new HashMap<Field, List<PropertyEnumValueBean>>(16);
		Set<Field> fields = JavaReflectUtil.getAllFields(clazz);
		if(!CollectionUtils.isEmpty(fields)) {
			fields.stream()
			.filter(f->f.getAnnotation(EnumField.class) !=null)
			.forEach(f->{
				EnumField ef = f.getAnnotation(EnumField.class);
				List<PropertyEnumValueBean> enums = getProjectEnums(ef.groupNo());
				if(!CollectionUtils.isEmpty(enums)) {
					enumMap.put(f, enums);
				}
			});
		}
		return enumMap;
	}

	private <T> T parseProjectEnum(T obj,Class<?> clazz,Map<Field, List<PropertyEnumValueBean>> enumMap) {
		if(!CollectionUtils.isEmpty(enumMap) && obj !=null) {
			for(Field field:enumMap.keySet()) {
				EnumField ef = field.getAnnotation(EnumField.class);
				try {
					Field rf = clazz.getDeclaredField(ef.relyFiled());
					rf.setAccessible(true);
					field.setAccessible(true);
					String enumNo=rf.get(obj).toString();
					boolean flag=false;
					for(PropertyEnumValueBean e:enumMap.get(field)) {
						if(!flag && e.getEnumNo().equals(enumNo)) {
							field.set(obj, e.getEnumName());
						}
					}
				} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		return obj;
	}

	/**
	 * 使用反射注入属性枚举数据
	 * @param <T>
	 * @param obj
	 * @return
	 * @author wangfan
	 * @date 2020年2月1日 下午10:49:08
	 */
	public <T> T parseProjectEnum(T obj) {
		if(obj !=null) {
			Class<? extends Object> clazz = obj.getClass();
			Map<Field, List<PropertyEnumValueBean>> enumMap = getProjectEnumMap(clazz);
			obj=parseProjectEnum(obj, clazz,enumMap);
		}
		return obj;
	}

	/**
	 * 使用反射注入属性枚举数据
	 * @param <T>
	 * @param objs
	 * @return
	 * @author wangfan
	 * @date 2020年2月1日 下午10:49:51
	 */
	public <T> Collection<T> parseProjectEnum(Collection<T> objs) {
		if(!CollectionUtils.isEmpty(objs)) {
			Class<? extends Object> clazz = objs.iterator().next().getClass();
			Map<Field, List<PropertyEnumValueBean>> enumMap = getProjectEnumMap(clazz);
			for(T obj:objs) {
				obj=parseProjectEnum(obj, clazz,enumMap);
			}
		}
		return objs;
	}
}
