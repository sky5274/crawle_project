package com.sky.flow.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FlowSqlMapper {
	public String queryShortUUID();
	public List<Map<String, String>> queryBySql(String sql);
	public int excuteBySql(String sql);
}
