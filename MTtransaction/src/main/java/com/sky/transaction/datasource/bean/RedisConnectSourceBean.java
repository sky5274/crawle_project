package com.sky.transaction.datasource.bean;

import org.springframework.data.redis.connection.RedisConnection;

/**
 * redis client connect  source
 * @author 王帆
 * @date  2019年9月20日 上午10:41:25
 */
public class RedisConnectSourceBean extends ConnectSourceBean<RedisConnection>{

	public RedisConnectSourceBean(RedisConnection conn) {
		super(conn);
	}

	public void commit() {
		
	}

	public void rollBack() {
		
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

}
