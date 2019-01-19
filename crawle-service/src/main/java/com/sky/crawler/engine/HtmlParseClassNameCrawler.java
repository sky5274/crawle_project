package com.sky.crawler.engine;

import com.sky.crawler.service.CrawlerService;
import com.sky.pub.ResultCode;
import com.sky.pub.common.exception.ResultException;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;

public class HtmlParseClassNameCrawler extends HtmlParseClassCrawler{
	private String className;
	
	public HtmlParseClassNameCrawler(String crawlPath, boolean autoParse,String className) throws ResultException {
		super(crawlPath, autoParse);
		this.className=className;
		bulid();
	}
	
	public HtmlParseClassCrawler bulid() throws ResultException {
		if(className==null) {
			throw new ResultException(ResultCode.PARAM_VALID,"请确认调用的java Class");
		}
		try {
			Class<?> clazz = Class.forName(className);
			if(!isCrawlerSerivce(clazz)) {
				throw new ResultException(ResultCode.PARAM_VALID,"调用的 "+className+" is not implements "+CrawlerService.class);
			}
			
		} catch (ClassNotFoundException e) {
			throw new ResultException(ResultCode.PARAM_VALID,className+"对应类不在系统中",e);
		}
		return this;
	}
	
	/**
	 * 判定是否继承
	 * @param clazz
	 * @return
	 * @author 王帆
	 * @date 2019年1月18日 下午3:02:49
	 */
	private boolean isCrawlerSerivce(Class<?> clazz) {
		boolean flag=false;
		Class<?>[] interfaces = clazz.getInterfaces();
		for(Class<?> inter:interfaces) {
			if(inter.getName().equals(CrawlerService.class)) {
				flag=true;
				break;
			}
		}
		return flag;
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
