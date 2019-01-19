package com.sky.crawler.service;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;

public interface CrawlerService {
	
	/**
	 * 	执行页面爬取
	 * @param page
	 * @param next
	 * @author 王帆
	 * @date 2019年1月18日 下午3:14:19
	 */
	void crawler(Page page, CrawlDatums next);

}
