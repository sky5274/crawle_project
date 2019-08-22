package com.sky.flow;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import com.alibaba.fastjson.JSON;
import com.sky.flow.bean.BaseTableBean;
import com.sky.flow.bean.TaskFlowBean;
import com.sky.flow.bean.TaskFlowNodeBean;
import com.sky.flow.dao.TaskFlowNodeBeanMapper;
import com.sky.flow.exception.FlowException;
import com.sky.flow.pub.TaskContants;
import com.sky.flow.service.FlowQueryService;
import com.sky.flow.service.TaskFlowActionService;

/**
 * 流程任务测试方法
 * @author 王帆
 * @date  2019年6月8日 下午2:12:08
 */
@EnableAutoConfiguration
public class TaskFlowTest extends SpringTestBase{

	@Autowired
	private TaskFlowActionService taskFlowActionService;
	@Autowired
	private FlowQueryService flowQueryService;
	@Autowired
	private TaskFlowNodeBeanMapper taskFlowNodeMapper;
	
	@Test
	public void queryTask() {
		TaskFlowNodeBean nowNode = taskFlowNodeMapper.selectNowNodeByTask("sdfs",0);
		System.err.println(nowNode);
	}
	
	@Test
	public void startTask() throws FlowException {
		TaskFlowBean task=new TaskFlowBean();
		task.setFlowId("98206404485054464");
		task.setDesc("任务测试");
		task.setName("测试任务1");
		task.setMessage("llaces");
		Map<String, Object> map=new HashMap<>();
		map.put("flag", true);
		map.put("time", new Date());
		map.put("descname", "my country is china");
		task.setParams(JSON.parseObject(JSON.toJSONString(map)));
		taskFlowActionService.startTask(task, null);
		
	}
	
	@Test
	public void callBackTask() throws FlowException {
		BaseTableBean user=new BaseTableBean();
		taskFlowActionService.callBackTask("98226735652274193", user);
	}
	
	@Test
	public void setNextNodeByParam() throws FlowException {
		TaskFlowNodeBean taskdetail=new TaskFlowNodeBean();
		taskdetail.setName("基本环节-测试");
		taskdetail.setContent("jiben环节测试过程");
		taskdetail.setDesc("环节测试");
		Map<String, Object> params=new HashMap<>();
		params.put("select-1", true);
		params.put("flag", "Y");
		params.put(TaskContants.Content, "环节测试");
		taskFlowActionService.addTaskDetail("98226735652274193", taskdetail, params);
	}
}
