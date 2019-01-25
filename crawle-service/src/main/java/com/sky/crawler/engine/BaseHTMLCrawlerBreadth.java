package com.sky.crawler.engine;

import java.io.IOException;
import java.io.Writer;

import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;

/**
 *	基础携带输出流的爬虫生命周期
 * @author 王帆
 * @date  2019年1月21日 下午3:20:10
 */
public abstract class BaseHTMLCrawlerBreadth  extends BreadthCrawler{
	private Writer writer;

	public BaseHTMLCrawlerBreadth(String crawlPath, boolean autoParse) {
		super(crawlPath, autoParse);
	}
	public BaseHTMLCrawlerBreadth(String crawlPath, boolean autoParse,Writer writer) {
		super(crawlPath, autoParse);
		this.writer=writer;
	}
	public Writer getWriter() {
		return writer;
	}
	public BaseHTMLCrawlerBreadth setWriter(Writer writer) {
		this.writer = writer;
		return this;
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

	@Override
	public void afterStop() {
		super.afterStop();
		if(writer!=null) {
			try {
				writer.flush();
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
