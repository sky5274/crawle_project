package com.crawl.config.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.crawl.config.service.ConfigTableService;
import com.crawl.data.config.dao.ConfigTableColumnEntityMapper;
import com.crawl.data.config.dao.ConfigTableEntityMapper;
import com.crawl.data.config.dao.entity.ConfigTableColumnEntity;
import com.crawl.data.config.dao.entity.ConfigTableEntity;
import com.sky.pub.Page;
import com.sky.pub.common.exception.ReslutException;

/**
 * 表及其属性配置
 * @author 王帆
 * @date  2018年12月25日 上午9:21:53
 */
@Service
public class ConfigTableServiceImpl implements ConfigTableService{
	@Resource
	private ConfigTableEntityMapper configTableMapper;
	@Resource
	private ConfigTableColumnEntityMapper configTableColumnMapper;
	
	@Override
	public List<ConfigTableEntity> getConfigTableList() {
		return null;
	}

	@Override
	public Page<ConfigTableEntity> getConfigTablePage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ConfigTableEntity getConfigTableById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ConfigTableColumnEntity> getConfigTableColumnList(ConfigTableColumnEntity column) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ConfigTableEntity addConfigTable(ConfigTableEntity table) throws ReslutException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ConfigTableEntity updateConfigTable(ConfigTableEntity table) throws ReslutException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean delConfigTable(ConfigTableEntity table) throws ReslutException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getConfigTableCreateSql(ConfigTableEntity table) {
		// TODO Auto-generated method stub
		return null;
	}
}
