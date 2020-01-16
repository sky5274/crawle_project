package com.sky.rpc.regist;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.sky.rpc.annotation.RpcProvider;
import com.sky.rpc.core.RpcElement;
import com.sky.rpc.resource.ResouceProperties;

@Component
public class RpcProviderRegistNodeHande implements RpcRegistNodHandel<RpcProvider>{

	@Override
	public RpcElement getRpcNodeConfig(Class<?> clazz) {
		RpcProvider anno = clazz.getAnnotation(getAnnotation());
		return anno==null?null:getRpcProviderElement(clazz,anno);
	}

	@Override
	public Class<RpcProvider> getAnnotation() {
		return RpcProvider.class;
	}
	
	private RpcElement getRpcProviderElement(Class<?> clazz, RpcProvider provider) {
		RpcElement ele=new RpcElement();
		if(!StringUtils.isEmpty(provider.group())) {
			ele.setGroup(provider.group());
		}else {
			ele.setGroup(getRpcGroup());
		}
		if(!StringUtils.isEmpty(provider.version())) {
			ele.setVersion(provider.version());
		}else {
			ele.setVersion(getRpcVersion());
		}
		ele.setInterfaceName(clazz.getName());
		return ele;
	}
	
	private static String group;
	private static String version;
	
	public static String getRpcGroup() {
		if(group==null) {
			group=ResouceProperties.getProperty("rpc.group");
		}
		return group;
	}

	public static String getRpcVersion() {
		if(version==null) {
			version=ResouceProperties.getProperty("rpc.version");
		}
		return version;
	}
}
