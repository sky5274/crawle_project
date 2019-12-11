package com.sky.flow.filter;

import java.util.Arrays;

import org.springframework.util.StringUtils;

import com.sky.flow.bean.FlowNodeBean;
import com.sky.flow.bean.TaskFlowNodeBean;
import com.sky.flow.exception.FlowException;
import com.sky.flow.service.FlowQueryService;
import com.sky.flow.util.SpringBeanUtil;

/**
 *   操作人流程控制校验（当前流程任务是否有权）
 * @author 王帆
 * @date  2019年7月4日 上午10:48:16
 */
public class TaskNodeAuthoValidHandel {
	
	
	public static boolean hasAutho(String flowId, String nodeId,String userCode) {
		FlowQueryService flowQueryService = SpringBeanUtil.getBean(FlowQueryService.class);
		return hasAutho(flowQueryService.queryNodeById(flowId, nodeId), userCode);
	}
	
	/**
	 *    判断用户编码是否在流程的的任务限制人员中
	 * @param node
	 * @param userCode
	 * @return
	 * @author 王帆
	 * @date 2019年7月4日 上午10:53:14
	 */
	public static boolean hasAutho(FlowNodeBean node ,String userCode) {
		if(node==null || "end".equals(node.getType())) {
			return false;
		}
		if(StringUtils.isEmpty(node.getAuthCodes())) {
			//未设置流程权限人员的默认所有人都可以
			return true;
		}
		//用户编码是否存在，是否限制环节人员
		return hasUserCode(userCode) && Arrays.asList(node.getAuthCodes().split(",")).contains(userCode);
	}
	
	/**
	 * 	任务限定于流程结构限定判断
	 * @param taskNode
	 * @param node
	 * @param userCode
	 * @return
	 * @throws FlowException
	 * @author 王帆
	 * @date 2019年7月4日 上午11:08:16
	 */
	public static boolean hasAutho(TaskFlowNodeBean taskNode,FlowNodeBean node ,String userCode) {
		if(taskNode==null) {
			return false;
		}
		if(StringUtils.isEmpty(taskNode.getLimitUser())) {
			return hasAutho(node, userCode);
		}else {
			return hasAutho(node, userCode) && Arrays.asList(taskNode.getLimitUser().split(",")).contains(userCode);
		}
	}
	
	public static boolean hasAutho(TaskFlowNodeBean taskNode,String userCode) {
		if(taskNode==null) {
			return false;
		}
		if(StringUtils.isEmpty(taskNode.getLimitUser())) {
			return hasAutho(taskNode.getFlowId(),taskNode.getNodeId(), userCode);
		}else {
			return hasAutho(taskNode.getFlowId(),taskNode.getNodeId(), userCode) && Arrays.asList(taskNode.getLimitUser().split(",")).contains(userCode);
		}
	}
	
	private static boolean  hasUserCode(String userCode) {
		return !StringUtils.isEmpty(userCode);	
	}
}
