package com.sky.cm.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.sky.pub.service.impl.BaseRedisServiceImpl;
import com.sky.pub.util.ComPubUtil;

@Component
public class SkyConfigCacheServiceImpl extends BaseRedisServiceImpl<String>{
	@Autowired
	private SkyConfigValue configValue;
	
	private String enumPrefix="enum_group";
	private static String releaseKey;
	public  String releaseKey() {
		if(StringUtils.isEmpty(releaseKey)) {
			releaseKey=String.format("%s~%s~%s@%s", configValue.getServiceName(),configValue.getProfile(),configValue.getVersion(),ComPubUtil.getIp());
		}
		return releaseKey;
	}
	
	@Override
	protected Class<String> getExtendClass() {
		return String.class;
	}
	
	public String getPrefixKey() {
		return super.getPrefixKey()+"~"+releaseKey();
	}
	
	public String doGet(String key) {
		return this.doStringGet(key);
	}
	
	public String doStringSet(String key,String value ,int expressTime) {
		super.doStringSet(key,value,expressTime);
		return key;
	}
	public String saveEnumValue(String key,String value ) {
		super.doHashSet(enumPrefix, key, value);
		return key;
	}
	public String getEnumValue(String key) {
		return super.doHashGet(enumPrefix, key);
	}
}
