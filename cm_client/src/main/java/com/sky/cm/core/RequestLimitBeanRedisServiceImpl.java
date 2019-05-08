package com.sky.cm.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.sky.cm.annotation.Limit;
import com.sky.cm.bean.LimitBean;
import com.sky.cm.enums.LimitType;

@Component
public class RequestLimitBeanRedisServiceImpl extends BaseRedisServiceImpl<LimitBean>{
	@Autowired
	private SkyConfig skyConfig;
	private static String spiteStr="_";

	@Override
	protected Class<LimitBean> getExtendClass() {
		return LimitBean.class;
	}
	
	/**
	 * 存储限制数据
	 * @param limit
	 * @author 王帆
	 * @date 2019年3月13日 上午9:27:35
	 */
	public void store(LimitBean limit) {
		doStringSet(limit.getKey(), limit, limit.getPriod());
	}
	
	/**
	 * 是否限制
	 * @param key
	 * @return
	 * @author 王帆
	 * @param limitdef 
	 * @date 2019年3月13日 上午9:27:23
	 */
	public LimitBean isLimit(String key, Limit limitdef) {
		if(limitdef !=null) {
			StringBuilder temp=new StringBuilder();
			if(!StringUtils.isEmpty(limitdef.prefix())) {
				temp.append(limitdef.prefix()).append(spiteStr);
			}
			if(!StringUtils.isEmpty(limitdef.key())) {
				temp.append(limitdef.key()).append(spiteStr);
			}
			if(!StringUtils.isEmpty(limitdef.name())) {
				temp.append(limitdef.name()).append(spiteStr);
			}
			key=temp.toString()+key;
		}
		LimitBean limit = doStringGet(key);
		if(limit != null) {
			if(limit.isLimit()) {
				return limit;
			}else {
				limit.setCount(limit.getCount()-1);
				doStringSet(limit.getKey(), limit, limit.getPriod());
				return limit;
			}
		}else {
			//查询配置信息
			/*
			 * 1:LimitType.LOCATION  使用本地限流配置
			 * 2：服务限流标识为true  使用服务限流配置
			 * 3: 服务限流标识为false  limit存在  使用本地限流配置
			 */
			String url =key.substring(key.indexOf("$")+1);
			if(limitdef !=null && limitdef.limitType()==LimitType.LOCATION) {
				limit=new LimitBean(key);
				limit.setCount(limitdef.count());
				limit.setPriod(limitdef.period());
				limit.setLimitCount(limitdef.count());
			}else if(skyConfig.getConfig().isEnablelimit()){
				limit=skyConfig.getLimit(url);
			}else if(limitdef !=null){
				limit=new LimitBean(key);
				limit.setCount(limitdef.count());
				limit.setPriod(limitdef.period());
				limit.setLimitCount(limitdef.count());
			}
			if(limit==null) {
				limit=new LimitBean(key);
			}
			doStringSet(limit.getKey(), limit, limit.getPriod());
		}
		return limit;
	}
}
