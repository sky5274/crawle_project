package com.sky.flow.service.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sky.flow.bean.BaseTableBean;
import com.sky.flow.bean.FlowBean;
import com.sky.flow.bean.FlowNodeBean;
import com.sky.flow.bean.FlowNodeContainerBean;
import com.sky.flow.bean.TaskFlowBean;
import com.sky.flow.bean.TaskFlowDetailBean;
import com.sky.flow.bean.TaskFlowInfoBean;
import com.sky.flow.bean.TaskFlowNodeBean;
import com.sky.flow.contant.FlowContant.EVENTTYPE;
import com.sky.flow.convert.TaskNodeConvert;
import com.sky.flow.dao.FlowSqlMapper;
import com.sky.flow.dao.TaskFlowBeanMapper;
import com.sky.flow.dao.TaskFlowNodeBeanMapper;
import com.sky.flow.exception.FlowException;
import com.sky.flow.filter.TaskNodeAuthoValidHandel;
import com.sky.flow.pub.Page;
import com.sky.flow.pub.PageRequest;
import com.sky.flow.pub.TaskContants;
import com.sky.flow.service.FlowQueryService;
import com.sky.flow.service.TaskFlowActionService;
import com.sky.flow.call.TaskFlowCallBack;
import com.sky.flow.service.TaskFlowQueryService;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Transactional(rollbackFor=Exception.class)
public class TaskFlowServiceImpl implements TaskFlowActionService,TaskFlowQueryService{
	@Autowired
	private FlowQueryService flowQuerySevice;
	@Autowired
	private TaskFlowBeanMapper taskFlowMapper;
	@Autowired
	private TaskFlowNodeBeanMapper taskFlowNodeMapper;
	@Autowired
	private FlowSqlMapper flowSqlMapper;
	@Autowired
	private BaseTaskFlowNodeAction baseTaskFlowNodeAction;
	@Autowired
	private TaskNodeConvert taskNodeConvert;
	
	
	@Override
	public List<TaskFlowBean> queryTask(TaskFlowBean task) {
		return taskFlowMapper.selectByParam(null,task);
	}

	@Override
	public Page<TaskFlowBean> queryTaskByPage(PageRequest<TaskFlowBean> taskpage) {
		taskpage.initPage();
		Page<TaskFlowBean> page=new Page<>();
		page.setPageData(taskpage);
		List<TaskFlowBean> tasks = taskFlowMapper.selectByParam(taskpage,taskpage.getData());
		for(TaskFlowBean t:tasks) {
			if(taskpage.getData() !=null && t.getInNowTaskNode()==null) {
				t.setInNowTaskNode(isInTaskNodeFlow(t.getId(),taskpage.getData().getOpretorCode()));
			}
		}
		page.setList(tasks);
		page.setTotal(taskFlowMapper.accoutByParam(taskpage.getData()));
		return page;
	}
	
	private boolean isInTaskNodeFlow(String taskId, String userCode) {
		return isInTaskNodeFlow(taskId, queryTaskNowNode(taskId), userCode);
	}
	private boolean isInTaskNodeFlow(String taskId, TaskFlowNodeBean node ,String userCode) {
		return TaskNodeAuthoValidHandel.hasAutho(node, userCode);
	}
	private boolean isInTaskFlow(TaskFlowNodeBean tasknode,FlowNodeBean node ,String userCode) {
		return TaskNodeAuthoValidHandel.hasAutho(tasknode,node, userCode);
	}

	@Override
	public List<TaskFlowNodeBean> queryTaskDetails(String taskId) {
		return taskFlowNodeMapper.selectByTask(taskId);
	}

	@Override
	public List<TaskFlowNodeBean> queryTaskDetails(TaskFlowNodeBean node) {
		return taskFlowNodeMapper.selectByparam(node);
	}

	@Override
	public TaskFlowNodeBean queryTaskNowNode(String taskId) {
		return taskFlowNodeMapper.selectNowNodeByTask(taskId,0);
	}

	@Override
	public TaskFlowDetailBean queryTask(String taskId,String opretorCode) {
		TaskFlowDetailBean task = queryTask(taskId);
		if(task!=null && task.getInNowTaskNode()==null) {
			task.setInNowTaskNode(isInTaskNodeFlow(taskId, opretorCode));
		}
		return task;
	}
	public TaskFlowDetailBean queryTask(String taskId) {
		TaskFlowDetailBean task=new TaskFlowDetailBean();
		BeanUtils.copyProperties(taskFlowMapper.selectByPrimaryKey(taskId), task);
		return task;
	}

	@Override
	public TaskFlowDetailBean queryTaskInfo(String taskId,String opretorCode) throws FlowException {
		TaskFlowDetailBean task = queryTask(taskId);
		if(task!=null) {
			task.setDetails(queryTaskDetails(taskId));
			if(task.getDetails()!=null && !task.getDetails().isEmpty() && task.getInNowTaskNode()==null) {
				try {
					TaskFlowNodeBean nowNode = task.getDetails().get(task.getDetails().size()-1);
					if(isInTaskNodeFlow(taskId,nowNode,opretorCode) && !"end".equals(nowNode.getParam().getString(TaskContants.TASK_NODE_TYPE))) {
						task.setInNowTaskNode(true);
						task.setNode(getFlowNode(nowNode.getFlowId(),nowNode.getNodeId()));
					}
				} catch (FlowException e) {
					throw new FlowException("未找到任务流程的环节信息");
				}
			}
		}
		return task;
	}
	
	
	@Override
	public TaskFlowNodeBean queryTaskNode(String taskId,String nodeId) {
		return taskFlowNodeMapper.selectByTaskAndId(taskId, nodeId);
	}

	@Override
	public String getPreTaskId() {
		return getUUID();
	}

	private String getUUID() {
		return flowSqlMapper.queryShortUUID();
	}

	@Override
	public TaskFlowBean startTask(TaskFlowBean task,TaskFlowNodeBean taskNode) throws FlowException {
		if(task==null) {
			throw new FlowException("请提交任务信息");
		}
		if(StringUtils.isEmpty(task.getFlowId())) {
			throw new FlowException("开启任务需要选择任务流程 flowid");
		}
		FlowBean flow = getFlow(task.getFlowId());
		if(flow==null) {
			throw new FlowException("开启任务对应的流程id信息不存在");
		}
		FlowNodeContainerBean node=null;
		if(taskNode ==null || StringUtils.isEmpty(taskNode.getNodeId())) {
			if(StringUtils.isEmpty(flow.getStartId())) {
				List<FlowNodeContainerBean> list = flowQuerySevice.queryNodesByFLow(task.getFlowId());
				if(list==null || list.isEmpty()) {
					throw new FlowException("开启任务对应的流程未设置流程节点");
				}
				if(!"start".equals(list.get(0).getType())) {
					throw new FlowException("开启任务对应的流程  查询初始节点，类型校验不是【开始】类型");
				}
				node= list.get(0);
			}else{
				node = getFlowNode(task.getFlowId(), flow.getStartId());
				if(node==null) {
					throw new FlowException("开启任务对应的流程的起始节点未找到！");
				}
				if(!"start".equals(node.getType())) {
					throw new FlowException("开启任务对应的流程设置的初始节点类型不是【开始】类型");
				}
			}
		}else {
			node = getFlowNode(task.getFlowId(), taskNode.getNodeId());
			if(node==null) {
				throw new FlowException("根据流程id与节点id未查询到节点信息！");
			}
			if(!"start".equals(node.getType())) {
				throw new FlowException("查询到的节点类型不是【开始】类型；节点id:"+taskNode.getNodeId());
			}
		}
		
		task.setId(getPreTaskId());
		task.setFlowName(flow.getName());
		taskFlowMapper.insertSelective(task);
		taskNode=taskNodeConvert.convert(task, taskNode ,flow, node);
		taskNode.putParams(TaskContants.TASK_STATUS, 0);
		taskNode.setCreateCode(task.getCreateCode());
		taskNode.setCreateName(task.getCreateName());
		//执行任务流程节点添加
		baseTaskFlowNodeAction.addNode(taskNode);
		return task;
	}


	/**
	 * 任何时段都允许回滚
	 */
	@Override
	public Boolean callBackTask(String taskId,BaseTableBean user) throws FlowException {
		TaskFlowBean task = queryTask(taskId);
		if(task==null) {
			throw new FlowException("根据任务id："+taskId+" 未找到任务");
		}
		if(task.getStatus()==0) {
			throw new FlowException("任务在开启状态，不支持回滚");
		}
		if(task.getStatus()==3) {
			throw new FlowException("任务在已结束，不支持回滚");
		}
		TaskFlowNodeBean nowNode = taskFlowNodeMapper.selectNowNodeByTask(taskId,0);
		if(nowNode==null) {
			throw new FlowException("根据任务id："+taskId+" 未找到任务的当前的信息");
		}
		FlowNodeContainerBean nowFlowNode = getFlowNode(nowNode.getFlowId(), nowNode.getNodeId());
		if(nowFlowNode!=null) {
			baseTaskFlowNodeAction.excEventByType(nowNode, nowFlowNode.getEvents(), EVENTTYPE.back);
		}
		if(user!=null && user.getCreateCode()!=null) {
			if(!isInTaskFlow(nowNode, nowFlowNode, user.getCreateCode())) {
				throw new FlowException("操作人："+user.getCreateCode()+" 无回滚当前任务的权限！任务id="+taskId);
			}
		}
		
		
		String upNodeId = getUpTaskNodeId(nowNode);
		if(StringUtils.isEmpty(upNodeId)) {
			throw new FlowException("当前任务未找到要回滚至的上级节点");
		}
		//获取应回滚的节点
		TaskFlowNodeBean backNode =getBackode(taskId,upNodeId);
		if(backNode==null) {
			throw new FlowException("根据任务的当前节点确定的上级节点信息丢失");
		}
		if(user!=null) {
			backNode.setCreateCode(user.getCreateCode());
			backNode.setCreateName(user.getCreateName());
		}

		//  流程顺序记录获取
		String links = null;
		String targetNodeId = nowNode.getParam().getString(TaskContants.TASK_NOW);
		if(StringUtils.isEmpty(targetNodeId)){
			links=nowNode.getParam().getString(TaskContants.TASK_LINK);
		}else {
			TaskFlowNodeBean targetNode = queryTaskNode(taskId, targetNodeId);
			if(targetNode!=null) {
				links=targetNode.getParam().getString(TaskContants.TASK_LINK);
			}
		}
		
		JSONObject param = backNode.getParam();
		backNode.setParam(null);
		
		if(!StringUtils.isEmpty(links)) {
			//记录的链路倒序
			List<String> list=Arrays.asList(links.split(","));
			Collections.reverse(list);
			backNode.putParams(TaskContants.TASK_LINK, String.join(",", list));
		}
		backNode.putParams(TaskContants.TASK_STATUS, 2);
		backNode.putParams(TaskContants.TASK_NODE_TYPE, param.getString(TaskContants.TASK_NODE_TYPE));
		String backId = param.getString(TaskContants.TASK_BACK);
		if(StringUtils.isEmpty(backId)) {
			backId= param.getString(TaskContants.TASK_UPNODE);
		}
		backNode.putParams(TaskContants.TASK_BACK, backId);
		backNode.putParams(TaskContants.TASK_NOW, backNode.getId());
		backNode.putParams(TaskContants.TASK_UPNODE, nowNode.getId());
		backNode.setId(null);
		
		TaskFlowNodeBean node = baseTaskFlowNodeAction.addNode(backNode);
		return node!=null;
	}

	/**
	 * 获取要回滚的目标节点
	 * @param taskId
	 * @param upNodeId
	 * @return
	 * @author 王帆
	 * @date 2019年6月13日 上午11:17:25
	 */
	private TaskFlowNodeBean getBackode(String taskId, String upNodeId) {
		TaskFlowNodeBean node = queryTaskNode(taskId,upNodeId);
		String backId = node.getParam().getString(TaskContants.TASK_BACK);
		//当前的回滚节点是否为回滚节点，不是机回滚，不是继续寻找
		if(StringUtils.isEmpty(backId)) {
			return node;
		}else {
			return getBackode(taskId, backId);
		}
	}

	private String getUpTaskNodeId(TaskFlowNodeBean nowNode) {
		String backId = nowNode.getParam().getString(TaskContants.TASK_BACK);
		return StringUtils.isEmpty(backId)? nowNode.getParam().getString(TaskContants.TASK_UPNODE):backId;
	}

	@Override
	public TaskFlowNodeBean addTaskDetail(String taskId,TaskFlowNodeBean taskdetail,  Object params) throws FlowException {
		List<String> errs=new LinkedList<>();
		if(StringUtils.isEmpty(taskId)) {
			errs.add("任务id为空");
		}
		if(taskdetail==null) {
			errs.add("要添加的任务节点信息缺失");
		}
		if(!errs.isEmpty()) {
			throw new FlowException(errs);
		}
		taskdetail.setParam(JSON.parseObject(JSON.toJSONString(params)));
		return baseTaskFlowNodeAction.addNode(getTaskFlowInfo(taskId), taskdetail);
	}

	@Override
	public TaskFlowNodeBean addTaskDetail(TaskFlowNodeBean detail) throws FlowException {
		return baseTaskFlowNodeAction.addNode(getTaskFlowInfo(detail.getTaskId()), detail);
	}

	@Override
	public FlowNodeBean getNowFlowNode(String taskId) throws FlowException {
		TaskFlowNodeBean nowNode = queryTaskNowNode(taskId);
		if(nowNode==null) {
			throw new FlowException("根据任务id："+taskId+" 未找到当前任务的环节");
		}
		return flowQuerySevice.queryNodeById(nowNode.getFlowId(), nowNode.getNodeId());
	}

	@SuppressWarnings("unused")
	private FlowBean getFlowSim(String flowId) throws FlowException {
		FlowBean flow = flowQuerySevice.queryFlowSimById(flowId);
		return flow;
	}
	private FlowBean getFlow(String flowId) throws FlowException {
		return flowQuerySevice.queryById(flowId);
	}

	private FlowNodeContainerBean getFlowNode(String flowId,String nodeId) throws FlowException {
		return flowQuerySevice.queryNodeById(flowId, nodeId);
	}

	@SuppressWarnings("unused")
	private List<FlowNodeContainerBean> getFlowNextNode(String flowId,String nodeId) throws FlowException {
		return flowQuerySevice.queryNextNodeById(flowId, nodeId);
	}

	/**
	 * 根据流程任务id查询对应的流程的信息；（可能存在的异常：1：任务不存在；2：任务对应的流程不存在）
	 * @param taskId
	 * @return
	 * @throws FlowException
	 * @author 王帆
	 * @date 2019年6月1日 下午4:08:04
	 */
	@SuppressWarnings("unused")
	private FlowBean getFlowByTask(String taskId) throws FlowException {
		TaskFlowBean task = queryTask(taskId);
		if(task==null) {
			throw new FlowException("根据任务id："+taskId+" 未找到任务");
		}
		FlowBean flow = getFlow(task.getFlowId());
		if(flow==null) {
			throw new FlowException("根据任务id："+taskId+" 关联的流程id："+task.getFlowId()+" 未找到关联的流程");
		}
		return flow;
	}

	private TaskFlowInfoBean getTaskFlowInfo(String taskId) throws FlowException {
		TaskFlowBean task = queryTask(taskId);
		if(task==null) {
			throw new FlowException("根据任务id："+taskId+" 未找到任务");
		}
		FlowBean flow = getFlow(task.getFlowId());
		if(flow==null) {
			throw new FlowException("根据任务id："+taskId+" 关联的流程id："+task.getFlowId()+" 未找到关联的流程");
		}
		TaskFlowInfoBean info=new TaskFlowInfoBean();
		info.setTask(task);
		info.setFlow(flow);
		//当前任务的节点
		info.setNowNode(queryTaskNowNode(taskId));
		return info;
	}

	@Override
	public TaskFlowNodeBean addTaskDetail(TaskFlowNodeBean detail, TaskFlowCallBack call) throws FlowException {
		return baseTaskFlowNodeAction.addNode(getTaskFlowInfo(detail.getTaskId()), detail,call);
	}


}
