package com.sky.crawl.config.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sky.crawl.data.config.dao.entity.ConfigTableColumnEntity;
import com.sky.crawl.data.config.dao.entity.ConfigTableEntity;
import com.sky.pub.Page;
import com.sky.pub.common.exception.ResultException;

/**
 * 表及其属性配置服务
 * @author 王帆
 * @date  2018年12月25日 上午9:12:46
 */
@Service
public interface ConfigTableService {
	
	public List<ConfigTableEntity> getConfigTableList(ConfigTableEntity table);
	
	/**
	 * 配置主表分页
	 * @return
	 * @author 王帆
	 * @date 2018年12月25日 上午9:26:30
	 */
	public Page<ConfigTableEntity> getConfigTablePage(ConfigTableEntity table);
	
	/**
	 * 根据id获取表及其属性
	 * @param id
	 * @return
	 * @author 王帆
	 * @date 2018年12月25日 上午9:30:46
	 */
	public ConfigTableEntity getConfigTableById(Integer id);
	
	/**
	 * 根据条件获取表属性集合
	 * @param column
	 * @return
	 * @author 王帆
	 * @date 2018年12月25日 上午9:27:57
	 */
	public List<ConfigTableColumnEntity> getConfigTableColumnList(ConfigTableColumnEntity column);
	
	/**
	 * 添加表及其属性
	 * @param table
	 * @return
	 * @author 王帆
	 * @date 2018年12月25日 上午9:29:15
	 */
	public ConfigTableEntity addConfigTable(ConfigTableEntity table) throws ResultException;
	
	/**
	 * 更新表及其属性
	 * @param table
	 * @return
	 * @throws ResultException
	 * @author 王帆
	 * @date 2018年12月25日 上午9:32:29
	 */
	public ConfigTableEntity updateConfigTable(ConfigTableEntity table) throws ResultException;
	
	/**
	 * 删除表配置
	 * @param table
	 * @return
	 * @throws ResultException
	 * @author 王帆
	 * @date 2018年12月25日 上午9:33:02
	 */
	public Boolean delConfigTable(ConfigTableEntity table) throws ResultException;
	
	/**
	 * 表创建sql
	 * @param table
	 * @return
	 * @author 王帆
	 * @throws ResultException 
	 * @date 2018年12月25日 上午9:39:22
	 */
	public String getConfigTableCreateSql(ConfigTableEntity table) throws ResultException;

	/**
	 * 	根据表配置数据获取创建表的定义sql
	 * @param tempTable
	 * @return
	 * @throws ResultException
	 * @author 王帆
	 * @date 2019年1月7日 下午2:35:34
	 */
	public String getTableCreateConfigSqlString(ConfigTableEntity tempTable) throws ResultException;
}
