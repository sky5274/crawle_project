package com.sky.flow.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sky.flow.bean.FlowNodeBean;
import com.sky.flow.bean.FlowNodeContainerBean;

/**
 * 流程节点mapper
 * @author 王帆
 * @date  2019年5月18日 上午11:31:09
 */
@Mapper
public interface FlowNodeBeanMapper {
    int deleteByPrimaryKey(String id);

    int insert(FlowNodeBean record);

    int insertSelective(FlowNodeBean record);

    FlowNodeBean selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(FlowNodeBean record);

    int updateByPrimaryKey(FlowNodeBean record);
    
    /**
     * 	根据流程id查询流程下基本的节点信息
     * @param flowId
     * @return
     * @author 王帆
     * @date 2019年5月19日 上午11:07:54
     */
    List<FlowNodeBean> selectBaseNodeByFlow(String flowId);
    
    /**
     * 查询简要的流程节点信息
     * @param flowId
     * @return
     * @author 王帆
     * @date 2019年5月27日 下午6:41:34
     */
    List<FlowNodeBean> selectSimpleNodes(@Param("flowId") String flowId);
    
    /**
     * 查询节点的简要信息包含节点属性
     * @param flowId
     * @return
     * @author 王帆
     * @date 2019年6月30日 下午4:25:07
     */
    List<FlowNodeContainerBean> selectSimpleWithInfoNodes(@Param("flowId") String flowId);
    
    /**
     * 	根据流程id查询流程下节点信息（包含节点与下一节点）
     * @param flowId
     * @return
     * @author 王帆
     * @date 2019年5月19日 上午11:07:54
     */
    List<FlowNodeContainerBean> selectNodeByFlow(String flowId);
    
    /**
     * 	根据流程id与节点id 查询流程节点信息（包含节点与下一节点）
     * @param flowId
     * @return
     * @author 王帆
     * @date 2019年5月19日 上午11:07:54
     */
    FlowNodeContainerBean selectNode(@Param("flowId")String flowId,@Param("nodeId")String nodeId);
    
    /**
     * 根据流程id与节点id 查询流程节点对应的下一节点信息（包含节点与下一节点）
     * @param flowId
     * @param nodeId
     * @return
     * @author 王帆
     * @date 2019年5月19日 上午11:20:14
     */
    List<FlowNodeContainerBean> selectNextNode(@Param("flowId")String flowId,@Param("nodeId")String nodeId);
    
    /**
     * 获取容器节点
     * @param flowId
     * @param containerId
     * @return
     * @author 王帆
     * @date 2019年6月23日 上午11:57:48
     */
    List<FlowNodeContainerBean> selectBaseNodeByContianerFlow(@Param("flowId")String flowId,@Param("containerId")String containerId);
}