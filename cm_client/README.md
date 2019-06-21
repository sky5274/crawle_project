# my_spring_limit and trace client
1：该客户端使用web HandlerInterceptor  作为请求监听的限流全局监听，提供两种限流措施：1. 注解模式，2.服务限流enable，使用服务限流

服务配置信息

	sky:
	
	   client:
	   
	      config:
	      
	         location: http://127.0.0.1:9000    -- 链路-限流服务请求地址
	         
	         profile: test						-- 环境
	         
	         enablelimit: false					-- 是否启动服务限流
	         
	         version: 1.0.0						-- 版本
	         
	         readTimeout: 60*1000				-- 链路-限流服务请求超时
	         
	         serviceName: project-name			-- 项目名称
	         
	         desc: 描述							-- 项目描述

2：链路请求记录设计：使用aop记录所有的http请求，以及使用httputil时自动添加到链路记录中

3：服务器开放的url注册到链路-限流服务器中，使用spring容器启动-卸载事件控制服务的注册与卸载

4:服务器提供动态配置信息数据提供动态查询配置的服务  