package com.sky.transaction.control.handle;

import com.sky.transaction.bean.ConnectTransationNodeData;

/**
 * 	连接资源数主动获取handle
 * @return
 * @author 王帆
 * @date 2019年9月25日 下午5:34:05
 */
public interface ConnectSubmitPushListenerHandle extends ConnectSubmitHandle{
	
	/**
	 * 	监听节点事件数据
	 * @param node
	 * @author 王帆
	 * @date 2019年9月25日 下午5:37:12
	 */
	public void excuteNode(ConnectTransationNodeData node);
}
