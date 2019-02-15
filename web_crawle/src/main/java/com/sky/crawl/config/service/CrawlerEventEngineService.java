package com.crawl.config.service;

import com.crawl.data.config.dao.entity.ConfigFileDefined;
import com.crawl.data.config.dao.entity.CrawlerConfigEntity;

/**
 *   爬虫事件引擎事件
 * @author 王帆
 * @date  2019年1月24日 下午3:38:53
 */
public interface CrawlerEventEngineService {
	
	public ConfigFileDefined excute(CrawlerConfigEntity config) throws Exception ;

	public ConfigFileDefined queryTempLogDefined(int id);
}
