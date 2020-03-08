package com.sky.sm.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.sky.pub.PageRequest;
import com.sky.sm.bean.ProjectPropertyEnumBean;
import com.sky.sm.bean.PropertyEnumValueEntity;

@Mapper
public interface PropertyEnumValueEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PropertyEnumValueEntity record);

    int insertSelective(PropertyEnumValueEntity record);
    
    
    int insertBatch(List<PropertyEnumValueEntity> records);

    PropertyEnumValueEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PropertyEnumValueEntity record);

    int updateByPrimaryKey(PropertyEnumValueEntity record);
    
    
	List<PropertyEnumValueEntity> queryPropertyEnumValueList(@Param("page")PageRequest<?> pageProperty, @Param("pro")PropertyEnumValueEntity record);
	
	List<PropertyEnumValueEntity> queryPropertyEnumValues(@Param("pro")PropertyEnumValueEntity record);

	
	int accoutPropertyEnumValue(@Param("pro")PropertyEnumValueEntity record);
	
	
	ProjectPropertyEnumBean queryProjectEnumByGroupId(Integer groupId);
	
	List<ProjectPropertyEnumBean> queryProjectEnums(@Param("pro")ProjectPropertyEnumBean record);
	
	List<ProjectPropertyEnumBean> queryProjectEnumValue(@Param("pro")ProjectPropertyEnumBean record,@Param("enumNo")String enumNo);
}