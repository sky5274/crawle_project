package com.sky.pub.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *	s  获取线程池
 * @author 王帆
 * @date  2019年1月21日 下午2:56:15
 */
public class PubThreadPool {
	static ExecutorService execut;
	static ExecutorService msgHandle;
	
	public static ExecutorService  getCommonThreadExcutor() {
		if(execut==null) {
			ThreadFactory namedThreadFactory = new ThreadFactoryBuilder("common");
			execut = new ThreadPoolExecutor(0, Integer.MAX_VALUE/2, 30L, TimeUnit.MILLISECONDS,
			        new SynchronousQueue<Runnable>(), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
		}
		return execut;
	}
	
	public static ExecutorService  getMessagePoolThreadExcetor() {
		if(msgHandle==null) {
			ThreadFactory namedThreadFactory = new ThreadFactoryBuilder("Message");
			msgHandle= new ThreadPoolExecutor(0,Integer.MAX_VALUE, 60L, TimeUnit.MILLISECONDS,
					new SynchronousQueue<Runnable>(), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
		}
		return msgHandle;
		
	}
	
	/**执行线程*/
	public static void excute(ExecutorService pool,Runnable run) {
		pool.execute(run);
	}
	/**执行有返回的线程*/
	public static <V> Future<V>  excute(ExecutorService pool,Callable<V> run) {
		return pool.submit(run);
	}
	
}
