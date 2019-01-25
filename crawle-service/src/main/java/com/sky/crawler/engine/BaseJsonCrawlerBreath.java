package com.sky.crawler.engine;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.sky.crawler.core.CrawlerUrlDatum;
import com.sky.crawler.core.HttpCrawlerComponent;
import com.sky.crawler.core.HttpRequest;
import com.sky.crawler.core.HttpVistor;


/**
 * 	json爬虫
 * @author 王帆
 * @date  2019年1月23日 下午2:53:53
 */
public abstract class BaseJsonCrawlerBreath extends Thread implements HttpCrawlerComponent{
	LinkedList<CrawlerUrlDatum> seeds = new LinkedList<CrawlerUrlDatum>();
	private int threads;
	private HttpRequest requst;
	private HttpVistor vistor;
	private Writer writer;
	public BaseJsonCrawlerBreath() {
		requst=new HttpRequest();
		vistor=new HttpVistor();
	}
	public BaseJsonCrawlerBreath(Writer writer) {
		this();
		this.writer=writer;
	}
	
	public void start() {
		forEachCrawler();
		afterStop();
	}
	
	private void forEachCrawler() {
		Iterator<CrawlerUrlDatum> its = seeds.iterator();
		final BaseJsonCrawlerBreath crawler=this;
		for(int i=0;i<threads;i++) {
			if(its.hasNext()) {
				final CrawlerUrlDatum datum = its.next();
				new Runnable() {
					public void run() {
						try {
							synchronized (datum){
								JSONObject data = requst.getResponse(datum);
								vistor.invoke(crawler,data, datum);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				};
				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				its.remove();
			}
		}
		if(its.hasNext()) {
			forEachCrawler();
		}
	}
	
	/**
	 * 	输出信息到输出流中
	 * @param str
	 * @author 王帆
	 * @date 2019年1月21日 下午3:24:04
	 */
	public void print(String str) {
		if(writer!=null) {
			try {
				writer.write(str);
				writer.flush();
			} catch (IOException e) {
			}
		}
	}

	public void afterStop() {
		if(writer!=null) {
			try {
				writer.flush();
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public int getThreads() {
		return threads;
	}

	public BaseJsonCrawlerBreath setThreads(int threads) {
		this.threads=threads;
		return this;
	}

	public HttpRequest getRequst() {
		return requst==null?new HttpRequest():requst;
	}

	public void setRequst(HttpRequest requst) {
		this.requst = requst;
	}

	public Writer getWriter() {
		return writer;
	}

	public void setWriter(Writer writer) {
		this.writer = writer;
	}
	public void addSeed(CrawlerUrlDatum datum) {
		seeds.add(datum);
	}
	public void addSeed(List<CrawlerUrlDatum> datums) {
		seeds.addAll(datums);
	}
}
