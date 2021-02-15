package com.sky.flow.filter;

import com.sky.flow.bean.FlowBean;
import com.sky.flow.bean.FlowNodeContainerBean;
import com.sky.flow.contant.FlowContant;
import com.sky.flow.exception.FlowException;

public class TaskNodeFilterUtil {
	
	/**
	 *  根据节点id从流程中过滤流程节点信息
	 * @param flow
	 * @param nodeId
	 * @return
	 * @throws FlowException
	 * @author 王帆
	 * @date 2019年7月8日 下午3:54:00
	 */
	public static FlowNodeContainerBean getFlowNode(FlowBean flow,String nodeId) throws FlowException {
		for(FlowNodeContainerBean n:flow.getNodes()) {
			if(n.getId().equals(nodeId) && n.getStatus()>=0) {
				return n;
			}
			//从容器中选择节点
			FlowNodeContainerBean node = getFlowNodeByContainer(n,nodeId);
			if(node!=null && node.getStatus()>=0) {
				return node;
			}
		}
		return null;
	}
	
	/**从容器中获取目标节点*/
	private static FlowNodeContainerBean getFlowNodeByContainer(FlowNodeContainerBean n, String nodeId) {
		//容器内搜索流程节点
		if(FlowContant.NODE_TYPE_CONTAINER.equals(n.getType()) || (n.getType()!=null && n.getType().length()>FlowContant.NODE_TYPE_CONTAINER.length() && FlowContant.NODE_TYPE_CONTAINER.contains(n.getType().substring(FlowContant.NODE_TYPE_CONTAINER.length())))) {
			if(n.getContainer()!=null && !n.getContainer().isEmpty()) {
				for(FlowNodeContainerBean cn:n.getContainer()) {
					if(cn.getId().equals(nodeId) && n.getStatus()>=0) {
						return cn;
					}
					if(FlowContant.NODE_TYPE_CONTAINER.contains(n.getType().substring(FlowContant.NODE_TYPE_CONTAINER.length()))) {
						FlowNodeContainerBean node = getFlowNodeByContainer(cn,nodeId);
						if(node!=null && node.getStatus()>=0) {
							return node;
						}
					}
				}
			}
		}
		return null;
	}
}
