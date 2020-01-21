package com.sky.crawler.engine;

import java.io.Writer;
import com.sky.crawler.service.CrawlerService;
import com.sky.crawler.service.CrawlerServiceFactoryUtil;
import com.sky.pub.ResultCode;
import com.sky.pub.common.exception.ResultException;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;

public class HtmlParseClassNameCrawler extends HtmlParseClassCrawler{
	private String className;
	
	public HtmlParseClassNameCrawler(String crawlPath, boolean autoParse,String className,Writer writer) throws ResultException {
		super(crawlPath, autoParse,writer);
		this.className=className;
		bulid();
	}
	public HtmlParseClassNameCrawler(String crawlPath, boolean autoParse,String className) throws ResultException {
		this(crawlPath, autoParse,className,null);
	}
	
	public HtmlParseClassCrawler bulid() throws ResultException {
		this.setCrawlerService(CrawlerServiceFactoryUtil.getClassBean(className, CrawlerService.class));
		if(this.getCrawlerService()==null) {
			throw new ResultException(ResultCode.UNKONW_EXCEPTION, "未确定 CrawlerService 服务");
		}
		return this;
	}
	@Override
	public void visit(Page page, CrawlDatums next) {
		super.visit(page,next);
	}

	public String getClassName() {
		return className;
	}

	public HtmlParseClassCrawler setClassName(String className) {
		this.className = className;
		return this;
	}
}
