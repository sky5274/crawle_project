package com.sky.flow.event.factory;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.sky.flow.bean.FlowNodeEventBean;
import com.sky.flow.bean.TaskFlowBean;
import com.sky.flow.bean.TaskFlowNodeBean;
import com.sky.flow.event.FlowEventEngineHandel;
import com.sky.flow.exception.FlowException;

/**
 * 流程事件引擎工厂
 * @author 王帆
 * @date  2019年6月7日 下午5:24:24
 */
@Component
public class FlowEventEngineFactory implements FlowEventEngineHandel{
	@Autowired
	List<FlowEventEngineHandel> handels;
	
	@Override
	public void invoke(FlowNodeEventBean event, TaskFlowNodeBean node, TaskFlowBean task) throws FlowException {
		/*
		 *1:handel  类型有，js，class,method;  分本地与远程服务等等 
		 */
		if(handels!=null && !handels.isEmpty()) {
			for(FlowEventEngineHandel handel:handels) {
				if(!StringUtils.isEmpty(handel.type()) && !StringUtils.isEmpty(event.getType()) && handel.type().toUpperCase().equals(event.getType().toUpperCase().trim())) {
					handel.invoke(event, node, task);
				}
			}
		}
	}

	@Override
	public String type() {
		return null;
	}
}
