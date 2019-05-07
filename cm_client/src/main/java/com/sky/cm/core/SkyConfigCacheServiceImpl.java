package com.sky.cm.core;

import org.springframework.stereotype.Component;

@Component
public class SkyConfigCacheServiceImpl extends BaseRedisServiceImpl<String>{

	@Override
	protected Class<String> getExtendClass() {
		return String.class;
	}

}
