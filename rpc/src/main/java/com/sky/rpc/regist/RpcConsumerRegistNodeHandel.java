package com.sky.rpc.regist;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.sky.rpc.annotation.RpcComsumer;
import com.sky.rpc.core.RpcElement;

@Component
public class RpcConsumerRegistNodeHandel extends AbstractRpcConsumerRegistNodeHandel<RpcComsumer>{

	@Override
	public Class<RpcComsumer> getAnnotation() {
		return RpcComsumer.class;
	}
	
	public RpcElement getRpcConsumerElement(Class<?> clazz, RpcComsumer consumer) {
		RpcElement ele=new RpcElement();
		if(!StringUtils.isEmpty(consumer.group())) {
			ele.setGroup(consumer.group());
		}else {
			ele.setGroup(RpcProviderRegistNodeHande.getRpcGroup());
		}
		if(!StringUtils.isEmpty(consumer.version())) {
			ele.setVersion(consumer.version());
		}else {
			ele.setVersion(RpcProviderRegistNodeHande.getRpcVersion());
		}
		boolean flag=consumer.target().length>0;
		if(flag) {
			ele.setGroup(consumer.target()[0].getName());
		}
		ele.setInterfaceName(clazz.getName());
		ele.setId("rpc_"+clazz.getSimpleName()+(flag?"_"+consumer.target()[0].getSimpleName():""));
		ele.setTimeout(consumer.timeout());
		return ele;
	}

}
