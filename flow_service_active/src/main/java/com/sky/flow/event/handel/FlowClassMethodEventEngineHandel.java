package com.sky.flow.event.handel;


import org.springframework.stereotype.Component;
import com.sky.flow.bean.FlowNodeEventBean;
import com.sky.flow.bean.TaskFlowBean;
import com.sky.flow.bean.TaskFlowNodeBean;
import com.sky.flow.exception.FlowException;

/**
 * 流程引擎：java method 类型的事件引擎
 * @author 王帆
 * @date  2019年6月7日 下午5:31:15
 */
@Component
public class FlowClassMethodEventEngineHandel extends FlowClassEventEngineHandel{
	@Override
	public String type() {
		return "method";
	}

	@Override
	public void invoke(FlowNodeEventBean event, TaskFlowNodeBean node, TaskFlowBean task) throws FlowException{
		initTaskEvent(event,node,task);
		String classMethod =event.getContent();
		
		//本地方法寻找，没有则在远程寻找
		int lastIndex = classMethod.lastIndexOf(".");
		clazzName=classMethod.substring(0, lastIndex);
		methodName=classMethod.substring(lastIndex+1);
		super.invoke(event, node, task);
	}
	

}
