package com.sky.transaction.datasource.factory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.SynchronousQueue;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import com.sky.transaction.bean.ConnectTransationNodeData;
import com.sky.transaction.bean.TransactionResult;
import com.sky.transaction.control.handle.ConnectSubmitHandle;
import com.sky.transaction.control.handle.ConnectSubmitPullHandleDecorator;
import com.sky.transaction.datasource.bean.ConnectSourceBean;
import com.sky.transaction.datasource.bean.ConnectSourceGroupBean;
import com.sky.transaction.util.SpringMTUtil;
import com.sky.transaction.util.ThreadCacheUtil;
import com.sky.transaction.util.ThreadUtil;

/**
 * 连接资源事务节点控制工厂
 * @author 王帆
 * @date  2019年9月22日 下午5:28:48
 */
public class ConnectTransactionNodeFactory {

	public static String groupIdKey="transaction_group_id";
	public static String parentIdKey="transaction_parent_id";
	public static String nodeIdKey="transaction_node_id";
	public static String nodeTimeOutKey="transaction_node_timeout";
	public static String NODE_ENV_PRO="com.transacton.project";
	public static String NODE_ENV_EVERSION="com.transacton.version";
	public static String DEF_NODE_ENV_PALTFORM="http";
	public volatile static Map<String, ConnectSourceGroupBean> connectGroupMap=new HashMap<String, ConnectSourceGroupBean>();
	//多线程操作事务结果队列
	private volatile static Map<String, SynchronousQueue<TransactionResult>> transactionResultMap=new HashMap<String, SynchronousQueue<TransactionResult>>();
	private volatile static ConnectSubmitPullHandleDecorator connectSubmitHandle;
	private static Log log=LogFactory.getLog(ConnectTransactionNodeFactory.class);

	static {
		ThreadUtil.getThreadExcutor().execute(()->{
			//使用时间调用监听事务节点是否超时
			long nowTime = System.currentTimeMillis();
			synchronized (connectGroupMap) {
				Iterator<Entry<String, ConnectSourceGroupBean>> its = connectGroupMap.entrySet().iterator();
				while(its.hasNext()) {
					Entry<String, ConnectSourceGroupBean> entry = its.next();	
					ConnectSourceGroupBean node =entry .getValue();
					//存续时间小于当前时间戳，则代表超时
					if(node.getExpressTime()<nowTime) {
						connectGroupMap.remove(entry.getKey());
						setTransactionResult(node.getGroupId(), new Exception("mt transaction excute time out:"+node.getTimeOut()));
					}else {
						if(!CollectionUtils.isEmpty(node.getConnects())) {
							node.getConnects().forEach((it)->{it.init();});
						}
					}
				}
			}
		});
	}

	/**
	 * 设置当前事务的节点
	 * @return
	 * @author 王帆
	 * @date 2019年9月22日 下午12:01:41
	 */
	public static String getTransactionNode() {
		String  nodeId=String.format("%s-%s-%s_%d", "mt_node",ThreadUtil.getThreadKey(),ThreadUtil.getShortUuid(),System.currentTimeMillis());
		return nodeId;
	}

	public static ConnectSubmitPullHandleDecorator getConnectHandle() {
		if(connectSubmitHandle==null) {
			//根据注入的数据提交handle  设置装饰器
			connectSubmitHandle=new ConnectSubmitPullHandleDecorator(SpringMTUtil.getBean(ConnectSubmitHandle.class));
		}
		return connectSubmitHandle;
	}

	public static String getThreadNodeId() {
		return  ThreadCacheUtil.get(nodeIdKey, String.class);
	}
	public static void setThreadNodeId(String nodeId) {
		ThreadCacheUtil.put(nodeIdKey, nodeId);
	}
	public static void setThreadGroupId(String groupid) {
		ThreadCacheUtil.put(groupIdKey, groupid);
	}
	public static String getThreadParentId() {
		return ThreadCacheUtil.get(parentIdKey, String.class);
	}
	public static String getThreadGroupId() {
		return ThreadCacheUtil.get(groupIdKey, String.class);
	}

	/**
	 * 当前连接资源分组数据
	 * @return
	 * @author 王帆
	 * @date 2019年9月22日 下午5:29:47
	 */
	public static ConnectSourceGroupBean CurrentSourceGroupBean() {
		ConnectSourceGroupBean sg=new ConnectSourceGroupBean();
		sg.setGroupId(getThreadGroupId());
		sg.setId(getThreadNodeId());
		sg.setParentId(getThreadParentId());
		sg.setThreadKey(ThreadUtil.getThreadKey());
		sg.setTimeOut(ThreadCacheUtil.get(nodeTimeOutKey,Long .class));
		return sg;
	}

	/**
	 * 初始事务节点数据   当前线程对应的事务分组数据
	 * @param timeout
	 * @author 王帆
	 * @date 2019年9月22日 下午5:30:10
	 */
	public static void initTransactionNode(long timeout) {
		String groupNodeId=getThreadGroupId();
		String nodeId=getThreadNodeId();
		String parentNodeId=nodeId;
		if(StringUtils.isEmpty(nodeId)) {
			ThreadCacheUtil.remove();
		}
		setThreadNodeId(getTransactionNode());
		if(StringUtils.isEmpty(nodeId)) {
			parentNodeId=getThreadNodeId();
			nodeId=parentNodeId;
		}
		ThreadCacheUtil.put(parentIdKey, parentNodeId);
		boolean flag=StringUtils.isEmpty(groupNodeId);
		ThreadCacheUtil.put("isFlag", flag);
		if(flag) {
			groupNodeId=nodeId;
			setThreadGroupId(groupNodeId);
		}
		ThreadCacheUtil.put(nodeTimeOutKey, timeout);
		//开启远程事务管理服务事件
		ConnectSourceGroupBean sg=new ConnectSourceGroupBean();
		sg.setGroupId(groupNodeId);
		sg.setId(nodeId);
		sg.setParentId(parentNodeId);
		sg.setThreadKey(ThreadUtil.getThreadKey());
		sg.setTimeOut(Long.valueOf(timeout+""));
		submitTransaction(sg,0);
	}

	/**
	 *	 删除当前事务组对应的连接数据
	 * @author 王帆
	 * @date 2019年9月22日 下午5:20:21
	 */
	public synchronized static void clear(String group) {
		Set<String> keys = connectGroupMap.keySet();
		if(!CollectionUtils.isEmpty(keys)) {
			boolean flag=false;
			Iterator<String> its = keys.iterator();
			while(its.hasNext()) {
				try {
					String key=its.next();
					if(key.startsWith(group+":")) {
						if(!flag) {
							ConnectSourceGroupBean sg=connectGroupMap.get(key);
							submitTransaction(sg, -2);
							flag=true;
						}
						connectGroupMap.remove(key);
						transactionResultMap.remove(group);
					}
				} catch (Exception e) {
				}
				
			}
			ConnectSourceGroupBean sg = CurrentSourceGroupBean();
			if(sg!=null && !StringUtils.isEmpty(sg.getGroupId()) && sg.getGroupId().equals(group)) {
				ThreadCacheUtil.remove();
			}
		}
	}
	public static Set<String> getAllGroupKes() {
		Set<String> groupKeys=new HashSet<>();
		Set<String> keys = connectGroupMap.keySet();
		for(String key:keys) {
			groupKeys.add(key.split(":")[0]);
		}
		return groupKeys;
	}

	/**
	 * 将连接资源添加到当前的事务分组中
	 * @param conn
	 * @author 王帆
	 * @date 2019年9月25日 下午2:15:31
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void addConnectSource(ConnectSourceBean conn) {
		ConnectSourceGroupBean sg = CurrentSourceGroupBean();
		if(sg!=null) {
			ConnectSourceGroupBean temp = connectGroupMap.get(sg.nodeKey());
			if(temp!=null) {
				sg=temp;
			}
			sg.append(conn);
			long expressTime=System.currentTimeMillis()+sg.getTimeOut();
			if(sg.getExpressTime() ==null || expressTime<sg.getExpressTime()) {
				sg.setExpressTime(expressTime);
			}
			connectGroupMap.put(sg.nodeKey(), sg);
		}
	}

	/**
	 * 	根据事务组id提交事件，并设置对应事务组的事务处理结果
	 * @param group
	 * @author 王帆
	 * @throws Exception 
	 * @date 2019年9月25日 上午11:00:57
	 */
	public  synchronized static void commitConnectSource(String group) {
		List<ConnectSourceBean<?>> list = getConnectGroup(group);
		if(!CollectionUtils.isEmpty(list)) {
			log.debug("mt transaction commit group: "+group);
			ConnectSourceFactory csf=new ConnectSourceFactory(list);
			try {
				csf.commit();
				setTransactionResult(group,true);
			} catch (Exception e) {
				setTransactionResult(group,e);
			}
		}
		clear(group);
	}

	/**
	 * 根据groupId 回滚对应的事务分组资源连接，并添加对应的事务处理结果到非租塞消息队列中
	 * @param group
	 * @author 王帆
	 * @date 2019年9月25日 下午2:16:12
	 */
	public synchronized static void rollbackConnectSource(String group){
		List<ConnectSourceBean<?>> list = getConnectGroup(group);
		if(!CollectionUtils.isEmpty(list)) {
			log.debug("mt transaction rollback group: "+group);
			ConnectSourceFactory csf=new ConnectSourceFactory(list);
			try {
				csf.rollBack();
				setTransactionResult(group,true);
			} catch (Exception e) {
				setTransactionResult(group,e);
			}
		}
		clear(group); 
	}

	/**
	 * 	事务结果多线程操作同步队列
	 * @param group
	 * @param success
	 * @author 王帆
	 * @date 2019年9月25日 下午1:32:48
	 */
	private static void setTransactionResult(String group,boolean success) {
		SynchronousQueue<TransactionResult> tr = transactionResultMap.get(group);
		if(tr!=null) {
			try {
				tr.put(new TransactionResult(success));
			} catch (InterruptedException e) {
				tr.offer(new TransactionResult(e));
			}
		}
	}
	public static void setTransactionResult(String group,Throwable exp) {
		SynchronousQueue<TransactionResult> tr = transactionResultMap.get(group);
		if(tr!=null) {
			try {
				tr.put(new TransactionResult(exp));
			} catch (InterruptedException e) {
				tr.offer(new TransactionResult(e));
			}
		}
	}

	/**
	 * 
	 * 
	 * @param group
	 * @return
	 * @author 王帆
	 * @date 2019年9月25日 上午11:15:29
	 */
	private static List<ConnectSourceBean<?>> getConnectGroup(String group) {
		List<ConnectSourceBean<?>> list=new LinkedList<>();
		for(String key:connectGroupMap.keySet()) {
			if(key.startsWith(group+":")) {
				list.addAll(connectGroupMap.get(key).getConnects());
			}
		}
		return list;
	}

	/**
	 *	给事务中心提交当前节点数据链接资源的事务提交状态 
	 * @author 王帆
	 * @param sg 
	 * @return 
	 * @date 2019年9月23日 下午1:28:48
	 */
	public static SynchronousQueue<TransactionResult> commit(ConnectSourceGroupBean sg) {
		/*
		 * 	事务提交时，需要判断是否存在上级事务线程
		 */
		if(sg==null) {
			sg = CurrentSourceGroupBean();
		}
		if(!StringUtils.isEmpty(sg.getGroupId()) && !StringUtils.isEmpty(sg.getId())) {
			if(sg.getId().equals(sg.getParentId())) {
				//向事务管理中心提交  当前事务节点--事务提交事件
				submitTransaction(sg,1);
			}
		}
		return getCurrentTransactionResult(sg);
	}

	private static SynchronousQueue<TransactionResult> getCurrentTransactionResult(ConnectSourceGroupBean sg) {
		SynchronousQueue<TransactionResult> tr = transactionResultMap.get(sg.getGroupId());
		if(tr==null) {
			tr=new SynchronousQueue<>();
			transactionResultMap.put(sg.getGroupId(), tr);
		}
		return tr;
	}

	/**
	 * 	本地数据链接资源事务回滚，并提交关联的事务分组数据回滚
	 * 
	 * @author 王帆
	 * @return 
	 * @date 2019年9月23日 下午1:30:59
	 */
	public static SynchronousQueue<TransactionResult> rollback() {
		ConnectSourceGroupBean sg = CurrentSourceGroupBean();
		if(!StringUtils.isEmpty(sg.getGroupId())) {
			//提交回滚事件
			submitTransaction(sg,-1);
			//本地先回滚
			commitConnectSource(sg.getGroupId());
		}
		return getCurrentTransactionResult(sg);
	}

	/**
	 * 	提交连接组节点事件
	 * @param sg
	 * @param event	-1：回滚；0：待处理；1：提交
	 * @author 王帆
	 * @date 2019年9月25日 上午10:38:21
	 */
	private static  void submitTransaction(ConnectSourceGroupBean sg,int event) {
		ConnectTransationNodeData req=getNodeData(sg);
		req.setEventType(event);
		//向事务管理中心提交  
		getConnectHandle().submitTransactionNodeEvent(req);
	}

	/**
	 * 根据事务分组信息获取项目的事务节点服务数据
	 * @param sg
	 * @return
	 * @author 王帆
	 * @date 2019年9月25日 下午2:18:09
	 */
	private static ConnectTransationNodeData getNodeData(ConnectSourceGroupBean sg) {
		ConnectTransationNodeData req=getTransactionNodeData();
		req.setGroupId(sg.getGroupId());
		req.setNodeId(sg.getId());
		req.setTimeOut(sg.getTimeOut());
		return req;
	}
	private static ConnectTransationNodeData defProNode;

	public static ConnectTransationNodeData getDefTransactionNodeData() {
		if(defProNode ==null) {
			defProNode=getTransactionNodeData();
		}
		return defProNode;
	}
	private static ConnectTransationNodeData getTransactionNodeData() {
		ConnectTransationNodeData req=new ConnectTransationNodeData();
		req.setProject(SpringMTUtil.getEvnProperty(NODE_ENV_PRO, NODE_ENV_PRO));
		req.setVersion(SpringMTUtil.getEvnProperty(NODE_ENV_EVERSION, NODE_ENV_EVERSION));
		return req;
	}


}
