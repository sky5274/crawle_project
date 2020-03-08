package com.sky.rpc.core;

import java.util.Arrays;
import java.util.List;
import com.sky.rpc.resource.ResouceProperties;

/**
 * rpc 服务类型
 * @author 王帆
 * @date  2020年1月16日 下午10:13:39
 */
public class RpcTypeContant {
	public static List<String> rpcTypeLimit=Arrays.asList("socket","bootsocket","http");
	protected static String rpcType;
	public static String rpcTypeKey="rpc.server.type";
	public static String getType() {
		if(rpcType ==null) {
			rpcType=ResouceProperties.getProperty(rpcTypeKey);
			if(!rpcTypeLimit.contains(rpcType)) {
				rpcType=rpcTypeLimit.get(0);
			}
		}
		return rpcType;
	}
}
