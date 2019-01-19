package com.sky.crawler.engine;


import com.sky.crawler.service.CrawlerService;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;

/**
 * 	使用定义的class爬去html
 * @author 王帆
 * @date  2019年1月18日 下午1:53:38
 */
public class HtmlParseClassCrawler  extends BreadthCrawler{
	private CrawlerService crawlerService;
	
	public HtmlParseClassCrawler(String crawlPath, boolean autoParse) {
		super(crawlPath, autoParse);
	}

	@Override
	public void visit(Page page, CrawlDatums next) {
		crawlerService.crawler(page,next);
	}

	public CrawlerService getCrawlerService() {
		return crawlerService;
	}

	public void setCrawlerService(CrawlerService crawlerService) {
		this.crawlerService = crawlerService;
	}
	

}
