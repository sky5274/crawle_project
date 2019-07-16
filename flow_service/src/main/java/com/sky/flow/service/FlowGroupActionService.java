package com.sky.flow.service;

import com.sky.flow.bean.FlowGroupBean;
import com.sky.flow.exception.FlowException;

/**
 * 流程分组定义service
 * @author 王帆
 * @date  2019年5月16日 下午3:28:59
 */
public interface FlowGroupActionService {
	
	/**
	 * 新增分组
	 * @param group
	 * @return
	 * @throws FlowException
	 * @author 王帆
	 * @date 2019年5月16日 下午4:34:56
	 */
	public FlowGroupBean addGroup(FlowGroupBean group) throws FlowException;
	
	/**
	 * 更新分组
	 * @param group
	 * @return
	 * @throws FlowException
	 * @author 王帆
	 * @date 2019年5月16日 下午4:35:07
	 */
	public int updateGroup(FlowGroupBean group) throws FlowException;
}
