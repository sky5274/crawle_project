package com.sky.flow.service;

import java.util.List;

import com.sky.flow.bean.FlowNodeEventBean;
import com.sky.flow.exception.FlowException;

public interface FlowNodeEventActionService {
	
	/**
	 * 添加节点事件
	 * @param event
	 * @return
	 * @author 王帆
	 * @date 2019年5月19日 下午4:27:52
	 */
	public FlowNodeEventBean addFlowNodeEvent(FlowNodeEventBean event) throws FlowException;
	
	/**
	 * 更新节点事件
	 * @param event
	 * @return
	 * @author 王帆
	 * @date 2019年5月19日 下午4:27:43
	 */
	public int updateFlowNodeEvent(FlowNodeEventBean event) throws FlowException;
	/**
	 * 删除节点事件
	 * @param ids
	 * @return
	 * @author 王帆
	 * @throws FlowException 
	 * @date 2019年5月19日 下午4:27:27
	 */
	public int deleteFlowNodeEvents(List<Integer> ids) throws FlowException;
}
