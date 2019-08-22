# rpc 服务
1：rpc 服务消费与提供

1.1  基本配置  rpc.properties

	rpc.server.port=9000   -- rpc  服务提供端口
	
	rpc.server.type=socket	--rpc 客户端、服务端口使用标识socket\netty

2.1  服务调用支持调用过程中本地参数方法回调（实现RpcCallBack），以及线程内rpc连接回调机制