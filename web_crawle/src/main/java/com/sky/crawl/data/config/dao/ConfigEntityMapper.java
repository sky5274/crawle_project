package com.sky.crawl.data.config.dao;

import org.apache.ibatis.annotations.Mapper;

import com.sky.crawl.data.config.dao.entity.ConfigEntity;

@Mapper
public interface ConfigEntityMapper {
    int deleteByPrimaryKey(ConfigEntity record);

    int insert(ConfigEntity record);

    int insertSelective(ConfigEntity record);

    ConfigEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ConfigEntity record);

    int updateByPrimaryKey(ConfigEntity record);
}