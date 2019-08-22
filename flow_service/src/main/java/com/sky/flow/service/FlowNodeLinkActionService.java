package com.sky.flow.service;

import java.util.List;

import com.sky.flow.bean.FlowNodeLinkBean;
import com.sky.flow.exception.FlowException;

public interface FlowNodeLinkActionService {
	
	/**
	 * 新增节点链接
	 * @param link
	 * @return
	 * @author 王帆
	 * @date 2019年5月19日 下午4:25:30
	 */
	public FlowNodeLinkBean addFlowNodeLink(FlowNodeLinkBean link) throws FlowException;
	
	/**
	 * 更新节点链接
	 * @param link
	 * @return
	 * @author 王帆
	 * @date 2019年5月19日 下午4:25:45
	 */
	public int updateFlowNodeLink(FlowNodeLinkBean link) throws FlowException;
	
	/**
	 * 批量添加流程节点链接
	 * @param links
	 * @return
	 * @throws FlowException
	 * @author 王帆
	 * @date 2019年5月16日 下午4:32:40
	 */
	public int addFlowNodeLink(List<FlowNodeLinkBean> links) throws FlowException;
	
	/**
	 * 根据链接id集合删除节点链接
	 * @param ids
	 * @return
	 * @throws FlowException
	 * @author 王帆
	 * @date 2019年5月16日 下午4:33:02
	 */
	public int deleteFlowNodeLink(List<Integer> ids,String flowId) throws FlowException;
	
	/**
	 * 根据链接key删除节点链接
	 * @param keys
	 * @return
	 * @throws FlowException
	 * @author 王帆
	 * @date 2019年5月16日 下午4:33:31
	 */
	public int deleteFlowNodeLinkByKey(List<String> keys,String flowId) throws FlowException;
}
