package com.sky.flow.call;

import com.sky.flow.bean.TaskFlowNodeInfoBean;
import com.sky.rpc.call.RpcCallBack;

/**
 * 流程执行过程回调控制
 * @author 王帆
 * @date  2019年6月8日 上午9:36:29
 */
public interface TaskFlowCallBack extends RpcCallBack{
	
	public void before(TaskFlowNodeInfoBean node);
	public void arround(TaskFlowNodeInfoBean node);
	public void after(TaskFlowNodeInfoBean node);
}
