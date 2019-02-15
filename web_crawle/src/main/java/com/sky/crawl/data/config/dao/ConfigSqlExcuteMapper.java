package com.sky.crawl.data.config.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface ConfigSqlExcuteMapper {
	List<Map<String, Object>> queryBySql(@Param("sql")String  sql);
	int excuteBySql(@Param("sql")String  sql);
}
