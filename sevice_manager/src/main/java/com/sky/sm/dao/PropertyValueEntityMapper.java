package com.sky.sm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sky.pub.PageRequest;
import com.sky.sm.bean.ProjectPropertyBean;
import com.sky.sm.bean.PropertyValueEntity;
import com.sky.sm.bean.req.PropertyValueReqEntity;

@Mapper
public interface PropertyValueEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PropertyValueEntity record);

    int insertSelective(PropertyValueEntity record);
    
    /**
     * 	批量新增配置属性
     * @param records
     * @return
     * @author 王帆
     * @date 2019年12月10日 下午4:03:56
     */
    int insertBatch(List<PropertyValueEntity> records);

    PropertyValueEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PropertyValueEntity record);

    int updateByPrimaryKey(PropertyValueEntity record);
    
    /**
     * 	根据条件查询属性配置数据
     * @param property
     * @return
     * @author 王帆
     * @param pageProperty 
     * @date 2019年3月7日 下午4:45:46
     */
	List<PropertyValueEntity> queryPropertyList(@Param("page")PageRequest<?> pageProperty, @Param("pro")PropertyValueReqEntity property);
	
	List<PropertyValueEntity> queryProperties(@Param("pro")PropertyValueEntity property);

	/**
     * 	根据条件查询属性配置数据统计
     * @param property
     * @return
     * @author 王帆
     * @date 2019年3月7日 下午4:45:46
     */
	int accoutProperty(@Param("pro")PropertyValueReqEntity property);
	
	/**
	 * 获取项目版本信息
	 * @param property
	 * @return
	 * @author wangfan
	 * @date 2020年2月2日 下午8:01:09
	 */
	PropertyValueEntity queryPropertyVersion(@Param("pro")PropertyValueReqEntity property);
	
	/**
	 * 获取项目的属性配置对象
	 * @param property
	 * @return
	 * @author 王帆
	 * @date 2019年3月8日 下午2:18:47
	 */
	List<ProjectPropertyBean> queryProjectProperties(@Param("pro")PropertyValueEntity property);
}