package com.sky.data.core.handel;

import java.net.InetSocketAddress;
import java.util.LinkedList;
import java.util.List;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.sky.data.bean.DataBatchTaskExtEntity;
import com.sky.data.core.bean.DataBatchBean;
import com.sky.data.handel.BatchDataFunctionHandel;
import com.sky.pub.util.ListUtils;
import com.sky.rpc.core.RpcProxy;
import com.sky.rpc.resource.ResouceProperties;
import com.sky.rpc.zk.RpcConfig;
import com.sky.rpc.zk.RpcConfig.nodeData;

/**
 * 分布式多数据端处理handel
 * @author 王帆
 * @date  2019年8月3日 下午3:02:29
 */
public class MultipleDataOperatHandel extends AbstractDataOperatHandel{
	protected String baseUrl; 
	public MultipleDataOperatHandel(String taskId, String groupId, DataBatchTaskExtEntity taskExt) throws Exception {
		super(groupId,taskExt);
		if(StringUtils.isEmpty(taskId)) {
			throw new Exception("taskId is null");
		}
		if(!taskId.equals(super.taskContent.getTaskId())) {
			throw new Exception("taskId is not same");
		}
		this.baseUrl="/"+ResouceProperties.getProperty("rpc.group")+"/"+ResouceProperties.getProperty("rpc.version");
	}
	
	/**
	 * 根据任务id获取所有分布式数据（包含ip，文件名，数据信息等）
	 * @return
	 * @author 王帆
	 * @date 2019年7月26日 上午9:31:45
	 */
	protected List<DataBatchBean> getAllBatchDatas() {
		List<DataBatchCollectQueryHandel> queryHandels = getAllClass(DataBatchCollectQueryHandel.class);
		List<DataBatchBean> datas=new LinkedList<>();
		if(!ListUtils.isEmpty(queryHandels)) {
			for(DataBatchCollectQueryHandel query:queryHandels) {
				if(query!=null) {
					datas.addAll(query.queryByBatch(taskContent.getTaskId()));
				}
			}
		}
		return datas;
	}
	
	/**
	 * 根据class获取全局rpc调用方法
	 * @param clazz
	 * @return
	 * @author 王帆
	 * @date 2019年7月26日 上午9:29:55
	 */
	private <T> List<T> getAllClass(Class<T> clazz) {
		List<nodeData> nodes = RpcConfig.getRcpNodeDatas(baseUrl+"/"+clazz.getName());
		List<T> list=new LinkedList<T>();
		for(nodeData n:nodes) {
			list.add(RpcProxy.getRemoteProxyObj(clazz, new InetSocketAddress(n.getIp(), n.getPort())));
		}
		return list;
	}
	
	
	@Override
	protected List<BatchDataFunctionHandel> getDataCountHandel() {
		return getAllTaskClass(BatchDataFunctionHandel.class);
	}

	@Override
	protected void setExceptionBataData(DataBatchBean data) {
		Class<BatchDataCollectSaveOperate> clazz = BatchDataCollectSaveOperate.class;
		nodeData node = RpcConfig.getRandomServer(getBaseRpcPath()+"/"+clazz.getName());
		if(node==null) {
			throw new RuntimeException("未找到任意一个异常数据处理源");
		}
		BatchDataCollectSaveOperate opt = RpcProxy.getRemoteProxyObj(clazz, new InetSocketAddress(node.getIp(), node.getPort()));
		opt.save(data.getTaskId(), 3, data.getTimes()+1, data.getDatas().toJavaList(JSONObject.class));
	}

}
