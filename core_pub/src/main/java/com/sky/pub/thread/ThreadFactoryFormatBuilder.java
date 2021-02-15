package com.sky.pub.thread;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadFactory;
import org.springframework.util.StringUtils;

/**
 * 实现工厂模式创建线程
 * @author wangfan
 * @date 2021年2月14日 下午10:21:10
 */
public class ThreadFactoryFormatBuilder implements ThreadFactory {
	private long counter;  
	private String format;  
	private List<String> stats; 
	
	public ThreadFactoryFormatBuilder(String format) {
		this.format=format;
		if(StringUtils.isEmpty(format)) {
			this.format="singile_thread_%s";
		}
		this.counter=0;
		stats=new LinkedList<>();
	}
	@Override  
	public Thread newThread(Runnable r) {  
		Thread t = new Thread(r,String.format(format, counter));  
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
