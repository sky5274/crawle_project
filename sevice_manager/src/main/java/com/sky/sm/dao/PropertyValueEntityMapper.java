package com.sky.sm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sky.sm.bean.ProjectPropertyBean;
import com.sky.sm.bean.PropertyValueEntity;
import com.sky.sm.bean.req.PropertyValueReqEntity;

@Mapper
public interface PropertyValueEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PropertyValueEntity record);

    int insertSelective(PropertyValueEntity record);

    PropertyValueEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PropertyValueEntity record);

    int updateByPrimaryKey(PropertyValueEntity record);
    
    /**
     * 	根据条件查询属性配置数据
     * @param property
     * @return
     * @author 王帆
     * @date 2019年3月7日 下午4:45:46
     */
	List<PropertyValueEntity> queryPropertyList(PropertyValueReqEntity property);
	
	List<PropertyValueEntity> queryProperties(PropertyValueEntity property);

	/**
     * 	根据条件查询属性配置数据统计
     * @param property
     * @return
     * @author 王帆
     * @date 2019年3月7日 下午4:45:46
     */
	int accoutProperty(PropertyValueReqEntity property);
	
	/**
	 * 获取项目的属性配置对象
	 * @param property
	 * @return
	 * @author 王帆
	 * @date 2019年3月8日 下午2:18:47
	 */
	List<ProjectPropertyBean> queryProjectProperties(PropertyValueEntity property);
}