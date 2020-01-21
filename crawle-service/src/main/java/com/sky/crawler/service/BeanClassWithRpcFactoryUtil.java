package com.sky.crawler.service;

import java.util.List;
import org.springframework.util.CollectionUtils;
import com.sky.pub.ResultCode;
import com.sky.pub.common.exception.ResultException;
import com.sky.pub.util.JavaReflectUtil;
import com.sky.pub.util.SpringUtil;
import com.sky.rpc.core.RpcElement;
import com.sky.rpc.core.RpcProxy;
import com.sky.rpc.regist.RpcProviderRegistNodeHande;
import com.sky.rpc.zk.RpcConfig;
import com.sky.rpc.zk.RpcConfig.nodeData;

/**
 * Bean class factory：  find bean from spring or rpc-container
 * @author wangfan
 * @date 2020年1月21日 上午11:14:27
 */
public class BeanClassWithRpcFactoryUtil {
	
	/**
	 * 根据class名称 和父级类型确定确定classBean
	 * @param <T>
	 * @param className
	 * @param superClass
	 * @return
	 * @throws ResultException
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getClassBean(String className,Class<T> superClass) throws ResultException {
		if(className==null) {
			throw new ResultException(ResultCode.PARAM_VALID,"请确认调用的java Class");
		}
		try {
			Class<?> clazz = Class.forName(className);
			if(!JavaReflectUtil.isExtrendOrImpl(clazz,superClass)) {
				throw new ResultException(ResultCode.PARAM_VALID,"调用的 "+className+" is not extends "+superClass);
			}
			return (T) SpringUtil.getBean(clazz);
		} catch (ClassNotFoundException  e) {
			T bean = getRpcRemoteBean(className,superClass);
			if(bean !=null) {
				return bean;
			}
			throw new ResultException(ResultCode.PARAM_VALID,className+"对应类不在系统环境中",e);
		}
	}
	
	/**
	 * 获取rpc-bean
	 * @param <T>
	 * @param className
	 * @param superClass
	 * @return
	 * @throws ResultException
	 * @author wangfan
	 * @date 2020年1月21日 上午11:13:05
	 */
	public static <T> T getRpcRemoteBean(String className,Class<T> superClass) throws ResultException {
		RpcElement node=RpcProviderRegistNodeHande.getRpcElement(superClass);
		node.setInterfaceName(className);
		node.setTarget(className);
		List<nodeData> nodes = RpcConfig.getRcpNodeDatas(node.writeUrl()+"/"+className);
		if(CollectionUtils.isEmpty(nodes)) {
			throw new ResultException(ResultCode.UNKONW_EXCEPTION,"class: "+className+" is not in the sys-rpc node container");
		}
		return RpcProxy.getRemoteProxyObj(superClass, node);
	}
}
