package com.sky.sm.service;

import java.util.List;

import com.sky.pub.Page;
import com.sky.pub.PageRequest;
import com.sky.sm.bean.ProjectPropertyEnumBean;
import com.sky.sm.bean.PropertyEnumEntity;
import com.sky.sm.bean.PropertyEnumValueEntity;

/**
 * project property enum service
 * @author wangfan
 * @date 2020年1月31日 下午4:26:45
 */
public interface PropertyEnumService {
	
	public int addEnumGroup(PropertyEnumEntity enums);
	public int updateEnumGroup(PropertyEnumEntity enums);
	public int delEnumGroup(PropertyEnumEntity enums);
	public Page<PropertyEnumEntity> queryEnumPage(PageRequest<PropertyEnumEntity> enump);
	
	/**
	 * 查询项目配置枚举
	 * @param enumd
	 * @return
	 * @author wangfan
	 * @date 2020年1月31日 下午4:32:17
	 */
	public List<ProjectPropertyEnumBean> queryProjectEnumList(ProjectPropertyEnumBean enumd);
	
	public int addEnumValue(PropertyEnumValueEntity enumVal);
	public int updateEnumValue(PropertyEnumValueEntity enumVal);
	public int delEnumValue(PropertyEnumValueEntity enumVal);
	public Page<PropertyEnumValueEntity> queryEnumValuePage(PageRequest<PropertyEnumValueEntity> enump);
	
	/**
	 * 根据分组id查询 配置枚举信息
	 * @param id
	 * @return
	 * @author wangfan
	 * @date 2020年1月31日 下午10:21:54
	 */
	public ProjectPropertyEnumBean queryEnumbById(Integer groupId);
	
	/**
	 * 查询配置枚举value信息
	 * @param enumNo
	 * @param enump
	 * @return
	 * @author wangfan
	 * @date 2020年1月31日 下午10:22:23
	 */
	public List<ProjectPropertyEnumBean> getPropertyEnmuValue(String enumNo, ProjectPropertyEnumBean enump);
}
