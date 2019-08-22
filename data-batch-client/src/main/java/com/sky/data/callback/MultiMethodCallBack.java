package com.sky.data.callback;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import com.sky.pub.thread.ThreadFactoryBuilder;

/**
 * 
 * @author 王帆
 * @date  2019年8月20日 下午3:09:43
 */
public class MultiMethodCallBack <V>  implements Callable<V>{
	static ThreadPoolExecutor mutilMethodHandle =null;
	
	/**线程池*/
	public static ExecutorService  getMessagePoolThreadExcetor() {
		if(mutilMethodHandle==null) {
			ThreadFactory namedThreadFactory = new ThreadFactoryBuilder("mutil_method","call_back");
			mutilMethodHandle= new ThreadPoolExecutor(0,Integer.MAX_VALUE, 60L, TimeUnit.MILLISECONDS,
					new SynchronousQueue<Runnable>(), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
		}
		return mutilMethodHandle;
		
	}
	
	/**
	 * 设定多次调用方法，以及传参
	 * @param datas
	 * @param method
	 * @return
	 * @author 王帆
	 * @date 2019年8月20日 下午3:10:07
	 */
	public static <V> MultiMethodCallBack<V> methodCallBack(Supplier<V> supplier) {
		return new MultiMethodCallBack<V>(supplier);
	}
	public static <V> MultiMethodCallBack<V> methodCallBack(int times,Supplier<V> supplier) {
		return new MultiMethodCallBack<V>(times,supplier);
	}
	
	private int times=5;
	private Supplier<V> supplier;
	private Exception lasExcept;
	public MultiMethodCallBack(Supplier<V> supplier) {
		this.supplier=supplier;
	}
	public MultiMethodCallBack(int times, Supplier<V> supplier) {
		this.times=times;
		this.supplier=supplier;
	}
	
	/**
	 * 回调函数
	 */
	@Override
	public V call() throws Exception {
		if(supplier==null) {
			throw new Exception("no found the excute supplier method");
		}
		int nowTime=times;
		while (nowTime>0) {
			nowTime--;
			try {
				return supplier.get();
			} catch (Exception e) {
				lasExcept=e;
			}
		}
		if(nowTime<=0 && lasExcept!=null) {
			lasExcept=new Exception("mutile method call back times: "+times+" has error!", lasExcept);
			throw lasExcept;
		}
		return null;
	}

	public int getTimes() {
		return times;
	}
	public void setTimes(int times) {
		this.times = times;
	}
	public  V excute() throws InterruptedException, ExecutionException {
		return getMessagePoolThreadExcetor().submit(this).get();
	}

	public Supplier<V> getSupplier() {
		return supplier;
	}
	public void setSupplier(Supplier<V> supplier) {
		this.supplier = supplier;
	}
}
