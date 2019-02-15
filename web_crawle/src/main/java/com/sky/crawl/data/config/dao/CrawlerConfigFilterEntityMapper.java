package com.crawl.data.config.dao;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Mapper;

import com.crawl.data.config.dao.entity.CrawlerConfigFilterEntity;

@Mapper
public interface CrawlerConfigFilterEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CrawlerConfigFilterEntity record);
    
    int insertBatch(List<CrawlerConfigFilterEntity> list);

    int insertSelective(CrawlerConfigFilterEntity record);

    CrawlerConfigFilterEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CrawlerConfigFilterEntity record);

    int updateByPrimaryKey(CrawlerConfigFilterEntity record);

    /**
     * 根据编码删除爬虫配置过滤规则
     * @param codeSet
     * @return
     * @author 王帆
     * @date 2019年1月17日 下午1:43:26
     */
	int deleteByCodeSet(Set<String> codeSet);
	int deleteByCode(String code);
}