package com.sky.transaction.control.handle;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import com.alibaba.fastjson.JSON;
import com.sky.transaction.bean.ConnectTransationNodeData;
import com.sky.transaction.datasource.factory.ConnectTransactionNodeFactory;

/**
 * redis  主动获取事务组信息handle
 * @author 王帆
 * @date  2019年9月26日 下午4:52:09
 */
public class RedisSubmitPullHandle extends RedisSubmitHandle implements ConnectSubmitPullHandle{

	public RedisSubmitPullHandle(Long timeOut) {
		super(timeOut);
	}

	@Override
	public List<ConnectTransationNodeData> listenConnectTransactionNode() {
//		System.err.println("redis get node");
		ConnectTransationNodeData node = ConnectTransactionNodeFactory.getDefTransactionNodeData();
		StringRedisTemplate template = super.getRedisTemplate();
		if(template!=null) {
			Set<String> keys = template.keys(getLikeKey(node));
			List<ConnectTransationNodeData> list=new LinkedList<ConnectTransationNodeData>();
			ValueOperations<String, String> valTemplate = template.opsForValue();
			for(String key:keys) {
				list.add(JSON.parseObject(valTemplate.get(key),ConnectTransationNodeData.class));
			}
			return list;
		}
		return null;
	}

}
