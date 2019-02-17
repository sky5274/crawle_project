package com.sky.task.core;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.fastjson.JSON;

/**
 * Rpc client config util
 *<p>Title: RpcConfig.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2017</p>
 * <p>Company: </p>
 * @author sky
 * @date 2018年10月15日
 */
@Configuration
@ConditionalOnBean(RpcClientManager.class)
public class RpcConfig {
	private static  Log log=LogFactory.getLog(RpcConfig.class);
	@Autowired
	private RpcClientManager clientManange;
	@Value("${spring.rpc.groupid:1.0.0_dev}")
	private String groupid="1.0.0_dev";
	public static String path;
	
	public void intiPath(String  path) {
		RpcConfig.path=path;
	}
	
	public RpcClientManager getManager() throws IOException, KeeperException, InterruptedException {
		if(clientManange==null) {
			if(path==null) {
				clientManange=new RpcClientManager();
			}else {
				clientManange=new RpcClientManager(path);
			}
		}
		return clientManange;
	}
	
	public  RpcClientManager getManager(String path) throws IOException, KeeperException, InterruptedException {
		if(clientManange==null) {
			clientManange=new RpcClientManager(path);
		}
		return clientManange;
	}
	
	/**
	 * 初始化注册节点
	* <p>Title: regist</p>
	* <p>Description: </p>
	 * @param <T>
	* @param url
	 * @param className 
	 * @throws Exception 
	 */
	public  void regist(String className,int port,String url) throws Exception {
		RpcClientManager manager = getManager();
			String u_result = manager.create("/"+groupid, groupid);
			log.debug("rpc config add node path:"+u_result);
		//添加子节点
		String nodeData=JSON.toJSONString(new NodeData(RpcClientManager.getIp(), port, className,url));
		String implPath=manager.create(getNodeUrl(className), nodeData);
		String tempPath=manager.createTemp(getNodeUrl(className)+"/"+getServerUrlIndex(implPath), nodeData);
		log.debug("service defind url: "+tempPath);
	}
	
	private  String getNodeUrl(String className) {
		return "/"+groupid+"/"+className;
	}
	
	public  List<String> getNodeChilds(String url){
		List<String> list = new LinkedList<String>();
			try {
				list = getManager().getChildrenPath(url, null);
			} catch (KeeperException | InterruptedException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		return list;
	}
	
	private long getServerUrlIndex(String url) {
		Set<Long> list=new HashSet<Long>();
		try {
			List<String> nodes = getManager().getChildrenNode(url, null);
			if(nodes!=null) {
				for(String s:nodes) {
					list.add(Long.valueOf(s));
				}
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
	private long getNullIndex(Set<Long> list) {
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
	private  int getRootPathIndex(RpcClientManager manager, String [] ulist) throws KeeperException, InterruptedException, IOException {
		for(int i=0;i<ulist.length;i++) {
			if(manager.exists(getPath(ulist,i))==null){
				return i;
			}
		}
		return ulist.length>0?0: ulist.length-1;
	}
	
	private  String getPath(String [] ulist,int i) {
		if(i<0) {
			return null;
		}
		String path="/"+ulist[0];
		for(int j=1;j<=i;j++) {
			path+="/"+ulist[j];
		}
		return path;
	}
	
	public static class NodeData {
		private String ip;
		private int port;
		private String className;
		private int type;
		private String url;
		public NodeData() {}
		public NodeData(String ip,int port,String className) throws ClassNotFoundException {
			this.ip=ip;
			this.port=port;
			this.setUrl("http://"+ip+":"+port+"/"+className.substring(className.lastIndexOf(".")+1, className.length()));
			setType(0);
			this.setClassName(className);;
		}
		public NodeData(String ip,int port,String className,String url) throws ClassNotFoundException {
			this.ip=ip;
			this.port=port;
			this.setUrl("http://"+ip+":"+port+url);
			setType(0);
			this.setClassName(className);;
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
		public int getType() {
			return type;
		}
		public void setType(int type) {
			this.type = type;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		
	}

	public  List<NodeData> getAllNodeDataByClassName(String className) {
		List<NodeData> nodes=new LinkedList<>();
		List<String> list = getAllNodeByClassName(className);
		if(!list.isEmpty()) {
			for (String path:list) {
				try {
					String data = new String(getManager().getData(path,null,null));
					nodes.add(JSON.parseObject(data, NodeData.class));
				} catch (KeeperException | InterruptedException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return nodes;
	}
	/**
	 * 随机获取接口对应得服务端信息
	* <p>Title: getRandomServer</p>
	* <p>Description: </p>
	* @param name
	* @return
	 */
	public  NodeData getRandomServer(String className) {
		log.debug("rpc config get server request class:"+className);
		List<String> list = getAllNodeByClassName(className);
		if(!list.isEmpty()) {
			Random random = new Random(list.size());
			String upath = list.get((int) Math.round(random.nextDouble())-1);
			try {
				String data = new String(getManager().getData(upath, new Watcher() {
					
					public void process(WatchedEvent event) {
						log.debug("get new rpc client watch"+event.getPath());
						log.debug("get new rpc client watch"+event.getState());
						log.debug("get new rpc client watch"+event.getType());
						log.debug("get new rpc client watch"+event.getWrapper());
					}
				}, new Stat()));
				log.debug(String.format("rpc config get random client node:[path:%s,data:%s]",upath,data));
				return JSON.parseObject(data,NodeData.class);
			} catch (KeeperException | InterruptedException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		return null;
	}
	
	private List<String> getAllNodeByClassName(String className) {
		List<String> list=new LinkedList<String>();
		try {
			list = getManager().getChildrenPath(getNodeUrl(className), null);
		} catch (KeeperException | InterruptedException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		return list;
	}
}



