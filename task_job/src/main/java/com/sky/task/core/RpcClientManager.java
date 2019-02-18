package com.sky.task.core;

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
import org.springframework.boot.logging.LogLevel;
import org.springframework.util.StringUtils;
import com.alibaba.fastjson.JSON;

public class RpcClientManager {
	private static  Log log=LogFactory.getLog(RpcClientManager.class);
	private ZooKeeper zkClient=null;
	private RpcConfigResource rpcConfigResource;
	public static String ip;
	private String node_prefix="";
	
	public RpcClientManager() throws IOException, KeeperException, InterruptedException {
		zkClient=getZookeeper();
	}
	public RpcClientManager(RpcConfigResource rpcConfigResource) throws IOException, KeeperException, InterruptedException {
		this.setRpcConfigResource(rpcConfigResource);
		zkClient=getZookeeper();
	}
	public RpcClientManager(RpcConfigResource rpcConfigResource,String path) throws IOException, KeeperException, InterruptedException {
		if(rpcConfigResource!=null) {
			rpcConfigResource.setUrl(path);
			this.setRpcConfigResource(rpcConfigResource);
		}
		zkClient=getZookeeper();
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
			zkClient=new ZooKeeper(getRpcConfigResource().getUrl(), rpcConfigResource.getSessionTimeout(), new Watcher() {
				public void process(WatchedEvent watch) {
					showLog(LogLevel.DEBUG," get watch:"+watch.getPath());
				}
			});
			iniNode();
		}
		return zkClient;
	}
	private void iniNode() throws IOException, KeeperException, InterruptedException {
		String[] initnodes = getRpcConfigResource().getPrefix().split("/");
		showLog(LogLevel.DEBUG," init node"+JSON.toJSONString(initnodes));
		for(String node:initnodes) {
			if(!StringUtils.isEmpty(node)) {
				node_prefix+="/"+node;
				if( getZookeeper().exists(node_prefix, true)==null) {
					 getZookeeper().create(node_prefix,getRpcConfigResource().getDesc().getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
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
		if(!path.startsWith(node_prefix)) {
			if(path.startsWith("/")){
				path="/"+path;
			}
			path=node_prefix+path;
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
		showLog(LogLevel.INFO,"add node:"+path);
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
		showLog(LogLevel.INFO,"add node:"+path);
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
	 * 	删除节点
	 * @param path
	 * @throws KeeperException
	 * @throws InterruptedException
	 * @throws IOException
	 * @author 王帆
	 * @date 2019年2月7日 下午9:54:12
	 */
	public void remove(String path) throws KeeperException, InterruptedException, IOException {
		showLog(LogLevel.INFO,"add node:"+path);
		path=intPath(path);
		Stat state = getZookeeper().exists(path, true);
		if(state !=null) {
			getZookeeper().delete(path, state.getVersion());
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
		showLog(LogLevel.INFO,"update node:"+path);
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
		showLog(LogLevel.INFO,"check node is exist:"+path);
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
		List<String> list=new LinkedList<String>();
		List<String> children = getChildrenNode(path, watcher);
		if(children!=null) {
			for(String cs:children) {
				list.add(path+"/"+cs);
			}
		}
		return list;
	}
	
	public List<String> getChildrenNode(String path,Watcher watcher) throws KeeperException, InterruptedException, IOException {
		path=intPath(path);
		showLog(LogLevel.DEBUG," get child node in path:"+path);
		if(exists(path)!=null) {
			List<String> children = getZookeeper().getChildren(path, watcher);
			return children;
		}else {
			return null;
		}
		
	}
	
	private void showLog(LogLevel level, String msg) {
		if(getRpcConfigResource().isShowlog()) {
			switch (level) {
			case DEBUG:
				log.debug(getRpcConfigResource().getTipTitle()+msg);
				break;
			case INFO:
				log.info(getRpcConfigResource().getTipTitle()+msg);
				break;
			case WARN:
				log.warn(getRpcConfigResource().getTipTitle()+msg);
				break;
			case ERROR:
				log.error(getRpcConfigResource().getTipTitle()+msg);
				break;
			default:
				log.debug(getRpcConfigResource().getTipTitle()+msg);
				break;
			}
		}
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
	public RpcConfigResource getRpcConfigResource() {
		if(this.rpcConfigResource==null) {
			this.rpcConfigResource=new RpcConfigResource();
		}
		return rpcConfigResource;
	}
	public void setRpcConfigResource(RpcConfigResource rpcConfigResource) {
		this.rpcConfigResource = rpcConfigResource;
	}
	
}

