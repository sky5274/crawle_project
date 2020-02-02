package com.sky.sm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sky.pub.Page;
import com.sky.pub.PageRequest;
import com.sky.sm.bean.ProjectPropertyEnumBean;
import com.sky.sm.bean.PropertyEnumEntity;
import com.sky.sm.bean.PropertyEnumValueEntity;
import com.sky.sm.dao.PropertyEnumEntityMapper;
import com.sky.sm.dao.PropertyEnumValueEntityMapper;
import com.sky.sm.service.PropertyEnumService;

@Service
public class PropertyEnumServiceImpl implements PropertyEnumService{
	@Autowired
	private PropertyEnumEntityMapper propertyEnumEntityMapper;
	@Autowired
	private PropertyEnumValueEntityMapper propertyEnumValueEntityMapper;

	@Override
	public int addEnumGroup(PropertyEnumEntity enumd) {
		return propertyEnumEntityMapper.insertSelective(enumd);
	}

	@Override
	public int updateEnumGroup(PropertyEnumEntity enumd) {
		return propertyEnumEntityMapper.updateByPrimaryKey(enumd);
	}

	@Override
	public int delEnumGroup(PropertyEnumEntity enumd) {
		return propertyEnumEntityMapper.deleteByPrimaryKey(enumd.getId());
	}

	@Override
	public Page<PropertyEnumEntity> queryEnumPage(PageRequest<PropertyEnumEntity> enump) {
		Page<PropertyEnumEntity> page=new Page<PropertyEnumEntity>();
		page.setPageData(enump);
		page.setTotal(propertyEnumEntityMapper.accoutPropertyEnums(enump.getData()));
		page.setList(propertyEnumEntityMapper.queryPropertyEnumList(enump, enump.getData()));
		return page;
	}

	@Override
	public List<ProjectPropertyEnumBean> queryProjectEnumList(ProjectPropertyEnumBean enumd) {
		return propertyEnumValueEntityMapper.queryProjectEnums(enumd);
	}

	@Override
	public int addEnumValue(PropertyEnumValueEntity enumVal) {
		return propertyEnumValueEntityMapper.insertSelective(enumVal);
	}

	@Override
	public int updateEnumValue(PropertyEnumValueEntity enumVal) {
		return propertyEnumValueEntityMapper.updateByPrimaryKeySelective(enumVal);
	}

	@Override
	public int delEnumValue(PropertyEnumValueEntity enumVal) {
		return propertyEnumValueEntityMapper.deleteByPrimaryKey(enumVal.getId());
	}

	@Override
	public Page<PropertyEnumValueEntity> queryEnumValuePage(PageRequest<PropertyEnumValueEntity> enump) {
		Page<PropertyEnumValueEntity> page=new Page<PropertyEnumValueEntity>();
		page.setPageData(enump);
		page.setTotal(propertyEnumValueEntityMapper.accoutPropertyEnumValue(enump.getData()));
		page.setList(propertyEnumValueEntityMapper.queryPropertyEnumValueList(enump, enump.getData()));
		return page;
	}

	@Override
	public ProjectPropertyEnumBean queryEnumbById(Integer groupId) {
		return propertyEnumValueEntityMapper.queryProjectEnumByGroupId(groupId);
	}

	@Override
	public List<ProjectPropertyEnumBean> getPropertyEnmuValue(String enumNo, ProjectPropertyEnumBean enump) {
		return propertyEnumValueEntityMapper.queryProjectEnumValue(enump, enumNo);
	}

}
