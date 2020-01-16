package com.sky.rpc.zk;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.data.Stat;
import org.springframework.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.sky.rpc.provider.socket.ProviderSocketServer;
import com.sky.rpc.resource.ResouceProperties;
import com.sky.rpc.resource.RpcMethodUtil;

/**
 * Rpc client config util
 *<p>Title: RpcConfig.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2017</p>
 * <p>Company: </p>
 * @author sky
 * @date 2018年10月15日
 */
public class RpcConfig {
	private static  Log log=LogFactory.getLog(RpcConfig.class);
	private static RpcClientManager clientManange=null;
	public static String path;
	
	public void intiPath(String  path) {
		RpcConfig.path=path;
	}
	
	public static boolean isSocketServer() {
		return "socket".equals(ResouceProperties.getProperty("rpc.server.type"));
	}
	
	public static RpcClientManager getManager() throws IOException, KeeperException, InterruptedException {
		if(clientManange==null) {
			if(path==null) {
				clientManange=new RpcClientManager();
			}else {
				clientManange=new RpcClientManager(path);
			}
		}
		return clientManange;
	}
	
	public static RpcClientManager getManager(String path) throws IOException, KeeperException, InterruptedException {
		if(clientManange==null) {
			clientManange=new RpcClientManager(path);
		}
		return clientManange;
	}
	
	/**
	 * 初始化注册类节点
	* <p>Title: regist</p>
	* <p>Description: </p>
	* @param url
	 * @param className 
	 * @throws Exception 
	 */
	public static void regist(String url, String className,String targetName,int port) throws Exception {
		//节点属性
		String nodeData=JSON.toJSONString(new nodeData(RpcClientManager.getIp(), port, className,targetName));
		regist(url,port,className,nodeData);
		
	}
	
	/**
	 * 	注册bean以及将bean方法携带到注册节点中
	 * @param url
	 * @param bean
	 * @param port
	 * @throws ClassNotFoundException
	 * @throws KeeperException
	 * @throws InterruptedException
	 * @throws IOException
	 * @author 王帆
	 * @date 2019年6月21日 上午9:21:20
	 */
	public static void regist(String url, Object bean, int port) throws ClassNotFoundException, KeeperException, InterruptedException, IOException {
		Class<? extends Object> clazz = bean.getClass();
		//节点信息，携带bean的方法的注册信息
		String nodeData=JSON.toJSONString(new nodeData(RpcClientManager.getIp(),port,clazz));
		regist(url,port,clazz.getName(),nodeData);
	}
	
	private static void regist(String url,int port,String className, String nodeData) throws KeeperException, InterruptedException, IOException {
		String[] uarray = url.substring(1).split("/");
		log.info("node url:"+url+" --"+JSON.toJSONString(uarray));
		RpcClientManager manager = getManager();
		int rootIndex = getRootPathIndex(manager,uarray);
		for(int i=rootIndex;i<uarray.length;i++) {
			String upath = getPath(uarray, i);
			String u_result = manager.create(upath, upath);
			log.debug("rpc config add node path:"+u_result);
			if(u_result==null && i!=uarray.length-1) {
				throw new InterruptedException("zookeeper create node error");
			}
		}
		//添加子节点
		String serverurl=url+"/"+className;
		String implPath=manager.create(serverurl, serverurl);
		String tempPath = manager.createTemp(url+"/"+className+"/"+getServerUrlIndex(implPath), nodeData);
		log.debug("service defind url: "+tempPath);
	}
	
	public static List<String> getInitServerUrl(String url){
		List<String> list = new LinkedList<String>();
			try {
				list = getManager().getChildrenPath(url, null);
			} catch (KeeperException | InterruptedException | IOException e) {
				e.printStackTrace();
			}
		return list;
	}
	
	public static long getServerUrlIndex(String url) {
		Set<Long> list=new HashSet<Long>();
		try {
			List<String> nodes = getManager().getChildrenNode(url, null);
			for(String s:nodes) {
				list.add(Long.valueOf(s));
			}
		} catch (KeeperException | InterruptedException | IOException e) {
			e.printStackTrace();
		}
		return getNullIndex(list);
	}
	
	/**
	 * 获取节点的空位序列号
	* <p>Title: getNullIndex</p>
	* <p>Description: </p>
	* @param list
	* @return
	 */
	public static long getNullIndex(Set<Long> list) {
		if(list==null || list.isEmpty()) {
			return 0;
		}
		long index=0;
		for(int i=0;i<list.size();i++) {
			if(list.contains(index)) {
				index++;
			}else {
				break;
			}
		}
		return index;
	}
	
	/**
	 * 获取节点序号
	* <p>Title: getRootPathIndex</p>
	* <p>Description: </p>
	* @param manager
	* @param ulist
	* @return
	* @throws KeeperException
	* @throws InterruptedException
	* @throws IOException
	 */
	private static int getRootPathIndex(RpcClientManager manager, String [] ulist) throws KeeperException, InterruptedException, IOException {
		for(int i=0;i<ulist.length;i++) {
			if(manager.exists(getPath(ulist,i))==null){
				return i;
			}
		}
		return ulist.length>0?0: ulist.length-1;
	}
	
	private static String getPath(String [] ulist,int i) {
		if(i<0) {
			return null;
		}
		String path="/"+ulist[0];
		for(int j=1;j<=i;j++) {
			path+="/"+ulist[j];
		}
		return path;
	}
	
	@SuppressWarnings("rawtypes")
	public static class nodeData {
		private String ip;
		private int port;
		private String rpcType;
		private String intfaceName;
		private String className;
		private Map<String, Class[]> methodParamTypeMap=new HashMap<>();
		private Map<String, String[]> methodParamNameMap=new HashMap<>();
		public nodeData() {}
		public nodeData(String ip,int port,String intfaceName,String className) throws ClassNotFoundException {
			this.ip=ip;
			this.port=port;
			this.setIntfaceName(intfaceName);
			this.setClassName(className);
			this.setRpcType(ProviderSocketServer.getType());
		}
		
		public nodeData(String ip,int port,Class<? extends Object> clazz) throws ClassNotFoundException {
			this.ip=ip;
			this.port=port;
			this.setClassName(clazz.getName());
			this.setRpcType(ProviderSocketServer.getType());
			Type[] intfaces = clazz.getGenericInterfaces();
			if(intfaces!=null && intfaces.length>0) {
				this.setIntfaceName(intfaces[0].getTypeName());
			}
			Method[] methods = clazz.getDeclaredMethods();
			for(Method m:methods) {
				methodParamTypeMap.put(m.getName(), m.getParameterTypes());
				methodParamNameMap.put(m.getName(), RpcMethodUtil.getMethodParameterNames(m));
			}
		}
		public String getIp() {
			return ip;
		}
		public void setIp(String ip) {
			this.ip = ip;
		}
		public int getPort() {
			return port;
		}
		public void setPort(int port) {
			this.port = port;
		}
		public String getClassName() {
			return className;
		}
		public void setClassName(String className) throws ClassNotFoundException {
			this.className = className;
		}
		public String getIntfaceName() {
			return intfaceName;
		}
		public void setIntfaceName(String intfaceName) {
			this.intfaceName = intfaceName;
		}
		public Map<String, Class[]> getMethodParamTypeMap() {
			return methodParamTypeMap;
		}
		public void setMethodParamTypeMap(Map<String, Class[]> methodParamTypeMap) {
			this.methodParamTypeMap = methodParamTypeMap;
		}
		public Map<String, String[]> getMethodParamNameMap() {
			return methodParamNameMap;
		}
		public void setMethodParamNameMap(Map<String, String[]> methodParamNameMap) {
			this.methodParamNameMap = methodParamNameMap;
		}
		public String getRpcType() {
			return rpcType;
		}
		public void setRpcType(String rpcType) {
			this.rpcType = rpcType;
		}
		
	}

	/**
	 * 随机获取接口对应得服务端信息
	* <p>Title: getRandomServer</p>
	* <p>Description: </p>
	* @param name
	* @return
	 */
	public static nodeData getRandomServer(String url) {
		log.debug("rpc config get server request mapper:"+url);
		List<String> list=new LinkedList<String>();
		try {
			list = getManager().getChildrenPath(url, null);
		} catch (KeeperException |InterruptedException |IOException e1) {
			//e1.printStackTrace();
		}
		if(!list.isEmpty()) {
			int randInt = (int) Math.abs(Math.random()*list.size());
			if(randInt==list.size()) {
				randInt--;
			}else if(randInt<0) {
				randInt=0;
			}
			String upath = list.get(randInt);
			try {
				String data = new String(getManager().getData(upath, null,null));
				log.debug(String.format("rpc config get random client node:[path:%s,data:%s]",upath,data));
				return JSON.parseObject(data,nodeData.class);
			} catch (KeeperException | InterruptedException | IOException e) {
				//e.printStackTrace();
			}
		}
		return null;
	}

	public static nodeData getRandomServer(String url, String interfaceImpl) {
		if(StringUtils.isEmpty(interfaceImpl)) {
			return getRandomServer(url);
		}
		log.debug("rpc config get server request mapper:"+url);
		List<String> list=new LinkedList<String>();
		try {
			list = getManager().getChildrenPath(url, null);
		} catch (KeeperException |InterruptedException |IOException e1) {
			e1.printStackTrace();
		}
		if(!list.isEmpty()) {
			
			List<nodeData> nodes=new LinkedList<>();
			for(String upath:list) {
				String data;
				try {
					data = new String(getManager().getData(upath, null, new Stat()));
					if(!StringUtils.isEmpty(data)) {
						nodeData node = JSON.parseObject(data,nodeData.class);
						if(node.getClassName().equals(interfaceImpl)) {
							nodes.add(node);
						}
					}
				} catch (KeeperException | InterruptedException | IOException e) {
					//e.printStackTrace();
				}
			}
			if(!nodes.isEmpty()) {
				int randInt = (int) Math.abs(Math.random()*nodes.size());
				if(randInt==nodes.size()) {
					randInt--;
				}else if(randInt<0) {
					randInt=0;
				}
				return nodes.get(randInt);
			}
		}
		return null;
	}
	
	public static List<nodeData> getRcpNodeDatas(String url){
		log.debug("rpc config get server request mapper:"+url);
		List<String> list=new LinkedList<String>();
		try {
			list = getManager().getChildrenPath(url, null);
		} catch (KeeperException |InterruptedException |IOException e1) {
			e1.printStackTrace();
		}
		List<nodeData> nodes=new LinkedList<>();
		if(!list.isEmpty()) {
			for(String upath:list) {
				String data;
				try {
					data = new String(getManager().getData(upath, null, new Stat()));
					if(!StringUtils.isEmpty(data)) {
						nodeData node = JSON.parseObject(data,nodeData.class);
						nodes.add(node);
					}
				} catch (KeeperException | InterruptedException | IOException e) {
					//e.printStackTrace();
				}
			}
		}
		return nodes;
	}

}


