package com.sky.flow.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sky.flow.bean.FlowNodeLinkBean;

/**
 * 流程节点链接mapper
 * @author 王帆
 * @date  2019年5月18日 上午11:33:38
 */
@Mapper
public interface FlowNodeLinkBeanMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FlowNodeLinkBean record);

    int insertSelective(FlowNodeLinkBean record);

    FlowNodeLinkBean selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FlowNodeLinkBean record);

    int updateByPrimaryKey(FlowNodeLinkBean record);

    /**
     * 	批量添加节点链接
     * @param nodes
     * @return
     * @author 王帆
     * @date 2019年5月18日 下午5:23:05
     */
	int insertBatch(List<FlowNodeLinkBean> nodes);

	/**
	 * 根据id集合删除节点关联数据
	 * @param ids
	 * @return
	 * @author 王帆
	 * @date 2019年5月18日 下午7:20:18
	 */
	int deleteByIds(@Param("list")List<Integer> ids, @Param("flowId") String flowId);

	/**
	 * 根据key集合删除节点关联数据
	 * @param keys
	 * @return
	 * @author 王帆
	 * @param flowId 
	 * @date 2019年5月18日 下午7:20:38
	 */
	int deleteByKeys(@Param("list")List<String> keys, @Param("flowId") String flowId);
	
	List<FlowNodeLinkBean> selectByFlow(String flowId);
	List<FlowNodeLinkBean> selectByNode(@Param("flowId")String flowId,@Param("nodeId")String nodeId);
	List<FlowNodeLinkBean> selectByUpNode(@Param("flowId")String flowId,@Param("nodeId")String upnodeId);

	/**
	 * 根据流程id与上级id删除流程链接
	 * @param flowId
	 * @param nodeId
	 * @return
	 * @author 王帆
	 * @date 2019年5月27日 上午10:15:09
	 */
	int deleteByNode(@Param("flowId")String flowId,@Param("nodeId") String nodeId);
}
