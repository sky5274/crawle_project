package com.sky.transaction.prepare.handle;

import org.springframework.stereotype.Component;

import com.sky.rpc.base.RpcRequest;
import com.sky.rpc.handle.RpcRequestPreHandle;
import com.sky.transaction.datasource.factory.ConnectTransactionNodeFactory;

/**
 * rpc调用请求参数预处理handle
 * @author 王帆
 * @date  2019年10月6日 上午9:54:33
 */
@Component
public class RpcPrepareRequestHandle implements RpcRequestPreHandle{

	@Override
	public void preRequest(RpcRequest req) {
		req.put(ConnectTransactionNodeFactory.groupIdKey, ConnectTransactionNodeFactory.getThreadGroupId());
		req.put(ConnectTransactionNodeFactory.parentIdKey, ConnectTransactionNodeFactory.getThreadParentId());
		req.put(ConnectTransactionNodeFactory.nodeIdKey, ConnectTransactionNodeFactory.getThreadNodeId());
	}

}
