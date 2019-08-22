package com.sky.flow.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sky.flow.bean.FlowNodeAttrBean;

@Mapper
public interface FlowNodeAttrBeanMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FlowNodeAttrBean record);

    int insertSelective(FlowNodeAttrBean record);

    FlowNodeAttrBean selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FlowNodeAttrBean record);

    int updateByPrimaryKey(FlowNodeAttrBean record);
    
    List<FlowNodeAttrBean> selectByNode(@Param("flowId")String flowId, @Param("nodeId")String nodeId);

	int deleteByIds(List<Integer> ids);
	
	/**
	 * 批量添加节点属性
	 * @param attrs
	 * @return
	 * @author 王帆
	 * @date 2019年5月27日 上午9:55:06
	 */
	int insertBatch(List<FlowNodeAttrBean> attrs);

	/**
	 * 根据节点id与流程id删除节点关联的所有属性
	 * @param flowId
	 * @param nodeId
	 * @return
	 * @author 王帆
	 * @date 2019年5月27日 上午9:58:47
	 */
	int deleteByNode(@Param("flowId")String flowId, @Param("nodeId")String nodeId);
}