package com.crawl.data.config.dao;

import java.util.List;

import com.crawl.data.config.dao.entity.ConfigTableEntity;

public interface ConfigTableEntityMapper {
    int deleteByPrimaryKey(ConfigTableEntity record);

    int insert(ConfigTableEntity record);

    int insertSelective(ConfigTableEntity record);

    ConfigTableEntity selectByPrimaryKey(ConfigTableEntity record);

    int updateByPrimaryKeySelective(ConfigTableEntity record);

    int updateByPrimaryKey(ConfigTableEntity record);
    
    /**
     * 根据条件查询 表集合
     * @param record
     * @return
     * @author 王帆
     * @date 2018年12月25日 上午9:46:17
     */
    List<ConfigTableEntity> queryListByParam(ConfigTableEntity record);
    
    /**
     *	统计数据数量 
     * @param record
     * @return
     * @author 王帆
     * @date 2018年12月25日 上午9:49:19
     */
    int accountData(ConfigTableEntity record);
    
    /**
     * 	根据条件查询表及其属性
     * @param record
     * @return
     * @author 王帆
     * @date 2018年12月25日 上午9:47:45
     */
    List<ConfigTableEntity> queryTableList(ConfigTableEntity record);
    
    /**
     *	 根据条件查询表 分页数据及其属性
     * @param record
     * @return
     * @author 王帆
     * @date 2018年12月29日 下午1:27:17
     */
    List<ConfigTableEntity> queryTableByPage(ConfigTableEntity record);
    
    /**
     * 根据主键可查询表与属性数据
     * @param id
     * @return
     * @author 王帆
     * @date 2018年12月26日 下午4:40:10
     */
    ConfigTableEntity queryTableByID(Integer id);
}