package com.sky.crawler.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;

public abstract class CrawlerService {
	private StringBuilder tempLog=new StringBuilder();
	protected Log log=LogFactory.getLog(getClass());
	
	protected void writer(String str) {
		tempLog.append(str);
	}
	
	/**
	 *  调用爬虫服务，并返回日志
	 * @param page
	 * @param next
	 * @return
	 * @author 王帆
	 * @date 2019年1月21日 上午8:59:33
	 */
	public String invoke(Page page, CrawlDatums next) {
		crawler(page,next);
		return tempLog.toString();
	}
	
	/**
	 * 	执行页面爬取
	 * @param page
	 * @param next
	 * @author 王帆
	 * @date 2019年1月18日 下午3:14:19
	 */
	public abstract void crawler(Page page, CrawlDatums next);

}
