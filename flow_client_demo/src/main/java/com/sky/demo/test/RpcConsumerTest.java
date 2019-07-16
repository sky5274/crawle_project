package com.sky.demo.test;

import java.util.List;

import org.springframework.stereotype.Component;

import com.sky.rpc.annotation.RpcComsumer;

//@Component
public class RpcConsumerTest {
	
//	@RpcComsumer
//	private List<MethodInterFace> intfs;
//	@RpcComsumer
//	private MethodInterFace[] intfs;
	
	@RpcComsumer
	private MethodInterFace intf;
	
	public  void test() {
		intf.invoker("dses");
	}
}
