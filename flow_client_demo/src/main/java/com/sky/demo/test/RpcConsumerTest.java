package com.sky.demo.test;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.sky.rpc.annotation.RpcComsumer;

@Component
public class RpcConsumerTest {
	
//	@RpcComsumer
//	private List<MethodInterFace> intfs;
//	@RpcComsumer
//	private MethodInterFace[] intfs;
	
	@RpcComsumer
	@Qualifier("rpc_MethodInterFace")
	private MethodInterFace intf;
	
	public  void test() {
		intf.invoker("dses");
	}
}
