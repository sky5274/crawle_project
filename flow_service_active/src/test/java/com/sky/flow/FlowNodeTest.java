package com.sky.flow;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.sky.flow.bean.FlowNodeBean;
import com.sky.flow.exception.FlowException;
import com.sky.flow.service.FlowActionService;


public class FlowNodeTest extends SpringTestBase{
	@Autowired
	FlowActionService flowActionService;
	
	@Test
	public void addNodeTest() throws FlowException {
		FlowNodeBean node=new FlowNodeBean();
		node.setFlowId("dsfds");
		flowActionService.addFlowNode(node);
		
	}
}
