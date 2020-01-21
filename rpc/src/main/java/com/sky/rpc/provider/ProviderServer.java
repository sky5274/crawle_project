package com.sky.rpc.provider;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;
import com.sky.rpc.core.RpcTypeContant;
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
	/**provider server is open flag*/
	protected volatile static boolean isOpen=false;
	public static String portKey="rpc.server.port";
	
	
	public ProviderServer() {
		getPort();
	};
	public ProviderServer(int port) {
		setPort(port+"");
	};
	
	
	
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
	public  static boolean canOpen() {
		return RpcTypeContant.rpcTypeLimit.indexOf(RpcTypeContant.getType())<2;
	}
	
	public abstract void close();
	
}
