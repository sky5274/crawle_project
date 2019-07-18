package com.sky.rpc.zk;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.springframework.util.StringUtils;
import com.sky.rpc.resource.ResouceProperties;
import com.sky.rpc.util.RpcSpringBeanUtil;

public class RpcClientManager {
	private static  Log log=LogFactory.getLog(RpcClientManager.class);
	public static String defaut_pref="com_sky_rpc_02";
	private String defaut_desc="com_sky_rpc_link_root_node";
	private ZooKeeper zkClient=null;
	private String url="127.0.0.1:2181";
	private int sessionTimeout=3000;
	private static String ip="";
	private String tipTitle="rpc center ";
	private boolean isSpringStart=false;
	
	public RpcClientManager() throws IOException, KeeperException, InterruptedException {
		initDefNod();
		zkClient=getZookeeper();
	}
	
	public RpcClientManager(String path) throws IOException, KeeperException, InterruptedException {
		initDefNod();
		log.debug(tipTitle+"init");
		this.url=path;
		zkClient=getZookeeper();
	}
	
	private void initDefNod() {
		String node = ResouceProperties.getProperty("rpc.node.prefix");
		if(!StringUtils.isEmpty(node)) {
			isSpringStart=true;
			defaut_pref=node;
		}
		String node_desc = ResouceProperties.getProperty("rpc.node.desc");
		if(!StringUtils.isEmpty(node_desc)) {
			isSpringStart=true;
			defaut_desc=node_desc;
		}
	}
	private String getDefPrefix() {
		if(!isSpringStart) {
			isSpringStart=RpcSpringBeanUtil.getApplicationContext()!=null;
			initDefNod();
			try {
				iniNode();
			} catch (KeeperException | InterruptedException | IOException e) {
			}
		}
		return defaut_pref;
	}

	/**
	 * 获取zookeeper连接
	 * <p>Title: getZookeeper</p>
	 * <p>Description: </p>
	 * @return
	 * @throws IOException
	 * @throws InterruptedException 
	 * @throws KeeperException 
	 */
	public ZooKeeper getZookeeper() throws IOException, KeeperException, InterruptedException {
		if(zkClient==null) {
			String zookeeper_url = ResouceProperties.getProperty("rpc.zookeeper.url");
			if(zookeeper_url!=null) {
				url=zookeeper_url;
			}
			zkClient=new ZooKeeper(url, sessionTimeout, new Watcher() {
				public void process(WatchedEvent watch) {
					if(log.isDebugEnabled()) {
						log.debug("rpc config get watch:"+watch.getPath());
					}
				}
			});
			iniNode();
		}
		return zkClient;
	}
	private void iniNode() throws KeeperException, InterruptedException, IOException {
		if(log.isDebugEnabled()) {
			log.debug(tipTitle+" init node");
		}
		String[] initnodes = getDefPrefix().split("/");
		String node_prefix="";
		for(String node:initnodes) {
			if(!StringUtils.isEmpty(node)) {
				node_prefix += "/"+node;
				if( getZookeeper().exists(node_prefix, true)==null) {
					 getZookeeper().create(node_prefix,defaut_desc.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
				}
			}
		}
	}
	
	
	/**
	 * 初始化路径
	* <p>Title: intPath</p>
	* <p>Description: </p>
	* @param path
	* @return
	 */
	private String intPath(String path) {
		if(!path.startsWith("/"+getDefPrefix())) {
			if(path.startsWith("/")){
				path="/"+path;
			}
			path="/"+getDefPrefix()+path;
		}
		return path.replace("//", "/");
	}
	
	/**
	 * 创建新的节点
	* <p>Title: create</p>
	* <p>Description: </p>
	* @param path
	* @param data
	* @return
	 * @throws IOException 
	* @throws Exception
	 */
	public String create(String path,String data) throws KeeperException, InterruptedException, IOException {
		if(log.isDebugEnabled()) {
		log.debug(tipTitle+"add node:"+path);
		}
		path=intPath(path);
		Stat state = getZookeeper().exists(path, true);
		if( state==null) {
			return  getZookeeper().create(path,data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
		}else {
			getZookeeper().setData(path,data.getBytes(), state.getVersion());
			return path;
		}
	}
	
	/**
	 * 创建临时节点
	* <p>Title: createTemp</p>
	* <p>Description: </p>
	* @param path
	* @param data
	* @return
	* @throws KeeperException
	* @throws InterruptedException
	* @throws IOException
	 */
	public String createTemp(String path,String data) throws KeeperException, InterruptedException, IOException {
		if(log.isDebugEnabled()) {
			log.debug(tipTitle+"add node:"+path);
		}
		path=intPath(path);
		Stat state = getZookeeper().exists(path, true);
		if( state==null) {
			return  getZookeeper().create(path,data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL);
		}else {
			getZookeeper().setData(path,data.getBytes(), state.getVersion());
			return path;
		}
	}
	
	/**
	 * 修改节点信息
	* <p>Title: setData</p>
	* <p>Description: </p>
	* @param path
	* @param data
	* @param version
	* @return
	* @throws KeeperException
	* @throws InterruptedException
	 * @throws IOException 
	 */
	public Stat setData(String path,byte[]data ,int version) throws KeeperException, InterruptedException, IOException {
		if(log.isDebugEnabled()) {
			log.debug(tipTitle+"update node:"+path);
		}
		path=intPath(path);
		if(exists(path)!=null) {
			return  getZookeeper().setData(path, data, version);
		}else {
			return null;
		}
	}
	
	/**
	 * 判断路径是否存在
	* <p>Title: exists</p>
	* <p>Description: </p>
	* @param path
	* @return
	* @throws KeeperException
	* @throws InterruptedException
	 * @throws IOException 
	 */
	public Stat exists(String path) throws KeeperException, InterruptedException, IOException {
		if(log.isDebugEnabled()) {
			log.debug(tipTitle+"check node is exist:"+path);
		}
		path=intPath(path);
		return getZookeeper().exists(path, true);
	}
	
	/**
	 * 获取节点信息
	* <p>Title: getData</p>
	* <p>Description: </p>
	* @param path
	* @param watcher
	* @param stat
	* @return
	* @throws KeeperException
	* @throws InterruptedException
	 * @throws IOException 
	 */
	public byte[] getData(String path,Watcher watcher,Stat stat) throws KeeperException, InterruptedException, IOException {
		path=intPath(path);
		return getZookeeper().getData(path, watcher, stat);
	}
	
	/**
	 * 获取子节点路径
	* <p>Title: getChildrenPath</p>
	* <p>Description: </p>
	* @param path
	* @param watcher
	* @return
	* @throws KeeperException
	* @throws InterruptedException
	 * @throws IOException 
	 */
	public List<String> getChildrenPath(String path,Watcher watcher) throws KeeperException, InterruptedException, IOException {
		path=intPath(path);
		List<String> children = getChildrenNode(path, watcher);
		List<String> list=new LinkedList<String>();
		for(String cs:children) {
			list.add(path+"/"+cs);
		}
		return list;
	}
	
	public List<String> getChildrenNode(String path,Watcher watcher) throws KeeperException, InterruptedException, IOException {
		path=intPath(path);
		if(log.isDebugEnabled()) {
			log.debug(tipTitle+" get child node in path:"+path);
		}
		List<String> children = getZookeeper().getChildren(path, watcher);
		return children;
	}

	/**
	 * 获取本地ip
	* <p>Title: getIp</p>
	* <p>Description: </p>
	* @return
	 */
	public static String getIp() {
		if(StringUtils.isEmpty(ip)) {
			// 获取ip地址
			try {
				Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
				InetAddress ip = null;
				while (allNetInterfaces.hasMoreElements()) {
					NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
					if (netInterface.isLoopback() || netInterface.isVirtual() || !netInterface.isUp()) {
						continue;
					} else {
						Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
						while (addresses.hasMoreElements()) {
							ip = addresses.nextElement();
							if (ip != null && ip instanceof Inet4Address) {
								RpcClientManager.ip=ip.getHostAddress();
								return ip.getHostAddress();
							}
						}
					}
				}
			} catch (Exception e) {
				log.error("IP地址获取失败", e);
			}
		}
		return ip;
	}
}
