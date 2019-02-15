package com.sky.crawl.data.config.dao;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Mapper;

import com.sky.crawl.data.config.dao.entity.CrawlerConfigUrlEntity;

@Mapper
public interface CrawlerConfigUrlEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CrawlerConfigUrlEntity record);
    int insertBatch(List<CrawlerConfigUrlEntity> list);

    int insertSelective(CrawlerConfigUrlEntity record);

    CrawlerConfigUrlEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CrawlerConfigUrlEntity record);

    int updateByPrimaryKey(CrawlerConfigUrlEntity record);

	int deleteByCodeSet(Set<String> codeSet);
	int deleteByCode(String code);
}