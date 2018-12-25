package com.crawl.data.config.dao;

import org.apache.ibatis.annotations.Mapper;

import com.crawl.data.config.dao.entity.ConfigDetailEntity;

@Mapper
public interface ConfigDetailEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ConfigDetailEntity record);

    int insertSelective(ConfigDetailEntity record);

    ConfigDetailEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ConfigDetailEntity record);

    int updateByPrimaryKey(ConfigDetailEntity record);
}