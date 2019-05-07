package com.sky.pub.thread;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadFactory;

/**
 * 实现工厂模式创建线程
 *
 * @author 王帆 
 * 2018年1月5日下午12:05:57
 */
public class ThreadFactoryBuilder implements ThreadFactory {

	private long counter;  
	private String name;  
	private String prefix="crwale_";  
	private List<String> stats; 
	public ThreadFactoryBuilder(String name) {
		this("crwale_",name);
	}
	public ThreadFactoryBuilder(String prefix,String name) {
		this.name=name;
		this.prefix=prefix;
		this.counter=0;
		stats=new LinkedList<>();
	}
	@Override  
	public Thread newThread(Runnable r) {  
		Thread t = new Thread(r,prefix+name+"_"+counter);  
		counter++;  
		stats.add(String.format("Created thread %d with name %s on %s\n", t.getId(),t.getName(),new Date()));  
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
