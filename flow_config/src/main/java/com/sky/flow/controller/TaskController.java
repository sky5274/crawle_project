package com.sky.flow.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sky.flow.bean.TaskFlowBean;
import com.sky.flow.bean.TaskFlowDetailBean;
import com.sky.flow.bean.TaskFlowInfoBean;
import com.sky.flow.bean.TaskFlowNodeBean;
import com.sky.flow.exception.FlowException;
import com.sky.flow.pub.BaseController;
import com.sky.flow.pub.Page;
import com.sky.flow.pub.PageRequest;
import com.sky.flow.service.TaskFlowActionService;
import com.sky.flow.service.TaskFlowQueryService;
import com.sky.pub.Result;
import com.sky.pub.ResultCode;
import com.sky.pub.ResultUtil;

@RestController
@RequestMapping("task")
public class TaskController extends BaseController{
	@Autowired
	private TaskFlowQueryService taskFlowQueryService;
	@Autowired
	private TaskFlowActionService taskFlowActionService;
	
	@PostMapping("/query/page")
	public Result<Page<TaskFlowBean>> queryPage(@RequestBody PageRequest<TaskFlowBean> page) {
		return ResultUtil.getOk(taskFlowQueryService.queryTaskByPage(page));
	}
	
	@RequestMapping("/query/info")
	public Result<TaskFlowDetailBean> getTaskInfo(String taskId) throws FlowException {
		return ResultUtil.getOk( taskFlowQueryService.queryTaskInfo(taskId,""));
	}
	
	@RequestMapping("/query/node/detail")
	public Result<List<TaskFlowNodeBean>> getTaskNodes(String taskId) {
		return ResultUtil.getOk( taskFlowQueryService.queryTaskDetails(taskId));
	}
	
	@PostMapping("start")
	public Result<TaskFlowBean> startTask(@RequestBody TaskFlowInfoBean task) throws FlowException {
		return ResultUtil.getOk(taskFlowActionService.startTask(task.getTask(), task.getNowNode()));
	}
	
	@PostMapping("callBack")
	public Result<Boolean> callBaskTask(@RequestBody TaskFlowNodeBean node) throws FlowException {
		if(node==null) {
			return ResultUtil.getFailed(ResultCode.VALID, "任务回滚未提交参数，无法回滚任务");
		}
		return ResultUtil.getOk(taskFlowActionService.callBackTask(node.getTaskId(), node));
	}
	
	@PostMapping("addNode")
	public Result<TaskFlowNodeBean> setTask(@RequestBody TaskFlowInfoBean task) throws FlowException {
		if(task==null) {
			return ResultUtil.getFailed(ResultCode.VALID, "任务提交无参数参数，无法更新任务");
		}
		if(task.getNowNode()==null) {
			return ResultUtil.getFailed(ResultCode.VALID, "任务细节未提交，无法更新任务");
		}
		if(task.getTask()==null) {
			return ResultUtil.getOk(taskFlowActionService.addTaskDetail(task.getNowNode()));
		}else if(task.getTask().getParams()!=null){
			return ResultUtil.getOk(taskFlowActionService.addTaskDetail(task.getTask().getId(), task.getNowNode(), task.getTask().getParams()));
		}else {
			return ResultUtil.getFailed(ResultCode.VALID, "任务细节参数未提交，无法更新任务");
		}
	}
}