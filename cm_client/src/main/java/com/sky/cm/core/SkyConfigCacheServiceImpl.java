package com.sky.cm.core;

import org.springframework.stereotype.Component;

import com.sky.pub.service.impl.BaseRedisServiceImpl;

@Component
public class SkyConfigCacheServiceImpl extends BaseRedisServiceImpl<String>{

	@Override
	protected Class<String> getExtendClass() {
		return String.class;
	}
	
	public String doGet(String key) {
		return this.doStringGet(key);
	}
	
	public String doStringSet(String key,String value ,int expressTime) {
		super.doStringSet(key,value,expressTime);
		return key;
	}
}
