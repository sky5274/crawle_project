package com.sky.flow.service;


import com.sky.flow.bean.FlowBean;
import com.sky.flow.exception.FlowException;

/**
 * 	流程定义操作service
 * @author 王帆
 * @date  2019年5月16日 下午2:52:16
 */
public interface FlowDefinedActionService {
	
	/**
	 * 新增流程
	 * @param flow
	 * @return
	 * @throws FlowException
	 * @author 王帆
	 * @date 2019年5月16日 下午4:33:59
	 */
	public FlowBean addFlow(FlowBean flow) throws FlowException;
	
	/**
	 * 更新流程
	 * @param flow
	 * @return
	 * @throws FlowException
	 * @author 王帆
	 * @date 2019年5月16日 下午4:34:13
	 */
	public int updateFlow(FlowBean flow) throws FlowException;
	
	/**
	 * 作废流程
	 * @param flow
	 * @return
	 * @throws FlowException
	 * @author 王帆
	 * @date 2019年5月16日 下午4:34:30
	 */
	public int displayFlow(FlowBean flow) throws FlowException;
	
	/**
	 * 删除流程
	 * @param flow
	 * @return
	 * @throws FlowException
	 * @author wangfan
	 * @date 2021年2月12日 下午8:32:53
	 */
	public int deleteFlow(FlowBean flow) throws FlowException;
	
	
}
