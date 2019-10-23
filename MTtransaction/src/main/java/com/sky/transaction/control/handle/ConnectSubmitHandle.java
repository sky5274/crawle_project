package com.sky.transaction.control.handle;

import com.sky.transaction.bean.ConnectTransationNodeData;

/**
 * 	数据链接提交handle
 * @author 王帆
 * @date  2019年9月24日 上午10:03:51
 */
public interface ConnectSubmitHandle {
	
	/**
	 * 	提交事务节点事务事件
	 * @param req  事务事件数据
	 * @return
	 * @author 王帆
	 * @date 2019年9月24日 上午10:39:13
	 */
	ConnectTransationNodeData submitTransactionNodeEvent(ConnectTransationNodeData req);
}
