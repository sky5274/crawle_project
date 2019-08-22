package com.sky.flow.service;

import java.util.List;
import java.util.Map;

public interface FlowSqlService {
	
	public List<Map<String, String>> queryBySql(String sql);
	public int excuteBySql(String sql);
}
