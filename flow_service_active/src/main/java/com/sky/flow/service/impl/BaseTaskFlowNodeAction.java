package com.sky.flow.service.impl;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.sky.flow.bean.FlowBean;
import com.sky.flow.bean.FlowNodeBean;
import com.sky.flow.bean.FlowNodeEventBean;
import com.sky.flow.bean.TaskFlowBean;
import com.sky.flow.bean.TaskFlowInfoBean;
import com.sky.flow.bean.TaskFlowNodeBean;
import com.sky.flow.bean.TaskFlowNodeInfoBean;
import com.sky.flow.contant.FlowContant;
import com.sky.flow.contant.FlowContant.EVENTTYPE;
import com.sky.flow.dao.FlowSqlMapper;
import com.sky.flow.dao.TaskFlowBeanMapper;
import com.sky.flow.dao.TaskFlowNodeBeanMapper;
import com.sky.flow.event.factory.FlowEventEngineFactory;
import com.sky.flow.exception.FlowException;
import com.sky.flow.filter.TaskNodeFilter;
import com.sky.flow.filter.TaskNodeFilterUtil;
import com.sky.flow.pub.TaskContants;
import com.sky.flow.service.FlowQueryService;
import com.sky.flow.call.TaskFlowCallBack;

@Service
@Transactional(rollbackFor=Exception.class)
public class BaseTaskFlowNodeAction  extends AbstratTaskFlowNodeAction {
	@Autowired
	private TaskFlowNodeBeanMapper taskFlowNodeMapper;
	@Autowired
	private TaskFlowBeanMapper taskFlowMapper;
	@Autowired
	private FlowQueryService flowQueryService;
	@Autowired
	private TaskNodeFilter taskNodeFilter;
	@Autowired
	private FlowEventEngineFactory flowEventEngineFactory;
	@Autowired
	private FlowSqlMapper flowSqlMapper;
	 
	
	
	private FlowBean flow;
	private FlowNodeBean flowNode;
	private TaskFlowBean task;
	
	private void init(TaskFlowNodeBean node) throws FlowException {
		if(task==null) {
			task=taskFlowMapper.selectByPrimaryKey(node.getTaskId());
		}
		if(flow==null) {
			flow=flowQueryService.queryById(node.getFlowId());
		}
		if(task==null) {
			throw new FlowException("未找到流程任务信息");
		}
		if(flow==null) {
			throw new FlowException("未找到流程信息");
		}
		if(flowNode==null) {
			flowNode=getFlowNode(task.getFlowId(), node.getNodeId());
		}
		if(flowNode==null) {
			throw new FlowException("未找到流程节点信息");
		}
	}
	
	private FlowNodeBean getFlowNode(String flowId, String nodeId) throws FlowException {
		if(flow.getId().equals(flowId)) {
			return TaskNodeFilterUtil.getFlowNode(flow,nodeId);
		}else {
			throw new FlowException("任务节点的选择流程与当前流程不匹配");
		}
	}
	
	@Override
	public void preAddNode(TaskFlowNodeBean node) throws FlowException {
		init(node);
		
		//执行前置事件
		excEventByType(node,flowNode.getEvents(),FlowContant.EVENTTYPE.in);
		
	}

	@Override
	public TaskFlowNodeBean arroundAddNode(TaskFlowNodeBean node) throws FlowException {
		if(StringUtils.isEmpty(node.getId())) {
			node.setId(flowSqlMapper.queryShortUUID());
		}
		taskFlowNodeMapper.insert(node);
		//更新任务信息
		task.putParam(node);
		task.setStatus(node.getParam().getInteger(TaskContants.TASK_STATUS));
		taskFlowMapper.updateByPrimaryKeySelective(task);
		excEventOutLimit(node,flowNode.getEvents(),Arrays.asList(FlowContant.EVENTTYPE.in,FlowContant.EVENTTYPE.out,FlowContant.EVENTTYPE.back));
		return node;
	}

	@Override
	public void afterAddNode(TaskFlowNodeBean node) throws FlowException {
		excEventByType(node,flowNode.getEvents(),FlowContant.EVENTTYPE.out);
	}
	
	/**
	 * 执行不在设定范围内的事件
	 * @param node
	 * @param events
	 * @param limitTypes
	 * @author 王帆
	 * @throws FlowException 
	 * @date 2019年6月10日 下午1:19:47
	 */
	protected void excEventOutLimit(TaskFlowNodeBean node, List<FlowNodeEventBean> events, List<EVENTTYPE> limitTypes) throws FlowException {
		List<String> list=null;
		if(limitTypes!=null) {
			list=new LinkedList<>();
			for(EVENTTYPE type:limitTypes) {
				list.add(type.name());
			}
		}
		if(events !=null && !events.isEmpty()) {
			for(FlowNodeEventBean evt:events) {
				//不在限制范围内的运行执行
				if(!isInLimintEventType(evt.getEventType(), list)) {
					flowEventEngineFactory.invoke(evt, node, task);
				}
			}
		}
	}
	
	/**
	 * 执行特定的节点事件
	 * @param node
	 * @param events
	 * @param enmu
	 * @author 王帆
	 * @throws FlowException 
	 * @date 2019年6月10日 下午1:20:24
	 */
	protected void excEventByType(TaskFlowNodeBean node, List<FlowNodeEventBean> events, EVENTTYPE enmu) throws FlowException {
		if(events !=null && !events.isEmpty()) {
			for(FlowNodeEventBean evt:events) {
				//节点类型对应的事件的运行执行
				if(evt.getType().equals(enmu.name())) {
					System.err.println(enmu);
					flowEventEngineFactory.invoke(evt, node, task);
				}
			}
		}
	}
	
	private boolean isInLimintEventType(String type,List<String> limitTypes) {
		boolean flag=false;
		if(limitTypes!=null && !limitTypes.isEmpty()) {
			flag=limitTypes.contains(type);
		}
		return flag;
	}
	
	/**
	 * 初始化，获取任务流程的下一节点
	 * @param info
	 * @param node
	 * @return
	 * @throws FlowException
	 * @author 王帆
	 * @date 2019年6月10日 上午11:08:16
	 */
	private TaskFlowNodeBean initAndGetNextNode(TaskFlowInfoBean info, TaskFlowNodeBean node) throws FlowException {
		task=info.getTask();
		flow=info.getFlow();
		/**根据任务提交参数，使用过滤器过滤出流程的下一节点*/
		node=taskNodeFilter.filter(info, node);
		String node_type = node.getParam().getString(TaskContants.TASK_NODE_TYPE);
		switch (node_type) {
		case "start":
			node.putParams(TaskContants.TASK_STATUS, 0);
			break;
		case "end":
			node.putParams(TaskContants.TASK_STATUS, 3);
			break;

		default:
			node.putParams(TaskContants.TASK_STATUS, 1);
			break;
		}
		
		flowNode=getFlowNode(node.getFlowId(), node.getNodeId());
		return node; 
	}
	
	public TaskFlowNodeBean addNode(TaskFlowInfoBean info,TaskFlowNodeBean node)throws FlowException {
		node=initAndGetNextNode(info,node);
		return addNode(node);
	}

	public TaskFlowNodeBean addNode(TaskFlowInfoBean info, TaskFlowNodeBean node, TaskFlowCallBack call) throws FlowException {
		node=initAndGetNextNode(info,node);
		TaskFlowNodeInfoBean nodeInfo=new TaskFlowNodeInfoBean();
		return addNode(node,nodeInfo,call);
		
	}

	private TaskFlowNodeBean addNode(TaskFlowNodeBean node, TaskFlowNodeInfoBean nodeInfo,TaskFlowCallBack call) throws FlowException {
		if(node.getParam() !=null) {
			Integer status = (Integer) node.getParam().get(TaskContants.TASK_STATUS);
			if(status==null) {
				node.putParams(TaskContants.TASK_STATUS, 1);
			}
		}
		nodeInfo.setActiveNode(node);
		call.before(nodeInfo);
		preAddNode(node);
		node=arroundAddNode(node);
		nodeInfo.setActiveNode(node);
		call.arround(nodeInfo);
		afterAddNode(node);
		call.after(nodeInfo);
		return node;
	}

}
