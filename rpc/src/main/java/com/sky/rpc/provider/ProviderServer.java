package com.sky.rpc.provider;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;
import com.sky.rpc.resource.ResouceProperties;

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
	protected static List<String> rpcTypeLimit=Arrays.asList("socketio","bootsocket","http");
	protected static String rpcType;
	/**provider server is open flag*/
	protected volatile static boolean isOpen=false;
	public static String portKey="rpc.server.port";
	public static String rpcTypeKey="rpc.server.type";
	
	public ProviderServer() {
		getPort();
	};
	public ProviderServer(int port) {
		setPort(port+"");
	};
	
	public static String getType() {
		if(rpcType ==null) {
			rpcType=ResouceProperties.getProperty(rpcTypeKey);
			if(!rpcTypeLimit.contains(rpcType)) {
				rpcType=rpcTypeLimit.get(0);
			}
		}
		return rpcType;
	}
	
	/**
	 * 是否是 java-socket 服务端
	 * @return
	 * @author 王帆
	 * @date 2020年1月10日 下午4:32:55
	 */
	public static boolean isSocketServer() {
		return "socketio".equals(ProviderServer.getType());
	}
	
	
	public static int getPort() {
		if(ProviderServer.port==null) {
			String sys_port = System.getProperty(portKey);
			if(StringUtils.isEmpty(sys_port)) {
				String port_str = ResouceProperties.getProperty(portKey);
				if(port_str!=null) {
					sys_port=port_str;
				}
			}
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
	
	public abstract void close();
	
}
