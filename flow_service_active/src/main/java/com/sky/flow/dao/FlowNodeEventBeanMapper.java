package com.sky.flow.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sky.flow.bean.FlowNodeBean;
import com.sky.flow.bean.FlowNodeEventBean;

/**
 * 流程节点事件mapper
 * @author 王帆
 * @date  2019年5月18日 上午11:31:38
 */
@Mapper
public interface FlowNodeEventBeanMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FlowNodeEventBean record);

    int insertSelective(FlowNodeEventBean record);

    FlowNodeEventBean selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FlowNodeEventBean record);

    int updateByPrimaryKey(FlowNodeEventBean record);
    
    /**
     * 	根据节点id与流程id查询节点事件
     * @param record
     * @return
     * @author 王帆
     * @date 2019年5月19日 上午10:16:11
     */
    List<FlowNodeEventBean> selectByNode(FlowNodeBean record);

	int deleteByIds(List<Integer> ids);

	int insertBatch(List<FlowNodeEventBean> events);

	/**
	 * 根据流程id与节点id删除节点事件
	 * @param flowId
	 * @param nodeId
	 * @return
	 * @author 王帆
	 * @date 2019年5月27日 上午10:22:44
	 */
	int deleteByNode(@Param("flowId")String flowId, @Param("nodeId")String nodeId);
    
}