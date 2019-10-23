package com.sky.transaction.util;

import java.time.LocalTime;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 	线程工具类
 * @author 王帆
 * @date  2019年10月8日 上午10:30:13
 */
public class ThreadUtil {
	static ExecutorService execut;
	
	/**
	 * 	获取线程池执行
	 * @return
	 * @author 王帆
	 * @date 2019年10月8日 上午10:30:28
	 */
	public static ExecutorService  getThreadExcutor() {
		if(execut==null) {
			ThreadFactory namedThreadFactory = new ThreadFactoryBuilder("local");
			execut = new ThreadPoolExecutor(0, Integer.MAX_VALUE/2, 30L, TimeUnit.MILLISECONDS,
					new SynchronousQueue<Runnable>(), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
		}
		return execut;
	}

	/**
	 * 获取当前线程的  key
	 * @return
	 * @author 王帆
	 * @date 2019年9月22日 上午11:01:45
	 */
	public static String getThreadKey() {
		Thread thread=Thread.currentThread();
		ThreadGroup tg=thread.getThreadGroup();
		return String.format("%d-%s/%s-%s", thread.getId(),tg.getParent().getName(),tg.getName(),thread.getName());
	}
	
	public static String[] chars = new String[] { "a", "b", "c", "d", "e", "f",
			"g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
			"t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
			"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
			"W", "X", "Y", "Z" };
	/**
	 * 获取短uuid
	 * @return
	 * @author 王帆
	 * @date 2019年9月22日 上午11:56:50
	 */
	public static String getShortUuid() {
		StringBuffer shortBuffer = new StringBuffer();
		String uuid = UUID.randomUUID().toString().replace("-", "");
		for (int i = 0; i < 8; i++) {
			String str = uuid.substring(i * 4, i * 4 + 4);
			int x = Integer.parseInt(str, 16);
			shortBuffer.append(chars[x % 0x3E]);
		}
		return shortBuffer.toString();
	}	

	/**
	 * 	线程工厂
	 * @author 王帆
	 * @date  2019年10月8日 上午10:30:57
	 */
	public static class ThreadFactoryBuilder implements ThreadFactory{
		private long counter;  
		private String name;  
		private String prefix="mt_thread_";  
		private List<String> stats; 
		public ThreadFactoryBuilder(String name) {
			this("thread_",name);
		}
		public ThreadFactoryBuilder(String prefix,String name) {
			this.name=name;
			this.prefix=prefix;
			this.counter=0;
			stats=new LinkedList<String>();
		}
		public Thread newThread(Runnable r) {  
			Thread t = new Thread(r,prefix+name+"_"+counter);  
			counter++;  
			stats.add(String.format("Created thread %d with name %s on %s\n", t.getId(),t.getName(), LocalTime.now().toString()));  
			return t;  
		}  


		public String getStatuts(){  
			StringBuffer buffer = new StringBuffer();  
			Iterator<String> it = stats.iterator();  
			while(it.hasNext()){  
				buffer.append(it.next());  
				buffer.append("\n");  
			}  
			return buffer.toString();  
		}
	}
}
