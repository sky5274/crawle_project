package com.sky.demo;


import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.fastjson.JSON;
import com.sky.flow.annotation.RpcEvent;
import com.sky.flow.bean.TaskFlowBean;
import com.sky.flow.bean.TaskFlowNodeBean;
import com.sky.flow.event.FlowEventExcuteHandel;
import com.sky.flow.exception.FlowException;
import com.sky.flow.service.TaskFlowQueryService;

/**
 * rpc  flow event test handel
 * @author 王帆
 * @date  2019年7月11日 下午4:28:07
 */
@RpcEvent
public class RpcFlowEventTestHandel implements FlowEventExcuteHandel{
	@Autowired
	private TaskFlowQueryService taskFlowQueryService;
	
	public void invoke(String taskId,String nodeId,Object param) {
		System.err.println("task id:"+taskId);
		System.err.println("task node id:"+nodeId);
		System.err.println("task node param:"+JSON.toJSONString(param));
		TaskFlowBean task = taskFlowQueryService.queryTask(taskId, null);
		System.err.println(JSON.toJSONString(task));
	}

	@Override
	public void excute(TaskFlowNodeBean node) throws FlowException {
		System.err.println(JSON.toJSONString(node));
	}
}
