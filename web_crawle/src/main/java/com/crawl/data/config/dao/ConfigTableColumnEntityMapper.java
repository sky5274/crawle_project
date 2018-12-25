package com.crawl.data.config.dao;

import com.crawl.data.config.dao.entity.ConfigTableColumnEntity;

public interface ConfigTableColumnEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ConfigTableColumnEntity record);

    int insertSelective(ConfigTableColumnEntity record);

    ConfigTableColumnEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ConfigTableColumnEntity record);

    int updateByPrimaryKey(ConfigTableColumnEntity record);
}