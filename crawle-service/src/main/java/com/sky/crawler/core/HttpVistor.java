package com.sky.crawler.core;


import com.alibaba.fastjson.JSONObject;

/**
 * http  接口调用视图器
 * @author 王帆
 * @date  2019年1月25日 上午9:24:25
 */
public class HttpVistor {
	public void invoke(HttpCrawlerComponent crawler, JSONObject data,CrawlerUrlDatum datum) {
		crawler.crawler(data, datum);
	}
}
