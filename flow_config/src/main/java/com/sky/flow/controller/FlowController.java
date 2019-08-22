package com.sky.flow.controller;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sky.flow.bean.FlowBean;
import com.sky.flow.bean.FlowGroupBean;
import com.sky.flow.bean.FlowNodeAttrBean;
import com.sky.flow.bean.FlowNodeBean;
import com.sky.flow.bean.FlowNodeContainerBean;
import com.sky.flow.bean.FlowNodeEventBean;
import com.sky.flow.bean.FlowNodeLinkBean;
import com.sky.flow.exception.FlowException;
import com.sky.flow.pub.BaseController;
import com.sky.flow.service.FlowActionService;
import com.sky.flow.service.FlowGroupActionService;
import com.sky.flow.service.FlowNodeActionService;
import com.sky.flow.service.FlowQueryService;
import com.sky.pub.Result;
import com.sky.pub.ResultUtil;

@RestController
@RequestMapping("/flow")
public class FlowController extends BaseController{
	@Resource
	private FlowQueryService flowQueryService;
	@Resource
	private FlowGroupActionService flowGroupActionService;
	@Resource
	private FlowNodeActionService flowNodeActionService;
	@Resource
	private FlowActionService flowActionService;
	
	@RequestMapping("query/bygroup")
	public Result<List<FlowBean>> queryFlowByGroup(String groupId){
		return ResultUtil.getOk(flowQueryService.queryByGroup(groupId));
	}
	
	@RequestMapping("query/id")
	public Result<FlowBean> queryFlow(String flowId){
		return ResultUtil.getOk(flowQueryService.queryById(flowId));
	}
	
	@RequestMapping("group")
	public Result<List<FlowGroupBean>> queryGroup(String codes){
		return ResultUtil.getOk(flowQueryService.queryGroupsByCode(StringUtils.isEmpty(codes)?null:codes.split(",")));
	}
	
	@RequestMapping("group/add")
	public Result<FlowGroupBean> addGroup(FlowGroupBean group) throws FlowException{
		return ResultUtil.getOk(flowGroupActionService.addGroup(group));
	}
	
	@RequestMapping("group/update")
	public Result<Integer> updateGroup(FlowGroupBean group) throws FlowException{
		return ResultUtil.getOk(flowGroupActionService.updateGroup(group));
	}
	
	@RequestMapping("/info/add")
	public Result<FlowBean> addFlow(FlowBean bean) throws FlowException{
		return ResultUtil.getOk(flowActionService.addFlow(bean));
	}
	
	@RequestMapping("info/update")
	public Result<Integer> updateFlow(FlowBean bean) throws FlowException{
		return ResultUtil.getOk(flowActionService.updateFlow(bean));
	}
	
	
	
	@RequestMapping("node/query")
	public Result<List<FlowNodeContainerBean>> queryNodes(String flowId)  {
		return ResultUtil.getOk(flowQueryService.queryNodesByFLow(flowId));
	}
	
	@RequestMapping("node/query/simple")
	public Result<List<FlowNodeBean>> querySimpleNodes(String flowId)  {
		return ResultUtil.getOk(flowQueryService.querySimpleNodesByFLow(flowId));
	}
	
	@RequestMapping("node/query/id")
	public Result<FlowNodeContainerBean> queryNodes(String flowId,String nodeId)  {
		return ResultUtil.getOk(flowQueryService.queryNodeById(flowId, nodeId));
	}
	
	@RequestMapping("node/query/next")
	public Result<List<FlowNodeContainerBean>> queryNextNodes(String flowId,String nodeId)  {
		return ResultUtil.getOk(flowQueryService.queryNextNodeById(flowId, nodeId));
	}
	
	@PostMapping("node/add")
	public Result<FlowNodeBean> addNode(@RequestBody FlowNodeBean node) throws FlowException {
		return ResultUtil.getOk(flowNodeActionService.addFlowNode(node));
	}
	
	@PostMapping("node/update")
	public Result<Integer> updateNode(@RequestBody FlowNodeContainerBean node) throws FlowException {
		return ResultUtil.getOk(flowNodeActionService.updateFlowNode(node));
	}
	
	@RequestMapping("node/disable")
	public Result<Integer> disableNode(FlowNodeBean node) throws FlowException {
		return ResultUtil.getOk(flowNodeActionService.disableFlowNode(node));
	}
	
	
	@PostMapping("node/attr/add")
	public Result<FlowNodeAttrBean> addNodeAttr(@RequestBody FlowNodeAttrBean attr) throws FlowException {
		return ResultUtil.getOk(flowActionService.addNodeAttr(attr));
	}
	
	@PostMapping("node/attr/update")
	public Result<Integer> updateNodeAttr(@RequestBody FlowNodeAttrBean attr) throws FlowException {
		return ResultUtil.getOk(flowActionService.updateNodeAttr(attr));
	}
	
	@RequestMapping("node/attr/del")
	public Result<Integer> delNodeAttr(Integer id) throws FlowException {
		return ResultUtil.getOk(flowActionService.deleteNodeAttrs(Arrays.asList(id)));
	}
	
	
	@PostMapping("node/link/add")
	public Result<FlowNodeLinkBean> addNodeLink(@RequestBody FlowNodeLinkBean link) throws FlowException {
		return ResultUtil.getOk(flowActionService.addFlowNodeLink(link));
	}
	
	@PostMapping("node/link/update")
	public Result<Integer> updateNodeLink(@RequestBody FlowNodeLinkBean link) throws FlowException {
		return ResultUtil.getOk(flowActionService.updateFlowNodeLink(link));
	}
	
	@RequestMapping("node/link/del")
	public Result<Integer> delNodeLink(Integer id,String flowId) throws FlowException {
		return ResultUtil.getOk(flowActionService.deleteFlowNodeLink(Arrays.asList(id), flowId));
	}
	
	@PostMapping("node/event/add")
	public Result<FlowNodeEventBean> addNodeEvent(@RequestBody FlowNodeEventBean event) throws FlowException {
		return ResultUtil.getOk(flowActionService.addFlowNodeEvent(event));
	}
	
	@PostMapping("node/event/update")
	public Result<Integer> updateNodeEvent(@RequestBody FlowNodeEventBean event) throws FlowException {
		return ResultUtil.getOk(flowActionService.updateFlowNodeEvent(event));
	}
	
	@RequestMapping("node/event/del")
	public Result<Integer> delNodeEvent(Integer id) throws FlowException {
		return ResultUtil.getOk(flowActionService.deleteFlowNodeEvents(Arrays.asList(id)));
	}
}
