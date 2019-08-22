package com.sky.flow.convert;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.sky.flow.bean.FlowBean;
import com.sky.flow.bean.FlowNodeBean;
import com.sky.flow.bean.TaskFlowBean;
import com.sky.flow.bean.TaskFlowNodeBean;
import com.sky.flow.pub.TaskContants;

@Component
public class TaskNodeConvert {
	
	public TaskFlowNodeBean convert(TaskFlowBean task,FlowBean flow,FlowNodeBean node) {
		return convert(task, null, flow, node);
	}
	
	public TaskFlowNodeBean convert(TaskFlowBean task,TaskFlowNodeBean tasknode,FlowBean flow,FlowNodeBean node) {
		if(tasknode==null) {
			tasknode=new TaskFlowNodeBean();
		}
		tasknode.setTaskId(task.getId());
		tasknode.setFlowId(flow.getId());
		tasknode.setFlowName(flow.getName());
		tasknode.setNodeId(node.getId());
		tasknode.setNodeKey(node.getKey());
		tasknode.setNodeName(node.getName());
		tasknode.putParams(TaskContants.TASK_NODE_TYPE, node.getType());
		if(StringUtils.isEmpty(tasknode.getName())) {
			tasknode.setName(node.getName());
		}
		if(StringUtils.isEmpty(tasknode.getContent()) &&  task.getParams()!=null) {
			String content = task.getParams().getString(TaskContants.Content);
			if(StringUtils.isEmpty(content)) {
				content=task.getMessage();
			}
			tasknode.setContent(content);
		}
		return tasknode;
	}
}
