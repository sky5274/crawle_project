package com.sky.test;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.sky.task.core.HttpTaskRpcClient;
import com.sky.task.core.RpcConfig;
import com.sky.task.core.RpcConfig.NodeData;
import com.sky.task.demo.SimpleJob;

public class HttpRpcClientTest {
	public static void main(String[] args) throws Throwable {
		RpcConfig rpcConfig=new RpcConfig();
		List<NodeData> nodes = rpcConfig.getAllNodeDataByClassName(SimpleJob.class.getName());
		System.err.println(JSON.toJSONString(nodes));
		boolean flag = HttpTaskRpcClient.invoke(nodes.get(0).getUrl(), null);
		System.err.println(flag);
	}
}
