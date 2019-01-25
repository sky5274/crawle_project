package com.sky.crawler.engine;


import java.io.Writer;
import com.sky.crawler.service.CrawlerService;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;

/**
 * 	使用定义的class爬去html
 * @author 王帆
 * @date  2019年1月18日 下午1:53:38
 */
public class HtmlParseClassCrawler  extends BaseHTMLCrawlerBreadth{
	private CrawlerService crawlerService;
	
	
	public HtmlParseClassCrawler(String crawlPath, boolean autoParse,Writer writer) {
		super(crawlPath, autoParse,writer);
	}
	public HtmlParseClassCrawler(String crawlPath, boolean autoParse) {
		super(crawlPath, autoParse);
	}

	@Override
	public void visit(Page page, CrawlDatums next) {
		if(crawlerService!=null) {
			String tempLog = crawlerService.invoke(page,next);
			print(tempLog);
		}else {
			print("未设置相关  CrawlerService.class");
		}
		
	}
	
	
	public CrawlerService getCrawlerService() {
		return crawlerService;
	}

	public void setCrawlerService(CrawlerService crawlerService) {
		this.crawlerService = crawlerService;
	}
	

}
