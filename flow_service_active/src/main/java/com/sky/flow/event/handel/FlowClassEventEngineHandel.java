package com.sky.flow.event.handel;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sky.flow.bean.FlowNodeEventBean;
import com.sky.flow.bean.TaskFlowBean;
import com.sky.flow.bean.TaskFlowNodeBean;
import com.sky.flow.event.FlowEventEngineHandel;
import com.sky.flow.exception.FlowException;
import com.sky.flow.util.BeanFindUtil;
import com.sky.flow.util.BeanFindUtil.MethodInfo;
import com.sky.flow.util.SpringBeanUtil;
import com.sky.rpc.base.RpcRequest;
import com.sky.rpc.call.RpcConnectCall;
import com.sky.rpc.call.RpcConnectCallFactory;
import com.sky.rpc.core.cilent.RpcClient;
import com.sky.rpc.resource.ResouceProperties;
import com.sky.rpc.resource.RpcMethodUtil;
import com.sky.rpc.zk.RpcConfig;
import com.sky.rpc.zk.RpcConfig.nodeData;

/**
 * 流程事件：class类型的事件的执行引擎
 * @author 王帆
 * @date  2019年6月7日 下午5:30:07
 */
@Component
public class FlowClassEventEngineHandel implements FlowEventEngineHandel{
	protected Log log=LogFactory.getLog(getClass());
	protected Map<String, Object> taskFlowParamMap=new HashMap<>();
	protected TaskFlowNodeBean node;
	protected TaskFlowBean task;
	protected String clazzName;
	protected String methodName="excute";
	
	@Override
	public String type() {
		return "class";
	}

	
	protected void initTaskEvent(FlowNodeEventBean event, TaskFlowNodeBean node, TaskFlowBean task) throws FlowException {
		List<String> errs=new LinkedList<>();
		if(node==null) {
			errs.add("流程任务节点信息未提交");
		}
		if(task==null) {
			errs.add("流程任务信息未提交");
		}
		String classMethod =event.getContent();
		if(StringUtils.isEmpty(classMethod)) {
			errs.add("流程事件未定义调用的方法");
		}
		if(!errs.isEmpty()) {
			throw new FlowException(errs);
		}
		
		this.task=task;
		this.node=node;
	}

	@Override
	public void invoke(FlowNodeEventBean event, TaskFlowNodeBean node, TaskFlowBean task) throws FlowException{
		initTaskEvent(event,node,task);
		if(StringUtils.isEmpty(clazzName)) {
			clazzName =event.getContent();
		}
		
		//初始化加载流程任务与任务节点信息
		initTaskParamsMap();
		
		/*
		 *流程事件执行会在一下3个地方寻找：1.服务器，2.rpc调用者本地，3.rpc版本注册服务内 
		 */
		//流程服务器本地
		boolean flag=invokeLocalMethod();
		if(!flag) {
			//rpc调用者内
			flag=invokeRpcClientMethod();
		}
		if(!flag) {
			//rpc服务注册节点
			flag=invokeRemoteMethod();
		}
		if(!flag) {
			throw new FlowException(getExceptionPrefix()+" 执行异常：本地/远程未找到执行方法："+clazzName+"."+methodName);
		}
		
	}
	
	private String getExceptionPrefix() {
		return "事件："+node.getNodeKey()+" 执行类型："+type();
	}
	
	/**
	 * 	服务端本地调用
	 * @param clazzName
	 * @param methodName
	 * @return
	 * @throws FlowException
	 * @author 王帆
	 * @date 2019年6月21日 下午3:38:07
	 */
	protected boolean invokeLocalMethod() throws FlowException {
		Class<?> clazz=null;
		Object clazzBean=null;
		try {
			clazzBean = SpringBeanUtil.getBean(clazzName);
		} catch (Exception e) {}
		
		if(clazzBean==null) {
			try {
				 clazz= Class.forName(clazzName);
			} catch (Exception e) {
			}
		}else {
			clazz=clazzBean.getClass();
		}
		if(clazz==null) {
			return false;
		}
		List<Method> methods = getClassMethod(clazz,methodName);
		if(methods.isEmpty()) {
			return false;
		}
		Method method = methods.get(0);
		String[] paramsName = RpcMethodUtil.getMethodParameterNames(method);
		Object[] args = getParamsByNames(paramsName);
		if(paramsName!=null && paramsName.length>0 && args!=null && args.length != paramsName.length) {
			throw new FlowException(getExceptionPrefix()+" 本地调用失败：参数匹配异常；[paramsName:"+JSON.toJSONString(paramsName)+",args:"+JSON.toJSONString(args)+"]");
		}
			//本地服务调用
			try {
				method.invoke(clazzBean, args);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				log.error(e.getMessage(), e);
			}
		
		
		return true;
	}
	
	@SuppressWarnings("rawtypes")
	protected boolean invokeRemoteMethod() throws FlowException {
		String version = ResouceProperties.getProperty("flow.server.version");
		if(!StringUtils.isEmpty(version)) {
			List<nodeData> datas=null;
			try {
				 datas= RpcConfig.getRcpNodeDatas("/"+version+"/"+clazzName);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
			if(datas!=null && !datas.isEmpty()) {
				for(nodeData node:datas) {
					Class[] paramsTypes = node.getMethodParamTypeMap().get(methodName);
					if(paramsTypes!=null) {
						String[] names = node.getMethodParamNameMap().get(methodName);
						RpcRequest req=new RpcRequest(clazzName,methodName,names,paramsTypes,getParam(Arrays.asList(names)).toArray());
						try {
							new RpcClient(new InetSocketAddress(node.getIp(),node.getPort())).request(req,Object.class);
							return true;
						} catch (Throwable e) {
							log.error(e.getMessage(), e);
							if( e instanceof FlowException) {
								throw (FlowException)e;
							}
							throw new FlowException(getExceptionPrefix()+" 调用失败，原因："+e.getMessage());
						}
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * rpc调用时，执行调用rpc客户端的方法
	 * @return
	 * @throws FlowException
	 * @author 王帆
	 * @date 2019年6月21日 下午4:29:16
	 */
	protected boolean invokeRpcClientMethod() throws FlowException {
		RpcConnectCall callback = RpcConnectCallFactory.getNowConnectCall();
		if(callback!=null) {
			MethodInfo methodinfo=null;
			try {
				Class<?>[] cls= {String.class,String.class};
				Object[] args={clazzName,methodName};
				RpcRequest req=new RpcRequest(BeanFindUtil.class.getName(),"getMethodInfo",null,cls,args) ;
				List<MethodInfo> methods=callback.call(req);
				if(methods!=null && !methods.isEmpty()) {
					methodinfo = methods.get(0);
				}
			} catch (Throwable e) {log.error(e.getMessage(), e);}
			if(methodinfo!=null) {
				try {
					callback.call( getMethodRpcParam(methodinfo));
					return true;
				} catch (Throwable e) {
					log.error(e.getMessage(), e);
					if( e instanceof FlowException) {
						throw (FlowException)e;
					}
					throw new FlowException(getExceptionPrefix()+" 调用失败，原因："+e.getMessage());
				}
			}
		}
		return false;
	}
	
	private List<Object> getParam(List<String> names) {
		List<Object> params=new LinkedList<>();
		for(String param:names) {
			params.add(taskFlowParamMap.get(param));
		}
		return params;
	}
	
	private RpcRequest getMethodRpcParam(MethodInfo methodinfo) throws FlowException{
		List<Object> params = getParam(methodinfo.getParamNames());
		@SuppressWarnings("rawtypes")
		List<Class> paramTypes=new LinkedList<>();
		int i=0;
		for(String type:methodinfo.getParamTypes()) {
			try {
				paramTypes.add(Class.forName(type));
			} catch (ClassNotFoundException e) {
				throw new FlowException(getExceptionPrefix()+" 调用失败："+clazzName+"."+methodName+"参数："+methodinfo.getParamNames().get(i)+":"+type+ " 无法转换");
			}
			i++;
		}
		return new RpcRequest(clazzName, methodName, methodinfo.getParamNames().toArray(new String[params.size()]), paramTypes.toArray(new Class[paramTypes.size()]), params.toArray());
	}


	/**
	 * 初始化任务与节点信息参数化
	 * @param node
	 * @param task
	 * @author 王帆
	 * @date 2019年6月17日 上午10:32:12
	 */
	protected void initTaskParamsMap() {
		JSONObject nodejson = JSON.parseObject(JSON.toJSONString(node));
		JSONObject taskjson = JSON.parseObject(JSON.toJSONString(task));
		for(String key:taskjson.keySet()) {
			taskFlowParamMap.put(key, taskjson.get(key));
		}
		for(String key:nodejson.keySet()) {
			taskFlowParamMap.put(key, nodejson.get(key));
		}
		taskFlowParamMap.remove("id");
		taskFlowParamMap.put("nodeId", node.getId());
		System.err.println(node.getId());
		System.err.println(JSON.toJSONString(taskFlowParamMap));
	}

	protected Object[] getParamsByNames(String[] paramsName) {
		List<Object> list=new LinkedList<>();
		for(String name:paramsName) {
			list.add(taskFlowParamMap.get(name));
		}
		return list.isEmpty()?null:list.toArray(new Object[list.size()]);
	}

	protected List<Method> getClassMethod(Class<?> clazz, String classMethod) {
		List<Method> list=new LinkedList<>();
		Method[] methods = clazz.getDeclaredMethods();
		for(Method m:methods) {
			if(m.getName().equals(classMethod)) {
				list.add(m);
			}
		}
		return list;
	}

	protected Class<?> getRemoteClass(String clazzName, String classMethod) {
		return null;
	}


}
