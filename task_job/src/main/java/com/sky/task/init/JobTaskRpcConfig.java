package com.sky.task.init;

import java.io.IOException;

import org.apache.zookeeper.KeeperException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sky.task.core.RpcClientManager;
import com.sky.task.core.RpcConfigResource;

@Configuration
@EnableConfigurationProperties(RpcConfigResource.class)
public class JobTaskRpcConfig {
	@Autowired
	private RpcConfigResource rpcConfigResource;
	
	@Bean
	public RpcClientManager getRpcClientManager() throws IOException, KeeperException, InterruptedException {
		return new RpcClientManager(rpcConfigResource);
	}
}
