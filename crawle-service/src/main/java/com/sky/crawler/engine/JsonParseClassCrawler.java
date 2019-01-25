package com.sky.crawler.engine;

import java.io.Writer;

import com.alibaba.fastjson.JSONObject;
import com.sky.crawler.core.CrawlerUrlDatum;
import com.sky.crawler.service.CrawlerJsonService;

/**
 *  使用java爬取json
 * @author 王帆
 * @date  2019年1月24日 上午10:30:41
 */
public class JsonParseClassCrawler extends BaseJsonCrawlerBreath{
	private CrawlerJsonService crawlerService;
	
	public JsonParseClassCrawler() {
		super();
	}
	public JsonParseClassCrawler(CrawlerJsonService crawlerJsonService) {
		this();
		this.crawlerService=crawlerJsonService;
	}
	public JsonParseClassCrawler(CrawlerJsonService crawlerJsonService,Writer writer) {
		super(writer);
		this.crawlerService=crawlerJsonService;
	}

	@Override
	public void crawler(JSONObject data, CrawlerUrlDatum datum) {
		String temp_log = crawlerService.invoke(data, datum);
		print(temp_log);
	}
	public CrawlerJsonService getCrawlerService() {
		return crawlerService;
	}
	public JsonParseClassCrawler setCrawlerService(CrawlerJsonService crawlerService) {
		this.crawlerService = crawlerService;
		return this;
	}
}
