package com.sky.crawler.core;

import com.alibaba.fastjson.JSONObject;

/**
 * json  请求获取接口
 * @author 王帆
 * @date  2019年1月23日 下午3:20:55
 */
public interface JsonRequester {
	 JSONObject getResponse(String url) throws Exception;

	 JSONObject getResponse(CrawlerUrlDatum datum) throws Exception;
}
