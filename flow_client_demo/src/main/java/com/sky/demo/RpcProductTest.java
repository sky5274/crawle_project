package com.sky.demo;

import com.sky.demo.test.MethodInterFace;
import com.sky.rpc.annotation.RpcProvider;

@RpcProvider
public class RpcProductTest implements MethodInterFace{
	
	@Override
	public Object invoker(Object arg) {
		System.err.println("provider test invoker method get args:"+arg);
		return arg+" invoke method";
	}
}
