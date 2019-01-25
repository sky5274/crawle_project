package com.sky.crawler.core;

import com.alibaba.fastjson.JSONObject;

public interface HttpCrawlerComponent {
	public void crawler(JSONObject data, CrawlerUrlDatum datum) ;
}
