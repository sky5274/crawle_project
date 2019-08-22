package com.sky.flow.service;

import java.util.List;

import com.sky.flow.bean.FlowBean;
import com.sky.flow.bean.FlowGroupBean;
import com.sky.flow.bean.FlowNodeBean;
import com.sky.flow.bean.FlowNodeContainerBean;

/**
 *	流程查询service 
 * @author 王帆
 * @date  2019年5月16日 下午2:49:27
 */
public interface FlowQueryService {
	
	/**
	 * 根据用户查询可用流程分组
	 * @param userCode
	 * @return
	 * @author 王帆
	 * @date 2019年5月16日 下午2:59:59
	 */
	public List<FlowGroupBean> queryGroupsByUser(String userCode);
	
	/**
	 * 查询流程分组的codes对应的流程
	 * @param code
	 * @return
	 * @author 王帆
	 * @date 2019年5月16日 下午3:00:19
	 */
	public List<FlowGroupBean> queryGroupsByCode(String... code);
	
	/**
	 * 根据流程分组id查询流程分组信息
	 * @param groupId
	 * @return
	 * @author 王帆
	 * @date 2019年5月16日 下午3:00:44
	 */
	public FlowGroupBean queryGroup(String groupId);
	
	/**
	 * 根据流程分组id查询对应的流程
	 * @param groupId
	 * @return
	 * @author 王帆
	 * @date 2019年5月16日 下午3:01:14
	 */
	public List<FlowBean> queryByGroup(String groupId);
	
	/**
	 * 根据流程id查询流程信息
	 * @param flowId
	 * @return
	 * @author 王帆
	 * @date 2019年5月16日 下午3:01:46
	 */
	public FlowBean queryById(String flowId);
	
	/**
	 * 获取流程的简要信息，不包含节点信息
	 * @param flowId
	 * @return
	 * @author 王帆
	 * @date 2019年6月30日 下午4:27:50
	 */
	public FlowBean queryFlowSimById(String flowId);
	
	/**
	 * 查询流程的节点信息
	 * @param flowId
	 * @return
	 * @author 王帆
	 * @date 2019年5月16日 下午3:02:02
	 */
	public List<FlowNodeContainerBean> queryNodesByFLow(String flowId);
	
	/**
	 * 根据节点id查询流程节点信息
	 * @param flowId
	 * @param nodeId
	 * @return
	 * @author 王帆
	 * @date 2019年5月16日 下午3:03:02
	 */
	public FlowNodeContainerBean queryNodeById(String flowId,String nodeId);
	
	/**
	 * 查询节点的下一节点的链接信息
	 * @param flowId
	 * @param nodeId
	 * @return
	 * @author 王帆
	 * @date 2019年5月16日 下午3:03:46
	 */
	public List<FlowNodeContainerBean> queryNextNodeById(String flowId,String nodeId);

	/**
	 * 查询简要的流程节点信息
	 * @param flowId
	 * @return
	 * @author 王帆
	 * @date 2019年5月27日 下午6:42:59
	 */
	public List<FlowNodeBean> querySimpleNodesByFLow(String flowId);
	
	/**
	 * 查询流程的实用节点信息（容器节点展开），包含节点属性、链接、事件
	 * @param flowId
	 * @return
	 * @author 王帆
	 * @date 2019年6月30日 下午4:29:10
	 */
	public List<FlowNodeContainerBean> querySimpleNodesWithInfoByFLow(String flowId);
}
