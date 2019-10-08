package com.sky.sm.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.sky.pub.service.impl.BaseRedisServiceImpl;

@Component
public class StringJsonRedisServiceImpl extends BaseRedisServiceImpl<String>{

	@Override
	public Class<String> getExtendClass() {
		return String.class;
	}
	
	public <T> T get(String key ,Class<T> clazz) {
		String val = doGet(key);
		if(val==null) {
			return null;
		}
		return JSON.parseObject(val,clazz);
	}
	
	public <T> List<T> getLike(String key,Class<T> clazz){
		List<T> list=new LinkedList<T>();
		Set<String> keys = doGetObjKeys(key);
		for(String k:keys) {
			list.add(get(k, clazz));
		}
		return list;
	}
	
	public void set(String key,String Value) {
		doStringSet(key, Value);
	}
	public void set(String key,String Value,int expressTime) {
		doStringSet(key, Value,expressTime,TimeUnit.MICROSECONDS);
	}

}
