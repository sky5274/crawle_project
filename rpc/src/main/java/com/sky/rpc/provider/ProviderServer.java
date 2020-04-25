package com.sky.rpc.provider;

import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;
import com.sky.rpc.core.RpcTypeContant;
import com.sky.rpc.resource.ResouceProperties;
import com.sky.rpc.zk.RpcClientManager;

/**
 * 服务端
 *<p>Title: ServerManager.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2017</p>
 * <p>Company: </p>
 * @author sky
 * @date 2018年10月13日
 */
public abstract class ProviderServer extends Thread {
	protected static ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
	protected Log log=LogFactory.getLog(getClass());
	protected static Integer port;
	protected static String host;
	/**provider server is open flag*/
	protected volatile static boolean isOpen=false;
	public static String portKey="rpc.server.port";
	public static String ipKey="rpc.server.ip";
	public static String urlKey="rpc.server.url";
	
	
	public ProviderServer() {
		getPort();
	};
	public ProviderServer(int port) {
		setPort(port+"");
	};
	
	/**
	 * 获取服务定义的ip或域名
	 * @return
	 * @author 王帆
	 * @date 2020年4月25日 下午2:56:00
	 */
	public static String getHost() {
		if(host ==null) {
			boolean flag=false;
			String url=ResouceProperties.getProperty(urlKey);
			if(!StringUtils.isEmpty(url)) {
				flag=true;
				try {
					URL u=new URL(url);
					setHost(u.getHost());
					setPort(u.getPort()+"");
				}catch (Exception e) {
					flag=false;
				}
			}
			if(!flag) {
				setHost(ResouceProperties.getProperty(ipKey));
			}
		}
		return ProviderServer.host;
	}
	
	public static void setHost(String host) {
		if(StringUtils.isEmpty(host)) {
			host=RpcClientManager.getIp();
		}
		try {
			ProviderServer.host=host;
			System.setProperty(ipKey, host);
		} catch (Exception e) {
		}
	}
	
	public static int getPort() {
		if(ProviderServer.port==null) {
			String sys_port = ResouceProperties.getProperty(portKey);
			setPort(sys_port);
		}
		return ProviderServer.port;
	}
	
	public  static void setPort(String sys_port) {
		if(StringUtils.isEmpty(sys_port)) {
			sys_port="9000";
		}
		try {
			ProviderServer.port=Integer.valueOf(sys_port);
			System.setProperty(portKey, sys_port);
		} catch (Exception e) {
		}
	}
	public synchronized static boolean isOpen() {
		return isOpen;
	}
	public synchronized static void setOpen(boolean isOpen) {
		ProviderServer.isOpen = isOpen;
	}
	public  static boolean canOpen() {
		return RpcTypeContant.rpcTypeLimit.indexOf(RpcTypeContant.getType())<2;
	}
	
	public abstract void close();
	
}
