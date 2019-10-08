package com.sky.rpc.provider.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.FactoryBean;
import com.alibaba.fastjson.JSON;
import com.sky.rpc.base.Result;
import com.sky.rpc.base.RpcRequest;
import com.sky.rpc.call.RpcCallBack;
import com.sky.rpc.call.RpcConnectCallFactory;
import com.sky.rpc.provider.ProviderMethodInvoker;


/**
 * 服务任务(任务的传送对象必须在提供者和消费者都存在)
 *<p>Title: ServiceTask.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2017</p>
 * <p>Company: </p>
 * @author sky
 * @date 2018年10月13日
 */
public class ProviderSocketServerTask implements Runnable{
	private Socket client;
	private static  Log log=LogFactory.getLog(ProviderSocketServerTask.class);

	public void run() {
		ObjectInputStream input = null;
		ObjectOutputStream output = null;
		try {
			// 2.将客户端发送的码流反序列化成对象，反射调用服务实现者，获取执行结果
			input = new ObjectInputStream(client.getInputStream());
			output = new ObjectOutputStream(client.getOutputStream());
			RpcRequest request = (RpcRequest) input.readObject();
			RpcConnectCallFactory.addConnectCall(new ProviderSocketCallBack(input, output, request.getRequestId(),client.getRemoteSocketAddress()));
			int i=0;
			Object[] args  =request.getArgs();
			if(args!=null) {
				for(Object arg:args) {
					if(arg!=null) {
						if(arg instanceof RpcCallBack) {
							args[i]=getArgCallProxy(request.getParameterTypes()[i],input,output,i,request.getRequestId(),request.getHeaders());
						}
					}
					i++;
				}
			}
			request.setArgs(args);
			log.info(">>get request:"+JSON.toJSONString(request));
			// 3.将执行结果反序列化，通过socket发送给客户端
			try {
				output.writeObject(new Result<Object>(request.getRequestId(), ProviderMethodInvoker.invoke(client.getLocalSocketAddress(),request)));
			} catch (Throwable e) {
				output.writeObject(new Result<>(request.getRequestId(), e));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			RpcConnectCallFactory.removeNowConnectCall();
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (client != null) {
					try {
						client.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private <T> T getArgCallProxy(final Class<T> serviceInterface, final ObjectInputStream input, final ObjectOutputStream output, final int i,final String requestId, final Map<String, String> headers) {
		return (T) Proxy.newProxyInstance(FactoryBean.class.getClassLoader(), new Class<?>[]{serviceInterface},
				new InvocationHandler() {
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						log.debug("rpc socket server call back,class: "+serviceInterface.getName()+" method: "+method.getName());
						RpcRequest req = new RpcRequest(serviceInterface.getName()+"_"+i, method, args);
						String id = requestId+"_"+serviceInterface.getName()+"_"+i;
						req.setRequestId(id);
						req.setHeaders(headers);
						//代理调用参数的方法
						output.writeObject(req);
						Result<?> res = (Result<?>) input.readObject();
						if(res.hasException()) {
							throw res.getException();
						}
						return res.getData();
					}
			
		});
	}

	public ProviderSocketServerTask append(Socket client) {
		this.client=client;
		return this;
	}

}
