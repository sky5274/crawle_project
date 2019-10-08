package com.sky.transaction.control.handle;

import java.util.List;
import org.springframework.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.sky.transaction.bean.ConnectTransationNodeData;
import com.sky.transaction.datasource.factory.ConnectTransactionNodeFactory;

/**
 * http 事务平台项目内事务组事件信息  主动获取handle
 * @author 王帆
 * @date  2019年9月26日 下午4:55:15
 */
public class HttpSubmitPullHandle extends HttpSubmitHandle implements ConnectSubmitPullHandle{
	private String queryUrl="/project/transaction/query";
	
	public HttpSubmitPullHandle(String platfromUrl,Long timeout) {
		super(platfromUrl,timeout);
	}

	@Override
	public List<ConnectTransationNodeData> listenConnectTransactionNode() {
		String obj = excutePostHttp(queryUrl, ConnectTransactionNodeFactory.getDefTransactionNodeData());
		if(!StringUtils.isEmpty(obj)) {
			return JSON.parseArray(obj,ConnectTransationNodeData.class);
		}
		return null;
	}

}
