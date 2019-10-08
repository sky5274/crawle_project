package com.sky.transaction.datasource.advice;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.UndeclaredThrowableException;
import java.sql.Connection;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.interceptor.ExposeInvocationInterceptor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import com.sky.transaction.annotation.MTransaction;
import com.sky.transaction.bean.TransactionResult;
import com.sky.transaction.datasource.bean.ConnectSourceBean;
import com.sky.transaction.datasource.bean.ConnectSourceGroupBean;
import com.sky.transaction.datasource.bean.SqlConnectSourceBean;
import com.sky.transaction.datasource.factory.ConnectSourceFactory;
import com.sky.transaction.datasource.factory.ConnectTransactionNodeFactory;
import com.sky.transaction.util.ThreadCacheUtil;
import com.sky.transaction.util.ThreadUtil;


/** 
 * 	数据源链接事务aop监听  
 * @author 王帆
 * @date  2019年9月12日 下午5:04:38
 */
@Order(-1)   
@Aspect  
@Component
public class SourceConnectTransactionAdvice {
	Log log=LogFactory.getLog(SourceConnectTransactionAdvice.class);

	@Around("execution(* *(..)) && @annotation(mtt)")
	public Object arroundTransaction(ProceedingJoinPoint pjp,MTransaction mtt) throws Throwable {
		try {
			/*
			 * 1.事务控制，检查是否存在上级事务，当前线程是否注册的事务中心服务，注册服务
			 * 2.监听服务中心  下发事务提交状态（提交与回滚）
			 * 3.当超过时间后，抛出事务控制超时异常 
			 */
			//根据线程初始化节点
			int timeOut=mtt.timeout();
			//初始化线程事务环境数据
			ConnectTransactionNodeFactory.initTransactionNode(timeOut);
			Map<String, Object> resource = ThreadCacheUtil.getResource();
			ConnectSourceGroupBean sg = ConnectTransactionNodeFactory.CurrentSourceGroupBean();
			RequestAttributes reqAttrs = RequestContextHolder.getRequestAttributes();
			final MethodInvocation mi = ExposeInvocationInterceptor.currentInvocation();
			Future<Object> resultFutrue = ThreadUtil.getThreadExcutor().submit(new Callable<Object>() {
				@Override
				public Object call() throws Exception {
					
					//同步线程request  与  事务组数据
					RequestContextHolder.setRequestAttributes(reqAttrs);
					ThreadCacheUtil.bindResource(resource);
					try {
						Object res =ExposeInvocationInterceptor.INSTANCE.invoke(mi);
						//使用同步租塞消息队列获取事务处理结果,事务组提交，使用异步提交
						ThreadUtil.getThreadExcutor().submit(new Callable<TransactionResult>() {

							@Override
							public TransactionResult call() throws Exception {
								TransactionResult tr= ConnectTransactionNodeFactory.commit(sg).take();
								if(tr!=null && tr.getException() !=null) {
									throw new Exception(tr.getException());
								}
								return tr;
							}
						});
						return res;
					} catch (Throwable e) {
						throw new Exception(e);
					}
				}
			});
//			//使用回调
			Object res = resultFutrue.get(timeOut, TimeUnit.MILLISECONDS);
			
			return res;
		} catch (Throwable e) {
			//向事务中心提交回滚请求 
			TransactionResult tr = ConnectTransactionNodeFactory.rollback().take();
			log.debug("transaction group result:"+tr);
			if(tr!=null && tr.getException()!=null) {
				throw tr.getException();
			}
			if(ExecutionException.class.getName().equals(e.getClass().getName())) {
				throw e.getCause().getCause();
			}
			if(UndeclaredThrowableException.class.getName().equals(e.getClass().getName())) {
				throw e.getCause();
			}
			throw e;
		}
	}
	
	@Around("execution(* *.getConnection(..))")
	public Object arroundConnnect(ProceedingJoinPoint pjp)throws Throwable{
		Object obj = pjp.proceed();
		if(!StringUtils.isEmpty(ConnectTransactionNodeFactory.getThreadGroupId())) {
			ConnectSourceBean<?> conn = ConnectSourceFactory.getConnectSource(obj);
			if(conn!=null) {
				//当前线程添加数据链接资源
				ConnectTransactionNodeFactory.addConnectSource(conn);
				if(conn.getConnect() instanceof Connection) {
					//如果是sql  connect 则使用代理方式，代理监听方法调用,在这边控制链接是否关闭，提交 等方法
					obj=Proxy.newProxyInstance(
							getClass().getClassLoader(), new Class<?>[]{Connection.class},
							new InvocationHandler() {
								public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
									if("close".equals(method.getName())) {
										SqlConnectSourceBean sqlconn = (SqlConnectSourceBean)conn;
										if(!sqlconn.isTransaction()) {
											//数据资源事务结束后资源方法执行
											return method.invoke(conn.getConnect(), args);
										}
										return null;
									}else {
										return method.invoke(conn.getConnect(), args);
									}
								}
						});
				}
			}
		}
		return obj;
	}
	
	
}
