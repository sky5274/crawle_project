package com.sky.flow.service;

import java.util.List;

import com.sky.flow.bean.FlowNodeAttrBean;
import com.sky.flow.exception.FlowException;

public interface FlowNodeAttrActionService {
	
	/**
	 * 新增节点属性
	 * @param attr
	 * @return
	 * @author 王帆
	 * @throws FlowException 
	 * @date 2019年5月19日 上午11:27:03
	 */
	public FlowNodeAttrBean addNodeAttr(FlowNodeAttrBean attr) throws FlowException;
	
	/**
	 * 更新节点属性
	 * @param attr
	 * @return
	 * @throws FlowException
	 * @author 王帆
	 * @date 2019年6月27日 上午10:38:05
	 */
	public int updateNodeAttr(FlowNodeAttrBean attr) throws FlowException;
	
	/**
	 * 删除节点属性
	 * @param ids
	 * @return
	 * @author 王帆
	 * @throws FlowException 
	 * @date 2019年5月19日 上午11:27:16
	 */
	public int deleteNodeAttrs(List<Integer> ids) throws FlowException;
}
