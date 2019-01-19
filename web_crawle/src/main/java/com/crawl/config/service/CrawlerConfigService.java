package com.crawl.config.service;

import java.util.List;

import com.crawl.data.config.dao.entity.CrawlerConfigEntity;
import com.sky.pub.Page;
import com.sky.pub.common.exception.ResultException;

/**
 * 爬虫配置service
 * @author 王帆
 * @date  2019年1月16日 下午4:52:58
 */
public interface CrawlerConfigService {
	
	/**
	 * 根据条件分页查询爬虫配置数据
	 * @param config
	 * @return
	 * @author 王帆
	 * @date 2019年1月16日 下午5:00:28
	 */
	public Page<CrawlerConfigEntity> queryPageCrawlerConfig(CrawlerConfigEntity config);
	
	/**
	 * 根据条件查询爬虫配置数据
	 * @param config
	 * @return
	 * @author 王帆
	 * @date 2019年1月16日 下午5:02:03
	 */
	public List<CrawlerConfigEntity> queryListCrawlerConfig(CrawlerConfigEntity config);
	
	/**
	 * 新建爬虫配置数据
	 * @param cofig
	 * @return
	 * @author 王帆
	 * @date 2019年1月16日 下午5:02:16
	 */
	public CrawlerConfigEntity saveCrawlerConfig(CrawlerConfigEntity config);
	
	/**
	 * 修改爬虫配置数据
	 * @param cofig
	 * @return
	 * @author 王帆
	 * @date 2019年1月16日 下午5:02:39
	 */
	public CrawlerConfigEntity editCrawlerConfig(CrawlerConfigEntity config);
	
	/**
	 * 根据id集合删除爬虫配置数据
	 * @param list
	 * @return
	 * @author 王帆
	 * @throws ResultException 
	 * @date 2019年1月16日 下午5:02:59
	 */
	public boolean delCrawlerConfig(List<CrawlerConfigEntity> list) throws ResultException; 
	
	/**
	 * 根据爬虫配置id获取爬虫配置数据
	 * @param id
	 * @return
	 * @author 王帆
	 * @date 2019年1月16日 下午5:03:26
	 */
	public  CrawlerConfigEntity getById(int id);
}
