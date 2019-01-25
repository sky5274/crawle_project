package com.sky.crawler.engine;

import java.util.List;
import java.util.Map;

/**
 * sql  执行mapper
 * @author 王帆
 * @date  2019年1月22日 下午1:05:30
 */
public interface SqlExcuteMapper {
	int excute(String sql);
	List<Map<String, Object>> queryList(String sql);
	Map<String, Object> queryOne(String sql);
}
