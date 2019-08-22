package com.sky.data.core.handel;

import java.util.Arrays;
import java.util.List;
import org.springframework.util.CollectionUtils;
import com.alibaba.fastjson.JSONObject;
import com.sky.data.bean.DataBatchTaskExtEntity;
import com.sky.data.core.bean.DataBatchBean;
import com.sky.data.handel.BatchDataFunctionHandel;
import com.sky.data.handel.impl.BatchDataFunctionHandelImpl;

/**
 * 单一本地数据处理handel
 * @author 王帆
 * @date  2019年8月3日 下午3:00:44
 */
public class SingleDataOperatHandel extends AbstractDataOperatHandel{

	public SingleDataOperatHandel(String groupId,DataBatchTaskExtEntity taskExt) throws Exception {
		super(groupId,taskExt);
	}

	@Override
	protected List<BatchDataFunctionHandel> getDataCountHandel() {
		return Arrays.asList(new BatchDataFunctionHandelImpl());
	}

	@Override
	protected void setExceptionBataData(DataBatchBean data) {
		BatchDataCollectSaveOperate dataSaveOperate=new BatchDataCollectSaveOperate();
		if(CollectionUtils.isEmpty(data.getDatas())) {
			//删除并替换数据
			dataSaveOperate.deleteData(data.getTaskId(), data.getFileName());
			dataSaveOperate.save(data.getTaskId(), 3, data.getTimes()+1, data.getDatas().toJavaList(JSONObject.class));
		}
		
	}

	/**
	 * 获取本地任务含有的数据
	 */
	@Override
	protected List<DataBatchBean> getAllBatchDatas() {
		DataBatchCollectQueryHandel query=new DataBatchCollectQueryHandel();
		return query.queryByBatch(super.taskContent.getTaskId());
	}

}
