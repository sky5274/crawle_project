package com.sky.transaction.control.handle;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.sky.transaction.bean.ConnectTransationNodeData;
import com.sky.transaction.util.SpringMTUtil;

/**
 * redis  机制共享事务组消息
 * @author 王帆
 * @date  2019年9月26日 下午4:54:31
 */
public class RedisSubmitHandle implements ConnectSubmitHandle{
	protected Long timeout;
	private StringRedisTemplate redisTemplate;
	/**事务节点redis key format*/
	private String transactionKeyFormat="%s%s-%s:%s";
	private String getKey(ConnectTransationNodeData data) {
		return String.format(transactionKeyFormat, data.getProject(),data.getVersion(),data.getGroupId(),data.getNodeId());
	}
	/**
	 * 事务节点模糊查询key
	 * */
	public String getLikeKey(ConnectTransationNodeData data) {
		return String.format(transactionKeyFormat, getDefString(data.getProject()),getDefString(data.getVersion()),getDefString(data.getGroupId()),getDefString(data.getNodeId()));
	}
	private String getDefString(String key) {
		if(StringUtils.isEmpty(key)) {
			return "*";
		}
		return key;
	}

	public RedisSubmitHandle(Long timeOut) {
		this.timeout=timeOut;
	}

	@Override
	public ConnectTransationNodeData submitTransactionNodeEvent(ConnectTransationNodeData req) {
		redisTemplate.opsForValue().set(getKey(req), JSON.toJSONString(req), req.getTimeOut()+timeout, TimeUnit.MILLISECONDS);
		return req;
	}

	public StringRedisTemplate getRedisTemplate() {
		if(redisTemplate==null) {
			redisTemplate=SpringMTUtil.getBean(StringRedisTemplate.class);
		}
		return redisTemplate;
	}


}
