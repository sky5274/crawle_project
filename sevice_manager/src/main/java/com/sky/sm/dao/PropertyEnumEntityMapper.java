package com.sky.sm.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.sky.pub.PageRequest;
import com.sky.sm.bean.PropertyEnumEntity;

@Mapper
public interface PropertyEnumEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PropertyEnumEntity record);

    int insertSelective(PropertyEnumEntity record);
    
    
    int insertBatch(List<PropertyEnumEntity> records);

    PropertyEnumEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PropertyEnumEntity record);

    int updateByPrimaryKey(PropertyEnumEntity record);
    
   
	List<PropertyEnumEntity> queryPropertyEnumList(@Param("page")PageRequest<?> pageProperty, @Param("pro")PropertyEnumEntity property);
	
	List<PropertyEnumEntity> queryPropertyEnums(@Param("pro")PropertyEnumEntity property);

	int accoutPropertyEnums(@Param("pro")PropertyEnumEntity property);
	
}