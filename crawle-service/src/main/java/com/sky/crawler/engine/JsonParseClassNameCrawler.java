package com.sky.crawler.engine;

import java.io.Writer;
import com.sky.crawler.service.CrawlerJsonService;
import com.sky.crawler.service.CrawlerServiceFactoryUtil;
import com.sky.pub.ResultCode;
import com.sky.pub.common.exception.ResultException;

public class JsonParseClassNameCrawler extends JsonParseClassCrawler{
	private String className;
	
	public JsonParseClassNameCrawler(String className) throws ResultException {
		super();
		this.className=className;
		bulid();
	}
	
	public JsonParseClassNameCrawler(String className, Writer writer) throws ResultException {
		super();
		this.className=className;
		bulid();
		super.setWriter(writer);
	}

	
	public JsonParseClassNameCrawler bulid() throws ResultException {
		this.setCrawlerService(CrawlerServiceFactoryUtil.getClassBean(className, CrawlerJsonService.class));
		if(this.getCrawlerService()==null) {
			throw new ResultException(ResultCode.UNKONW_EXCEPTION, "未确定 CrawlerJsonService 服务");
		}
		return this;
	}
	
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	
}
