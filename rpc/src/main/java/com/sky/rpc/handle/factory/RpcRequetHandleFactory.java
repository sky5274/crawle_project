package com.sky.rpc.handle.factory;

import java.util.Collection;

import org.springframework.util.CollectionUtils;

import com.sky.rpc.base.RpcRequest;
import com.sky.rpc.handle.RpcRequestAfterHandle;
import com.sky.rpc.handle.RpcRequestPreHandle;
import com.sky.rpc.handle.RpcServiceInvokePreHandle;
import com.sky.rpc.util.RpcSpringBeanUtil;

/**
 * rpc  调用过程aop接口工厂
 * @author 王帆
 * @date  2019年10月6日 上午9:27:27
 */
public class RpcRequetHandleFactory {
	
	/**
	 * 调用前参数预处理
	 * @param req
	 * @author 王帆
	 * @date 2019年10月6日 上午9:27:53
	 */
	public static void before(RpcRequest req) {
		Collection<RpcRequestPreHandle> perHabdles = RpcSpringBeanUtil.getBeans(RpcRequestPreHandle.class);
		if(!CollectionUtils.isEmpty(perHabdles)) {
			for(RpcRequestPreHandle perh:perHabdles) {
				perh.preRequest(req);
			}
		}
	}
	
	/**
	 * 客户端调用后 监听
	 * @param req
	 * @param res
	 * @author 王帆
	 * @date 2019年10月6日 上午9:28:19
	 */
	public static void after(RpcRequest req,Object res) {
		Collection<RpcRequestAfterHandle> aftHabdles = RpcSpringBeanUtil.getBeans(RpcRequestAfterHandle.class);
		if(!CollectionUtils.isEmpty(aftHabdles)) {
			for(RpcRequestAfterHandle afth:aftHabdles) {
				afth.afterInvoke(req, res);
			}
		}
	}
	
	/**
	 * rpc服务端方法执行预处理
	 * @param req
	 * @author 王帆
	 * @date 2019年10月6日 上午9:28:51
	 */
	public static void preInvoke(RpcRequest req) {
		Collection<RpcServiceInvokePreHandle> preHandles = RpcSpringBeanUtil.getBeans(RpcServiceInvokePreHandle.class);
		if(!CollectionUtils.isEmpty(preHandles)) {
			for(RpcServiceInvokePreHandle pre:preHandles) {
				pre.preInvoke(req);
			}
		}
	}
}
