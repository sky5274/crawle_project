package com.sky.demo.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FlowSqlMapper {
	public String queryShortUUID();
	public List<Map<String, String>> queryBySql(@Param("sql")String sql);
	public int excuteBySql(@Param("sql")String sql);
}
