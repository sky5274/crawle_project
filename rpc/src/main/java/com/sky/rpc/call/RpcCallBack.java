package com.sky.rpc.call;

import java.io.Serializable;

public interface RpcCallBack extends Serializable{
	
	public static RpcCallBack call=new RpcCallBack() {
		private static final long serialVersionUID = 1L;
	};
}
