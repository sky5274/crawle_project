package com.sky.flow.service;


import com.sky.flow.bean.FlowNodeBean;
import com.sky.flow.bean.FlowNodeContainerBean;
import com.sky.flow.exception.FlowException;

/**
 * 流程节点操作以及流程节点链接操作
 * @author 王帆
 * @date  2019年5月16日 下午3:28:09
 */
public interface FlowNodeActionService {
	
	/**
	 * 添加流程
	 * @param node
	 * @return
	 * @throws FlowException
	 * @author 王帆
	 * @date 2019年5月16日 下午4:25:21
	 */
	public FlowNodeBean addFlowNode(FlowNodeBean node) throws FlowException;
	
	/**
	 * 更新流程
	 * @param node
	 * @return
	 * @throws FlowException
	 * @author 王帆
	 * @date 2019年5月16日 下午4:28:50
	 */
	public int updateFlowNode(FlowNodeContainerBean node) throws FlowException;
	
	/**
	 * 作废流程节点
	 * @param node
	 * @return
	 * @throws FlowException
	 * @author 王帆
	 * @date 2019年5月16日 下午4:31:43
	 */
	public int disableFlowNode(FlowNodeBean node) throws FlowException;
	
	/**
	 * 删除流程节点
	 * @param node
	 * @return
	 * @throws FlowException
	 * @author wangfan
	 * @date 2021年2月12日 下午8:37:27
	 */
	public int deleteFlowNode(FlowNodeBean node) throws FlowException;

}
