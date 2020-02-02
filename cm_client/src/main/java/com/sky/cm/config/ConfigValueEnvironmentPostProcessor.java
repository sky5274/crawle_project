package com.sky.cm.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.support.StandardServletEnvironment;
import com.alibaba.fastjson.JSON;
import com.sky.cm.bean.ProjectPropertyBean;
import com.sky.cm.core.SkyConfig;
import com.sky.cm.core.SkyConfigRequest;
import com.sky.cm.core.SkyConfigValue;
import com.sky.pub.Result;
import com.sky.pub.thread.PubThreadPool;
import com.sky.pub.util.SpringUtil;

/**
 * config value environment refresh processor
 * @author wangfan
 * @date 2020年1月30日 下午9:35:48
 */
@Component
public class ConfigValueEnvironmentPostProcessor implements ApplicationContextAware{
	@Autowired
	private SkyConfig skyConfig;
	@Autowired
	private SkyConfigValue configValue;
	@Autowired
	SpringUtil springUtil;
	@Resource(name="redisTemplate")  
	private RedisTemplate<String,String> redisTemplate; 
	private Log log=LogFactory.getLog(getClass());
	private static int listenTimeInterval=60*1000;
	private static String configPropertySourceKey="config_value property source";

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		refreshConfigProperty(true);
		if(configValue.isAutoRefresh()) {
			//客户监听配置版本
			PubThreadPool.excute(PubThreadPool.getCommonThreadExcutor(), ()->{
				while(true) {
					int times=SpringUtil.getEvnProperty("listenTimeInterval", Integer.class, listenTimeInterval);
					try {
						Thread.sleep(times);
					} catch (InterruptedException e) {
					}
					//客户端主动监听版本更新以及配置更新,时间间隔100
					refreshConfigProperty(false);
				}
			});
		}
	}
	
	private void refreshConfigProperty(boolean flag) {
		String release=getConfigPropertyRelease();
		if(!StringUtils.isEmpty(release)) {
			String hisRelease=redisTemplate.opsForValue().get(skyConfig.releaseKey());
			if(flag || !release.equals(hisRelease)) {
				//以及更新项目配置属性
				List<ProjectPropertyBean> properties = skyConfig.getProperties();
				if(CollectionUtils.isEmpty(properties)) {
					return;
				}
				PropertySource<?> props = transToPropertySource(properties,release);
				Environment env = SpringUtil.getEvn();
				if(env !=null  && env instanceof StandardServletEnvironment) {
					StandardServletEnvironment senv = (StandardServletEnvironment)env;
					senv.getPropertySources().addFirst(props);
				}
				//初始化项目枚举
				skyConfig.initProjectEnums();
			}
			//更新项目发布信息
			redisTemplate.opsForValue().set(skyConfig.releaseKey(), release, 1000, TimeUnit.SECONDS);
		}
	}
	
	/**
	 * 封装属性配置资源
	 * @param properties
	 * @param release
	 * @return
	 * @author wangfan
	 * @date 2020年1月29日 下午8:46:45
	 */
	private PropertySource<?> transToPropertySource(List<ProjectPropertyBean> properties,String release) {
		Map<String, Object> prop=new HashMap<String, Object>();
		properties.stream().forEach(pvlist->{
			if(CollectionUtils.isEmpty(pvlist.getProperties())) {
				pvlist.getProperties().stream().forEach(pv->{
					prop.put(pv.getKey(), pv.getValue());
				});
			}
		});
		prop.put(skyConfig.releaseKey(), release);
		return new MapPropertySource(configPropertySourceKey,prop);
	}
	
	
	/**
	 * 获取配置属性版本
	 * @return
	 * @author wangfan
	 * @date 2020年1月29日 下午7:55:23
	 */
	@SuppressWarnings("unchecked")
	private String getConfigPropertyRelease() {
		SkyConfigRequest propertyReleaseInfo = SkyConfigRequest.property_release;
		Map<String, Object> body = JSON.parseObject(JSON.toJSONString(configValue),Map.class);
		String result=skyConfig.http(propertyReleaseInfo.getUrl(), propertyReleaseInfo.getMethod(), body);
		if(result!=null) {
			Result<String> res_result = JSON.parseObject(result, Result.class);
			if(res_result.isSuccess()) {
				return res_result.getData();
			}else {
				log.error("config property release get error, message:"+res_result.getMessage());
			}
		}
		return null;
	}
}
