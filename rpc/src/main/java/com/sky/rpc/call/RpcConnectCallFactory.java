package com.sky.rpc.call;

import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Map;
import com.sky.rpc.base.RpcRequest;

/**
 * rpc connect call factory
 * @author 王帆
 * @date  2019年6月21日 下午2:48:20
 */
public class RpcConnectCallFactory implements RpcConnectCall{
	public static Map<String, RpcConnectCall> callMap=new HashMap<>();
	
	private static String getNowThreadName() {
		return Thread.currentThread().getName();
	}
	
	public static void addConnectCall(RpcConnectCall call) {
		callMap.put(getNowThreadName(), call);
	}
	public static RpcConnectCall getNowConnectCall() {
		return callMap.get(getNowThreadName());
	}
	
	public static void clearConnectCall() {
		callMap.clear();
	}
	public static void removeNowConnectCall() {
		callMap.remove(getNowThreadName());
	}

	@Override
	public <T> T call(RpcRequest req) throws Throwable {
		RpcConnectCall call = getNowConnectCall();
		if(call==null) {
			throw new Exception("未发现线程rpc回调链接");
		}
		return call.call(req);
	}

	@Override
	public SocketAddress getSocketAddress() {
		RpcConnectCall call = getNowConnectCall();
		if(call!=null) {
			return call.getSocketAddress();
		}
		return null;
	}
}
