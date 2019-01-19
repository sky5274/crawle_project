package com.crawl.data.config.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.crawl.data.config.dao.entity.CrawlerConfigEntity;

@Mapper
public interface CrawlerConfigEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CrawlerConfigEntity record);

    int insertSelective(CrawlerConfigEntity record);

    CrawlerConfigEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CrawlerConfigEntity record);

    int updateByPrimaryKey(CrawlerConfigEntity record);

    /**
     * 根据id集合删除数据
     * @param list
     * @return
     * @author 王帆
     * @date 2019年1月17日 上午10:54:24
     */
	int deleteByIdList(List<CrawlerConfigEntity> list);
	
	/**
	 * 根据爬虫配置条件查询数据
	 * @param config
	 * @return
	 * @author 王帆
	 * @date 2019年1月17日 上午10:55:02
	 */
	List<CrawlerConfigEntity> queryListByCrawlerConfig(CrawlerConfigEntity config);

	/**
	 * 根据爬虫配置条件分页查询数据
	 * @param config
	 * @return
	 * @author 王帆
	 * @date 2019年1月17日 上午10:55:29
	 */
	List<CrawlerConfigEntity> queryPageByCrawlerConfig(CrawlerConfigEntity config);
	
	/**
	 * 根据爬虫配置条件统计数据
	 * @param config
	 * @return
	 * @author 王帆
	 * @date 2019年1月17日 上午10:56:11
	 */
	int account(CrawlerConfigEntity config);

	/**
	 * 根据id查询配置信息
	 * @param id
	 * @return
	 * @author 王帆
	 * @date 2019年1月17日 上午10:56:49
	 */
	CrawlerConfigEntity queryCrawlerCnfig(int id);
	
	/**
	 * 根据配置编码查询爬虫配置信息
	 * @param code
	 * @return
	 * @author 王帆
	 * @date 2019年1月17日 上午10:57:36
	 */
	CrawlerConfigEntity queryCrawlerCnfigByCode(String code);
}