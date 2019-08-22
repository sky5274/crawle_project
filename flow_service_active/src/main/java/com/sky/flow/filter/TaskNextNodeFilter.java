package com.sky.flow.filter;

import java.util.LinkedList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sky.flow.bean.FlowBean;
import com.sky.flow.bean.FlowNodeContainerBean;
import com.sky.flow.bean.FlowNodeLinkBean;
import com.sky.flow.bean.TaskFlowInfoBean;
import com.sky.flow.bean.TaskFlowNodeBean;
import com.sky.flow.convert.TaskNodeConvert;
import com.sky.flow.exception.FlowException;
import com.sky.flow.pub.TaskContants;
import com.sky.flow.util.SpringExpressionUtil;

@Component
public class TaskNextNodeFilter implements TaskNodeFilter{
	@Autowired
	private TaskNodeConvert taskNodeConvert;
	private FlowBean flow;
	private TaskFlowNodeBean nowNode;
	private String userCode;
	private StringBuilder link=new StringBuilder();
	private Log log=LogFactory.getLog(getClass());
	
	private boolean isInTask(FlowNodeContainerBean flowNowNode) {
		return TaskNodeAuthoValidHandel.hasAutho(nowNode,flowNowNode, userCode);
	}
	
	@Override
	public TaskFlowNodeBean filter(TaskFlowInfoBean info, TaskFlowNodeBean node) throws FlowException {
		link=new StringBuilder();
		flow=info.getFlow();
		if(info.getNowNode()==null) {
			throw new FlowException("任务流程的当前节点获取失败！！！");
		}
		nowNode = info.getNowNode();
		userCode=node.getCreateCode();
		FlowNodeContainerBean flowNowNode = getFlowNode(nowNode.getFlowId(),nowNode.getNodeId());
		if(!isInTask(flowNowNode)) {
			throw new FlowException("任务流程的当前节点用户："+node.getCreateCode()+"无权限操作！！！");
		}
		link.append(flowNowNode.getId());
		if(StringUtils.isEmpty(node.getNodeId())) {
			log.debug("node filter next node");
			//未确定的流程节点，根据参数寻找下一流程节点
			FlowNodeContainerBean nextNode = getNextFlowNode(flowNowNode,node.getParam(),true);
			if(nextNode==null) {
				throw new FlowException("根据参数未找到匹配的下级流程节点");
			}
			log.debug("task node filter transeform:\r now:  "+flowNowNode+" \r->next:  "+JSON.toJSONString(nextNode)+"");
			node=taskNodeConvert.convert(info.getTask(),node, flow, nextNode);
		}else {
			log.debug("node filter valid the flow node");
			//已存在的设置流程节点，校验流程节点是否存在
			FlowNodeContainerBean nowFlowNode = getFlowNode(node.getFlowId(), node.getNodeId());
			if(nowFlowNode==null) {
				throw new FlowException("自定义的流程任务节点不在任务对应的流程中");
			}
			link.append(",").append(nowFlowNode.getId());
			node=taskNodeConvert.convert(info.getTask(),node, flow, nowFlowNode);
		}
		if(link.toString().contains(",")) {
			node.putParams(TaskContants.TASK_LINK, link.toString());
		}
		node.putParams(TaskContants.TASK_UPNODE, nowNode.getId());
		log.info("by filter the task node is :"+node);
		return node;
	}

	private FlowNodeContainerBean getFlowNode(String flowId,String nodeId) throws FlowException {
		if(flow.getId().equals(flowId)) {
			return TaskNodeFilterUtil.getFlowNode(flow,nodeId);
		}else {
			throw new FlowException("任务节点的选择流程与当前流程不匹配");
		}
	}
	

	/**
	 * 执行流程走向判断
	 * @param flowNowNode
	 * @param jsonObject
	 * @throws FlowException
	 * @author 王帆
	 * @param flowBean 
	 * @return 
	 * @date 2019年6月8日 下午11:18:29
	 */
	public FlowNodeContainerBean getNextFlowNode( FlowNodeContainerBean flowNowNode, JSONObject param,boolean flag) throws FlowException {
		FlowNodeContainerBean nextNode=flag?null:flowNowNode;
		if(flag && (flowNowNode.getLinks()==null || flowNowNode.getLinks().isEmpty())) {
			throw new FlowException("流程节点："+flowNowNode.getKey()+" 不存在下级节点，故无法确定流程走向");
		}
		if(flowNowNode.getLinks().size()==1) {
			/*
			 * 找到的节点为空时，使用默认节点返回
			 * 只有一个节点时，
			 * 		如果没有条件,根据链接找到对应节点
			 * 		有条件时，判断条件是否满足，满足时，尝试获取获取下一节点
			 */
			FlowNodeLinkBean nextLink = flowNowNode.getLinks().get(0);
			boolean isEmpty=StringUtils.isEmpty(nextLink.getCondition());
			boolean isMatch = isEmpty?false:matchLinkNode(nextLink.getCondition(),param);
			if( isEmpty || isMatch) {
				FlowNodeContainerBean node = getFlowNode(flowNowNode.getFlowId(),nextLink.getDownNodeId());
				if((isMatch || flag) && node==null) {
					throw new FlowException("流程节点："+flowNowNode.getKey()+" 仅存的链接节点信息未找到");
				}
				if(node!=null) {
					nextNode=node;
				}
			}
		}else {
			//根据链接条件确定下级节点
			FlowNodeContainerBean node = getNextFlowNodeByLinks(flowNowNode,param);
			if(node!=null) {
				nextNode=node;
			}
		}
		/*
		 * 如果参数中有自动标识，则继续往下找节点
		 * */
		if(isAuto(param) && nextNode!=null) {
			//任务节点是否控制（默认控制）,判断操作人员是否在配置的人员或任务节点限制的人员中
			//任务节点不控制时，直接根据条件寻找下一节点
			if( !isController(param)||isInTask(nextNode)) {
				nextNode=getNextFlowNode(nextNode, param, false);
			}
		}
		if(nextNode!=null) {
			link.append(",").append(nextNode.getId());
		}
		return nextNode;
	}

	/**
	 * 根据参数与条件公式配置链接节点
	 * @param flowNowNode
	 * @param param
	 * @return
	 * @throws FlowException
	 * @author 王帆
	 * @date 2019年6月11日 上午9:32:35
	 */
	public FlowNodeContainerBean getNextFlowNodeByLinks( FlowNodeContainerBean flowNowNode, JSONObject param) throws FlowException {
		for(FlowNodeLinkBean link:flowNowNode.getLinks()) {
			if(!StringUtils.isEmpty(link.getCondition()) && matchLinkNode(link.getCondition(), param)) {
				return getFlowNode(flowNowNode.getFlowId(),link.getDownNodeId());
			}
		}
		return null;
	}

	/**是否自动寻找下一节点（默认false）*/
	private boolean isAuto(JSONObject param) {
		boolean flag=false;
		if(param !=null ) {
			Boolean isauto = param.getBoolean(TaskContants.TASK_AUTO);
			if(isauto!=null) {
				flag=isauto;
			}
		}
		return flag;
	}
	/**是否人员控制（默认ture）*/
	private boolean isController(JSONObject param) {
		boolean flag=true;
		if(param !=null ) {
			Boolean isController = param.getBoolean(TaskContants.TASK_CONTROLLER);
			if(isController!=null) {
				flag=isController;
			}
		}
		return flag;
	}

	/**
	 * 链接携带的参数条件是否与链接条件相符
	 * @param linkCondtion
	 * @param param
	 * @return
	 * @author 王帆
	 * @date 2019年6月8日 下午11:56:48
	 */
	public boolean matchLinkNode(String linkCondtion, JSONObject param) {
		try {
			return SpringExpressionUtil.match(linkCondtion, param);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}


	/**
	 * 根据流程链接获取节点对应的下级流程节点
	 * @param flowNowNode
	 * @param link
	 * @return
	 * @throws FlowException
	 * @author 王帆
	 * @date 2019年6月8日 下午11:34:42
	 */
	@SuppressWarnings("unused")
	private FlowNodeContainerBean getFlowNodeByLink(FlowNodeContainerBean flowNowNode, FlowNodeLinkBean link) throws FlowException {
		List<String> errs=new LinkedList<>();
		if(link==null) {
			errs.add("节点链接属性未提交");
		}
		if(flowNowNode==null) {
			errs.add("流程信息未提交");
		}
		if(!errs.isEmpty()) {
			throw new FlowException(errs);
		}
		for(FlowNodeContainerBean node:flowNowNode.getNext()) {
			if(link.getDownNodeId().equals(node.getId())) {
				return node;
			}
		}
		throw new FlowException("根据流程链接：[up:"+link.getUpNodeId()+",down:"+link.getDownNodeId()+"] 未找到对应的流程下级节点，请检查流程配置");
	}

}
