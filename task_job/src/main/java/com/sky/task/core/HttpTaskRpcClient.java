package com.sky.task.core;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;
import com.sky.task.job.AbstractBaseJobcClient;

public class HttpTaskRpcClient {
	public static boolean invoke(String url,String param) throws Throwable {
		Class<AbstractBaseJobcClient> target = AbstractBaseJobcClient.class;
		HttpInvokerProxyFactoryBean beanfactory=new HttpInvokerProxyFactoryBean();
		beanfactory.setServiceUrl(url);
		beanfactory.setServiceInterface(target);
		MethodInvocation method = new SimpleMethodInvocation(target,target.getMethod("excute", String.class),param);
		return (boolean) beanfactory.invoke(method);
	}
}
