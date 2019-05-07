package com.sky.cm.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sky.cm.bean.LimitBean;

@Component
public class RequestLimitBeanRedisServiceImpl extends BaseRedisServiceImpl<LimitBean>{
	@Autowired
	private SkyConfig skyConfig;

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
	 * @date 2019年3月13日 上午9:27:23
	 */
	public LimitBean isLimit(String key) {
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
			String url =key.substring(key.indexOf("$")+1);
			limit=skyConfig.getLimit(url);
			if(limit==null) {
				limit=new LimitBean(key);
			}
			doStringSet(limit.getKey(), limit, limit.getPriod());
		}
		return limit;
	}
}
