package com.sky.crawl.data.config.dao;

import java.util.List;

import com.sky.crawl.data.config.dao.entity.ConfigTableColumnEntity;

public interface ConfigTableColumnEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ConfigTableColumnEntity record);

    int insertSelective(ConfigTableColumnEntity record);

    ConfigTableColumnEntity selectByPrimaryKey(Integer id);
    
    /**
     * 根据表主键 查询其属性集合
     * @param tableid
     * @return
     * @author 王帆
     * @date 2018年12月26日 下午4:37:56
     */
    List<ConfigTableColumnEntity> queryListByTable(Integer tableid);

    int updateByPrimaryKeySelective(ConfigTableColumnEntity record);

    int updateByPrimaryKey(ConfigTableColumnEntity record);

    /**
     * 批量添加表属性数据
     * @param list
     * @return
     * @author 王帆
     * @date 2018年12月26日 下午5:09:34
     */
	int insetBatch(List<ConfigTableColumnEntity> list);
	
	/**
	 * 根据表id删除表属性
	 * @param tableId
	 * @return
	 * @author 王帆
	 * @date 2018年12月26日 下午5:17:04
	 */
	int delByTableId(Integer tableId);
}