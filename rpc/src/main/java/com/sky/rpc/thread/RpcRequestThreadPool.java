package com.sky.rpc.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *   获取线程池
 * @author 王帆
 * 2017年12月30日下午8:49:31
 */
public class RpcRequestThreadPool {
	static ExecutorService execut;
	static ExecutorService yhxc;
	static ExecutorService MsgHandle;
	
	public static ExecutorService  getCommonThreadExcutor() {
		if(execut==null) {
			ThreadFactory namedThreadFactory = new ThreadFactoryBuilder("common");
			execut = new ThreadPoolExecutor(0, Integer.MAX_VALUE/2, 30L, TimeUnit.MILLISECONDS,
			        new SynchronousQueue<Runnable>(), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
		}
		return execut;
	}
	
}
