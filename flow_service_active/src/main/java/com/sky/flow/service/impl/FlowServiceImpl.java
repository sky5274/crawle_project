package com.sky.flow.service.impl;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import com.sky.flow.bean.FlowBean;
import com.sky.flow.bean.FlowGroupBean;
import com.sky.flow.bean.FlowNodeAttrBean;
import com.sky.flow.bean.FlowNodeBean;
import com.sky.flow.bean.FlowNodeContainerBean;
import com.sky.flow.bean.FlowNodeEventBean;
import com.sky.flow.bean.FlowNodeLinkBean;
import com.sky.flow.contant.FlowContant;
import com.sky.flow.dao.FlowBeanMapper;
import com.sky.flow.dao.FlowGroupBeanMapper;
import com.sky.flow.dao.FlowNodeAttrBeanMapper;
import com.sky.flow.dao.FlowNodeBeanMapper;
import com.sky.flow.dao.FlowNodeEventBeanMapper;
import com.sky.flow.dao.FlowNodeLinkBeanMapper;
import com.sky.flow.dao.FlowSqlMapper;
import com.sky.flow.exception.FlowException;
import com.sky.flow.service.FlowActionService;
import com.sky.flow.service.FlowQueryService;
import com.sky.flow.service.FlowSqlService;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Transactional(rollbackFor=Exception.class)
public class FlowServiceImpl implements FlowActionService,FlowQueryService,FlowSqlService{
	@Resource
	private FlowBeanMapper flowMapper;
	@Resource
	private FlowSqlMapper flowSqlMapper;
	@Resource
	private FlowGroupBeanMapper flowGroupMapper;
	@Resource
	private FlowNodeBeanMapper flowNodeMapper;
	@Resource
	private FlowNodeEventBeanMapper flowNodeEventMapper;
	@Resource
	private FlowNodeLinkBeanMapper flowNodeLinkMapper;
	@Resource
	private FlowNodeAttrBeanMapper flowNodeAttrMapper;
	
	private List<String> getFlowIdValidInfo(String flowId){
		List<String> errs=new LinkedList<>();
		if(StringUtils.isEmpty(flowId)) {
			errs.add("流程id:为空");
		}else {
			FlowBean flow = flowMapper.selectByPrimaryKey(flowId);
			if(flow==null) {
				errs.add("根据流程id未找到流程信息，流程id:"+flowId+"无效");
			}
		}
		return errs;
	}
	
	/**
	 * 校验流程节点
	 * @param node
	 * @throws FlowException
	 * @author 王帆
	 * @date 2019年5月18日 上午11:59:19
	 */
	private void validFlowNodeAction(FlowNodeBean node) throws FlowException{
		if(node==null) {
			throw new FlowException("流程节点数据为空");
		}
		List<String> errs=new LinkedList<>();
		if(StringUtils.isEmpty(node.getName())) {
			errs.add("流程节点名称为空");
		}
		if(StringUtils.isEmpty(node.getKey())) {
			errs.add("流程节点key为空");
		}
		errs.addAll(getFlowIdValidInfo(node.getFlowId()));
		if(!FlowContant.isNodeType(node.getType())) {
			errs.add("节点类型："+node.getType()+" 不在系统定义的节点类型中");
		}
		if(!StringUtils.isEmpty(node.getType()) && FlowContant.NODE_TYPE_CONTAINER.equals(node.getType())) {
			//容器id（流程.id）是否存在
			if(StringUtils.isEmpty(node.getId())) {
				errs.add("容器节点操作时必须有对应的流程id");
			}else {
				FlowBean container = flowMapper.selectByPrimaryKey(node.getId());
				if(container==null) {
					errs.add("容器节点对应的流程不存在");
				}
			}
		}
		if(!errs.isEmpty()) {
			throw new FlowException(errs);
		}
	}
	
	/**
	 * 	初始化流程节点数据
	 * @param node
	 * @author 王帆
	 * @date 2019年5月18日 下午4:26:27
	 */
	private void initFlowNode(FlowNodeBean node) {
		if(node !=null) {
			if(StringUtils.isEmpty(node.getType())) {
				node.setType(FlowContant.NODE_TYPE_BASE);
			}
		}
	}
	
	/***
	 * 添加节点的关联内容
	 * @param node
	 * @throws FlowException
	 * @author 王帆
	 * @date 2019年5月27日 上午10:05:22
	 */
	private void addNodeRelativeContent(FlowNodeBean node) throws FlowException {
		addNodeAttr(node);
		addNodeLink(node);
		addNodeEvent(node);
	}
	
	/**
	 * 添加节点的属性
	 * @param node
	 * @throws FlowException
	 * @author 王帆
	 * @date 2019年5月27日 上午10:05:06
	 */
	private void addNodeAttr(FlowNodeBean node) throws FlowException {
		if(node.getAttrs() !=null && !node.getAttrs().isEmpty()) {
			flowNodeAttrMapper.deleteByNode(node.getFlowId(),node.getId());
			List<FlowNodeAttrBean> list = node.getAttrs();
			for(FlowNodeAttrBean attr:list) {
				attr.setFlowId(node.getFlowId());
				attr.setNodeId(node.getId());
			}
			//批量添加节点属性
			addNodeAttr(list);
		}
	}
	
	/**
	 * 添加节点的链接
	 * @param node
	 * @throws FlowException
	 * @author 王帆
	 * @date 2019年5月27日 上午10:04:13
	 */
	private void addNodeLink(FlowNodeBean node) throws FlowException {
		if(node.getLinks() !=null && !node.getLinks().isEmpty()) {
			flowNodeLinkMapper.deleteByNode(node.getFlowId(),node.getId());
			List<FlowNodeLinkBean> list = node.getLinks();
			for(FlowNodeLinkBean link:list) {
				link.setFlowId(node.getFlowId());
				link.setUpNodeId(node.getId());
			}
			addFlowNodeLink(list);
		}
	}
	
	/**
	 * 添加节点的事件
	 * @param node
	 * @throws FlowException
	 * @author 王帆
	 * @date 2019年5月27日 上午10:04:40
	 */
	private void addNodeEvent(FlowNodeBean node) throws FlowException{
		if(node.getEvents() !=null && !node.getEvents().isEmpty()) {
			flowNodeEventMapper.deleteByNode(node.getFlowId(),node.getId());
			List<FlowNodeEventBean> list = node.getEvents();
			for(FlowNodeEventBean event:list) {
				event.setFlowId(node.getFlowId());
				event.setNodeId(node.getId());
			}
			addFlowNodeEvent(list);
		}
	}
	
	
	
	@Override
	public FlowNodeBean addFlowNode(FlowNodeBean node) throws FlowException {
		initFlowNode(node);
		validFlowNodeAction(node);
		if(StringUtils.isEmpty(node.getId())) {
			node.setId(flowSqlMapper.queryShortUUID());
		}
		flowNodeMapper.insertSelective(node);
		//添加节点关联内容
		addNodeRelativeContent(node);
		return node;
	}

	@Override
	public int updateFlowNode(FlowNodeContainerBean node) throws FlowException {
		initFlowNode(node);
		validFlowNodeAction(node);
		int size=0;
		if(StringUtils.isEmpty(node.getContainerId())) {
			 size= flowNodeMapper.updateByPrimaryKeySelective(node);
		}
		//添加节点关联内容
		addNodeRelativeContent(node);
		return size;
	}

	@Override
	public int disableFlowNode(FlowNodeBean node) throws FlowException {
		node.setStatus(-1);
		return flowNodeMapper.updateByPrimaryKeySelective(node);
	}
	
	/*----------------------节点链接----------------------------------------*/
	private void validNodeLink(FlowNodeLinkBean node) throws FlowException{
		if(node==null) {
			throw new FlowException("节点链接数据不能为空");
		}
		validNodeLinks(Arrays.asList(node));
	}
	private void validNodeLinks(List<FlowNodeLinkBean> nodes) throws FlowException{
		if(nodes==null || nodes.isEmpty()) {
			throw new FlowException("节点链接数据不能为空");
		}
		List<String> flowIds=new LinkedList<>();
		List<String> nodeIds=new LinkedList<>();
		List<String> errors=new LinkedList<>();
		for(int i=0;i<nodes.size();i++) {
			if(nodes.get(i) !=null) {
				validNodeLink(nodes.get(i),i, nodes.size()==0,flowIds,nodeIds,errors);
			}
		}
		if(!errors.isEmpty()) {
			throw new FlowException(errors);
		}
	}
	
	private void validNodeLink(FlowNodeLinkBean node, int index, boolean flag, List<String> flowIds, List<String> nodeIds, List<String> errors) {
		//校验FlowId 是否有效
		String tpis=flag?"":index+"行";
		errors.addAll(getFlowIdValidInfo(node.getFlowId()));
		List<String> upNodeErrs = getLinkNodeValid(node.getUpNodeId(),tpis+"起始");
		if(upNodeErrs.isEmpty()) {
			nodeIds.add(node.getUpNodeId());
		}
		List<String> downNodeErrs = getLinkNodeValid(node.getDownNodeId(),tpis+"结束");
		if(downNodeErrs.isEmpty()) {
			nodeIds.add(node.getDownNodeId());
			if(node.getDownNodeId().equals(node.getUpNodeId())) {
				errors.add(tpis+"起始节点与结束节点的id一致，形成循环，与系统设计不符； nodeId="+node.getDownNodeId());
			}
		}
		errors.addAll(upNodeErrs);
		errors.addAll(downNodeErrs);
	}
	
	private List<String> getLinkNodeValid(String nodeId,String tips) {
		List<String> errs=new LinkedList<>();
		if(StringUtils.isEmpty(nodeId)) {
			errs.add((tips==null?"":tips)+"节点id为空");
		}else {
			FlowNodeBean node = flowNodeMapper.selectByPrimaryKey(nodeId);
			if(node==null) {
				errs.add("根据 "+(tips==null?"":tips)+"节点id未找到节点信息，节点id："+nodeId+"  无效");
			}
		}
		return errs;
	}

	@Override
	public FlowNodeLinkBean addFlowNodeLink(FlowNodeLinkBean link) throws FlowException {
		validNodeLink(link);
		int size=flowNodeLinkMapper.insert(link);
		return size>0?link:null;
	}

	@Override
	public int addFlowNodeLink(List<FlowNodeLinkBean> links) throws FlowException {
		validNodeLinks(links);
		return flowNodeLinkMapper.insertBatch(links);
	}
	@Override
	public int updateFlowNodeLink(FlowNodeLinkBean link) throws FlowException {
		validNodeLink(link);
		return flowNodeLinkMapper.updateByPrimaryKeySelective(link);
	}

	@Override
	public int deleteFlowNodeLink(List<Integer> ids,String flowId) throws FlowException {
		if(ids==null || ids.isEmpty()) {
			throw new FlowException("删除节点关联数据，请提交关联id集合");
		}
		return flowNodeLinkMapper.deleteByIds(ids,flowId);
	}

	@Override
	public int deleteFlowNodeLinkByKey(List<String> keys,String flowId) throws FlowException {
		if(keys==null || keys.isEmpty()) {
			throw new FlowException("删除节点关联数据，请提交关联key集合");
		}
		return flowNodeLinkMapper.deleteByKeys(keys,flowId);
	}

	/*----------------------流程分组---------------------------------*/
	@Override
	public FlowGroupBean addGroup(FlowGroupBean group) throws FlowException {
		flowGroupMapper.insertSelective(group);
		return group;
	}

	@Override
	public int updateGroup(FlowGroupBean group) throws FlowException {
		
		return flowGroupMapper.updateSelective(group);
	}

	/*-------------------------流程控制-----------------------------------------*/
	private void validFlowBean(FlowBean flow) throws FlowException{
		if(StringUtils.isEmpty(flow.getName())) {
			throw new FlowException("流程名称为空");
		}
		if(StringUtils.isEmpty(flow.getGroupId())) {
			throw new FlowException("流程分组为空");
		}
	}
	@Override
	public FlowBean addFlow(FlowBean flow) throws FlowException {
		validFlowBean(flow);
		flowMapper.insertSelective(flow);
		return flow;
	}

	@Override
	public int updateFlow(FlowBean flow) throws FlowException {
		validFlowBean(flow);
		return flowMapper.updateByPrimaryKeySelective(flow);
	}

	@Override
	public int displayFlow(FlowBean flow) throws FlowException {
		flow.setStatus(-1);
		return flowMapper.updateByPrimaryKeySelective(flow);
	}

	@Override
	public List<FlowGroupBean> queryGroupsByUser(String userCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FlowGroupBean> queryGroupsByCode(String... code) {
		return flowGroupMapper.selectByCodes(code==null?null:Arrays.asList(code));
	}

	@Override
	public FlowGroupBean queryGroup(String groupId) {
		return flowGroupMapper.selectById(groupId);
	}

	@Override
	public List<FlowBean> queryByGroup(String groupId) {
		return flowMapper.selectByGroupId(groupId);
	}

	@Override
	public FlowBean queryById(String flowId) {
		FlowBean flow = flowMapper.selectByPrimaryKey(flowId);
		if(flow!=null) {
			flow.setNodes(queryNodesByFLow(flowId));
		}
		return flow;
	}
	@Override
	public FlowBean queryFlowSimById(String flowId) {
		FlowBean flow = flowMapper.selectByPrimaryKey(flowId);
		return flow;
	}

	@Override
	public List<FlowNodeContainerBean> queryNodesByFLow(String flowId) {
		List<FlowNodeContainerBean> nodes = flowNodeMapper.selectNodeByFlow(flowId);
		if(nodes!=null && !nodes.isEmpty()) {
			for(FlowNodeContainerBean node:nodes) {
				if(FlowContant.NODE_TYPE_CONTAINER.equals(node.getType()) && node.getContainer()!=null && !node.getContainer().isEmpty()) {
					for(FlowNodeContainerBean n:node.getContainer()) {
						if(FlowContant.NODE_TYPE_CONTAINER.contains(n.getType().substring(FlowContant.NODE_TYPE_CONTAINER.length()))) {
							n.setContainer(getContainerNode(flowId,n.getId()));
						}
					}
				}
			}
		}
		return nodes;
	}

	private List<FlowNodeContainerBean> getContainerNode(String flowId, String id) {
		List<FlowNodeContainerBean> nodes = flowNodeMapper.selectBaseNodeByContianerFlow(flowId, id);
		for(FlowNodeContainerBean node:nodes) {
			if(FlowContant.NODE_TYPE_CONTAINER.contains(node.getType().substring(FlowContant.NODE_TYPE_CONTAINER.length()))) {
				List<FlowNodeContainerBean> containernodes = getContainerNode(node.getContainerId(), node.getId());
				for(FlowNodeContainerBean n:containernodes) {
					n.setFlowId(flowId);
				}
				node.setContainer(containernodes);;
			}
		}
		return nodes;
	}

	@Override
	public FlowNodeContainerBean queryNodeById(String flowId,String nodeId) {
		return flowNodeMapper.selectNode(flowId, nodeId);
	}

	@Override
	public List<FlowNodeContainerBean> queryNextNodeById(String flowId,String nodeId) {
		return flowNodeMapper.selectNextNode(flowId, nodeId);
	}

	/*---------------------节点属性操作-------------------------------------*/
	@Override
	public FlowNodeAttrBean addNodeAttr(FlowNodeAttrBean attr) throws FlowException{
		validNodeAttr(attr);
		int size = flowNodeAttrMapper.insertSelective(attr);
		return size>0?attr:null;
	}
	
	@Override
	public int updateNodeAttr(FlowNodeAttrBean attr) throws FlowException{
		validNodeAttr(attr);
		return flowNodeAttrMapper.updateByPrimaryKeySelective(attr);
	}
	
	public int addNodeAttr(List<FlowNodeAttrBean> attrs) throws FlowException{
		validNodeAttr(attrs);
		return flowNodeAttrMapper.insertBatch(attrs);
	}

	private void validNodeAttr(FlowNodeAttrBean attr) throws FlowException{
		List<String> errs=getNodeAttrValidInfo(attr);
		if(!errs.isEmpty()) {
			throw new FlowException(errs);
		}
	}
	
	private List<String> getNodeAttrValidInfo(FlowNodeAttrBean attr){
		List<String> errs=new LinkedList<>();
		if(StringUtils.isEmpty(attr.getKey())) errs.add("链接属性key为空");
		if(StringUtils.isEmpty(attr.getName())) errs.add("链接名称为空");
		errs.addAll(getFlowIdValidInfo(attr.getFlowId()));
		errs.addAll(getLinkNodeValid(attr.getNodeId(), ""));
		return errs;
	}
	
	private void validNodeAttr(List<FlowNodeAttrBean> attrs) throws FlowException{
		List<String> errs=new LinkedList<>();
		for(FlowNodeAttrBean attr:attrs) {
			errs.addAll(getNodeAttrValidInfo(attr));
		}
		if(!errs.isEmpty()) {
			throw new FlowException(errs);
		}
	}

	@Override
	public int deleteNodeAttrs(List<Integer> ids) throws FlowException {
		if(ids==null || ids.isEmpty()) {
			throw new FlowException("删除节点属性数据，请提交关联id集合");
		}
		return flowNodeAttrMapper.deleteByIds(ids);
	}

	/*---------------------节点事件操作-------------------------------------*/
	public int addFlowNodeEvent(List<FlowNodeEventBean> events) throws FlowException {
		validEvent(events,"新增校验");
		return flowNodeEventMapper.insertBatch(events);
	}
	@Override
	public FlowNodeEventBean addFlowNodeEvent(FlowNodeEventBean event) throws FlowException {
		validEvent(event,"新增校验");
		return flowNodeEventMapper.insertSelective(event)>0?event:null;
	}

	@Override
	public int updateFlowNodeEvent(FlowNodeEventBean event) throws FlowException {
		validEvent(event,"更新校验");
		return flowNodeEventMapper.updateByPrimaryKeySelective(event);
	}

	/**
	 * 校验节点事件
	 * @param events
	 * @throws FlowException
	 * @author 王帆
	 * @date 2019年6月28日 下午2:42:18
	 */
	private void validEvent(List<FlowNodeEventBean> events, String tips) throws FlowException {
		List<String> errs=new LinkedList<>();
		int i=1;
		for(FlowNodeEventBean e:events) {
			try {
				validEvent(e);
			} catch (FlowException e1) {
				List<String> errmsgs = e1.getErrorMessages();
				for(String msg:errmsgs) {
					msg=tips+":第"+i+"行"+msg;
				}
				errs.addAll(errmsgs);
			}
			i++;
		}
		if(!errs.isEmpty()) {
			throw new FlowException(errs);
		}
	}
	private void validEvent(FlowNodeEventBean event, String tips) throws FlowException{
		tips=tips==null?"":tips;
		List<String> errs=new LinkedList<>();
		if(event==null) {
			throw new FlowException(tips+"节点事件为空");
		}
		if(StringUtils.isEmpty(event.getFlowId())) {
			errs.add(tips+"所属流程id为空");
		}
		if(StringUtils.isEmpty(event.getNodeId())) {
			errs.add(tips+"所属流程节点id为空");
		}
		if(StringUtils.isEmpty(event.getEventType())) {
			errs.add(tips+"事件类型为空");
		}
		if(StringUtils.isEmpty(event.getType())) {
			errs.add(tips+"事件执行类型为空");
		}
		if(StringUtils.isEmpty(event.getContent())) {
			errs.add(tips+"事件执行内容为空");
		}
		
		if(!errs.isEmpty()) {
			throw new FlowException(errs);
			
		}
	}
	private void validEvent(FlowNodeEventBean event) throws FlowException{
		validEvent(event, null);
	}

	@Override
	public int deleteFlowNodeEvents(List<Integer> ids) throws FlowException {
		if(ids==null || ids.isEmpty()) {
			throw new FlowException("删除节点事件数据，请提交关联id集合");
		}
		return flowNodeEventMapper.deleteByIds(ids);
	}

	@Override
	public List<FlowNodeBean> querySimpleNodesByFLow(String flowId) {
		return flowNodeMapper.selectSimpleNodes(flowId);
	}
	@Override
	public List<FlowNodeContainerBean> querySimpleNodesWithInfoByFLow(String flowId) {
		return flowNodeMapper.selectSimpleWithInfoNodes(flowId);
	}

	@Override
	public List<Map<String, String>> queryBySql(String sql) {
		return flowSqlMapper.queryBySql(sql);
	}

	@Override
	public int excuteBySql(String sql) {
		return flowSqlMapper.excuteBySql(sql);
	}


}
