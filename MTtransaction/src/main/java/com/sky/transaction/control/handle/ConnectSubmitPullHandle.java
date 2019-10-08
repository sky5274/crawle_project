package com.sky.transaction.control.handle;

import java.util.List;
import com.sky.transaction.bean.ConnectTransationNodeData;

/**
 * 	连接资源数主动获取handle
 * @return
 * @author 王帆
 * @date 2019年9月25日 下午5:34:05
 */
public interface ConnectSubmitPullHandle extends ConnectSubmitHandle{
	
	/**
	 * 获取项目的事务组事件信息
	 * @return
	 * @author 王帆
	 * @date 2019年9月26日 下午4:59:58
	 */
	public List<ConnectTransationNodeData> listenConnectTransactionNode(); 
}
