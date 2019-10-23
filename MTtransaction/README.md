# 分布式事务jar

1：将事务注解方法内发生数据连接资源按线程组进行分组进行管理，事务节点只要回滚，所有事务都回滚，只有发起事务的方法执行完才会进行事务组提交。

2：由于事务方法过程内存在多线程情况（线程执行与线程回调），添加线程关联数据进行同步，事务组提交结果非租塞队列获取，根据平台的事务提交情况进行批量处理事务组提交\回滚操作，并清除相关资源

3：事务分组管理平台， 目前支持：http，redis; 将支持http，rpc调用事务协同

4:  config demo

	com:
	   transacton: 
	      platform: redis    ## http/redis
	      platType: pull			## pull/push
	      url: http://localhost:9000
	      topic: topic
	      timeOut: 30000
	      project: ${spring.application.name}
	      version: 1.0.0