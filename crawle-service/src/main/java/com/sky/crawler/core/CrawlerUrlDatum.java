package com.sky.crawler.core;


import java.util.HashMap;
import java.util.Map;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatum;

/**
 * 	爬取url数据集
 * @author 王帆
 * @date  2019年1月23日 下午2:55:26
 */
public class CrawlerUrlDatum extends CrawlDatum{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CrawlerRequestMethod method;
	private Map<String, Object> param=new HashMap<>();
	private String content;
	
	public CrawlerUrlDatum() {}
	public CrawlerUrlDatum(String url) {
		super(url);
	}
	public CrawlerUrlDatum(String url,String type) {
		super(url, type);
	}
	public CrawlerUrlDatum(String url,CrawlerRequestMethod method) {
		this(url);
		this.method=method;
	}
	public CrawlerUrlDatum(String url,CrawlerRequestMethod method,String type) {
		this(url,type);
		this.method=method;
	}
	public CrawlerRequestMethod getMethod() {
		return method;
	}
	public void setMethod(CrawlerRequestMethod method) {
		this.method = method;
	}
	public Map<String, Object> getParam() {
		return param;
	}
	public void setParam(Map<String, Object> param) {
		this.param = param;
	}
	
	public CrawlerUrlDatum append(String key,Object value) {
		param.put(key, value);
		return this;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
