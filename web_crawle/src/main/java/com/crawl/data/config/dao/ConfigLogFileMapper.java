package com.crawl.data.config.dao;


import org.apache.ibatis.annotations.Mapper;

import com.crawl.data.config.dao.entity.ConfigFileDefined;

/**
 * 系统配置临时日志输出
 * @author 王帆
 * @date  2019年1月19日 上午10:17:46
 */
@Mapper
public interface ConfigLogFileMapper {
	int deleteByPrimaryKey(Integer id);

    int insert(ConfigFileDefined record);

    int insertSelective(ConfigFileDefined record);

    ConfigFileDefined selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ConfigFileDefined record);

    int updateByPrimaryKey(ConfigFileDefined record);
}
