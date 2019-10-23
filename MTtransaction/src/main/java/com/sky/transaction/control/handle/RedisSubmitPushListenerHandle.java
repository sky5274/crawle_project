package com.sky.transaction.control.handle;

import org.springframework.data.redis.core.StringRedisTemplate;
import com.alibaba.fastjson.JSON;
import com.sky.transaction.bean.ConnectTransationNodeData;
import com.sky.transaction.util.SpringMTUtil;

/**
 * redis  使用订阅发布机制监听事务事件（事务提交/事务回滚）
 * @author 王帆
 * @date  2019年9月26日 下午4:52:52
 */
public class RedisSubmitPushListenerHandle extends RedisSubmitHandle implements ConnectSubmitPushListenerHandle{
	private String topic;
	
	public RedisSubmitPushListenerHandle(Long timeOut) {
		super(timeOut);
		topic=SpringMTUtil.getEvnProperty("com.transacton.topic}", "topic");
	}

	@Override
	public void excuteNode(ConnectTransationNodeData node) {
		ConnectSubmitPullHandleDecorator.excuteTransaction(node);
	}
	
	@Override
	public ConnectTransationNodeData submitTransactionNodeEvent(ConnectTransationNodeData req) {
		//执行事务发布机制
		submitNode(req);
		return super.submitTransactionNodeEvent(req);
	}
	
	/**
	 * 发布消息
	 * @param node
	 * @author 王帆
	 * @date 2019年9月26日 下午4:46:39
	 */
	public void submitNode(ConnectTransationNodeData node) {
		StringRedisTemplate template = getRedisTemplate();
		if(template!=null) {
			//发布消息
			template.convertAndSend(topic,JSON.toJSONString(node));
		}
	}
	
	/**
	 * 定于消息执行方法
	 * @param node
	 * @author 王帆
	 * @date 2019年9月26日 下午4:51:17
	 */
	public void excuteNode(String node) {
		if(node.startsWith("\"")) {
			node=node.substring(1, node.length()-1);
		}
		node=node.replace("\\", "");
		excuteNode(JSON.parseObject(node, ConnectTransationNodeData.class));
	}
}
