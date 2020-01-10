package com.sky.crawler.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONObject;
import com.sky.crawler.core.CrawlerUrlDatum;
import com.sky.crawler.core.HttpCrawlerComponent;

public abstract class CrawlerJsonService implements HttpCrawlerComponent{
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
	public String invoke(JSONObject data, CrawlerUrlDatum datum) {
		this.crawler(data, datum);
		return tempLog.toString();
	}
}
