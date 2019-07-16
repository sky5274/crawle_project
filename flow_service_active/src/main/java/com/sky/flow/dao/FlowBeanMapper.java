package com.sky.flow.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sky.flow.bean.FlowBean;

/**
 * 流程mapper
 * @author 王帆
 * @date  2019年5月18日 上午11:30:47
 */
@Mapper
public interface FlowBeanMapper {
    int deleteByPrimaryKey(String id);

    int insert(FlowBean record);

    int insertSelective(FlowBean record);

    FlowBean selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(FlowBean record);

    int updateByPrimaryKey(FlowBean record);

	List<FlowBean> selectByGroupId(@Param("groupId")String groupId);
    
}