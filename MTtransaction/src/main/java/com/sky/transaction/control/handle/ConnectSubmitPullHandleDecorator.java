package com.sky.transaction.control.handle;

import java.util.List;
import java.util.Set;
import org.springframework.util.CollectionUtils;
import com.sky.transaction.bean.ConnectTransationNodeData;
import com.sky.transaction.datasource.factory.ConnectTransactionNodeFactory;
import com.sky.transaction.util.ThreadUtil;


/**
 *	数据连接资源事务提交处理(拉取事务中心数据)装饰器
 * @author 王帆
 * @date  2019年9月24日 上午10:48:04
 */
public class ConnectSubmitPullHandleDecorator implements ConnectSubmitHandle{
	private ConnectSubmitHandle handle;
	private static boolean isStart=false;
	
	public ConnectSubmitPullHandleDecorator(ConnectSubmitHandle handle) {
		this.handle=handle;
		if(handle instanceof ConnectSubmitPullHandle) {
			listenConnectTransactionNode((ConnectSubmitPullHandle)handle);
		}
	}
	
	/**
	 * 	处理具体的事务节点事件
	 * @param node
	 * @author 王帆
	 * @date 2019年9月26日 上午9:03:34
	 */
	public synchronized static void excuteTransaction(ConnectTransationNodeData node) {
		//节点分组与节点的id一致，且事件不是待处理
		if(node.getEventType() !=0 && node.getGroupId() !=null) {
			if(node.getEventType()==1 && node.getGroupId().equals(node.getNodeId())) {
				ConnectTransactionNodeFactory.commitConnectSource(node.getGroupId());
			}else if(node.getEventType()==-1){
				ConnectTransactionNodeFactory.rollbackConnectSource(node.getGroupId());
			}
		}
	}
	
	public void cancel() {
		isStart=false;
	}
	
	/**
	 * 	处理事务节点事件
	 * @param nodes
	 * @author 王帆
	 * @date 2019年9月24日 下午1:25:12
	 */
	private void excuteTransaction(List<ConnectTransationNodeData> nodes) {
		if(!CollectionUtils.isEmpty(nodes)) {
			for(ConnectTransationNodeData node:nodes) {
				excuteTransaction(node);
			}
		}
	}
	
	/**
	 * 如果事务管理客户端是pull类型的handle， 实现监听每隔一定时间主动获取平台共享的事务消息
	 * @param handle
	 * @author 王帆
	 * @date 2019年9月26日 下午4:57:04
	 */
	private void listenConnectTransactionNode(ConnectSubmitPullHandle handle) {
		if(!isStart && handle !=null) {
			//启动线程监听项目事务节点信息
			ThreadUtil.getThreadExcutor().execute(()->{
				while(isStart) {
					try {
						excuteTransaction(handle.listenConnectTransactionNode());
					} catch (Throwable e) {
						//事务组管理控件中  过程中发生任何异常 事务全局失效
						Set<String> groups = ConnectTransactionNodeFactory.getAllGroupKes();
						if(!CollectionUtils.isEmpty(groups)) {
							for(String group:groups) {
								//给对应的事务组的事务处理结果设置返回值
								ConnectTransactionNodeFactory.setTransactionResult(group, new Exception("事务处理异常", e));
							}
						}
					}
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
					}
				}
				
			});
			isStart=true;
		}
	}

	@Override
	public ConnectTransationNodeData submitTransactionNodeEvent(ConnectTransationNodeData req) {
		return handle.submitTransactionNodeEvent(req);
	}

}
