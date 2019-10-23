package com.sky.transaction.prepare.handle;

import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.sky.rpc.base.RpcRequest;
import com.sky.rpc.handle.RpcServiceInvokePreHandle;
import com.sky.transaction.datasource.factory.ConnectTransactionNodeFactory;

/**
 *	rpc service prepare invoke method handle 
 * @author 王帆
 * @date  2019年10月6日 上午10:01:38
 */
@Component
public class RpcServicePrepareInvokeHandle implements RpcServiceInvokePreHandle{

	@Override
	public void preInvoke(RpcRequest req) {
		/*
		 *	rpc调用服务端方法时，将当期方法前置goupId与NodeId 
		 */
		Map<String, String> headers = req.getHeaders();
		if(!CollectionUtils.isEmpty(headers)) {
			String groupId = headers.get(ConnectTransactionNodeFactory.groupIdKey);
			String nodeId = headers.get(ConnectTransactionNodeFactory.nodeIdKey);
			if(!StringUtils.isEmpty(groupId)) {
				ConnectTransactionNodeFactory.setThreadGroupId(groupId);
			}
			if(!StringUtils.isEmpty(nodeId)) {
				ConnectTransactionNodeFactory.setThreadNodeId(nodeId);
			}
			
		}
	}

}
