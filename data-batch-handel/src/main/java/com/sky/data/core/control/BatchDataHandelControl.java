package com.sky.data.core.control;

import java.io.Serializable;

import com.sky.data.bean.DataBatchTaskExtEntity;
import com.sky.data.core.handel.AbstractDataOperatHandel;
import com.sky.data.core.handel.MultipleDataOperatHandel;
import com.sky.data.core.handel.SingleDataOperatHandel;
import com.sky.data.dao.DataBatchTaskMapper;
import com.sky.pub.util.SpringUtil;

/**
 * batch data core controller
 * @author 王帆
 * @date  2019年7月26日 上午8:55:02
 */
public class BatchDataHandelControl implements Serializable{
	/***/
	private static final long serialVersionUID = 5154183892982998363L;
	private String batchId;
	private String groupId;
	protected DataBatchTaskExtEntity dataBatchTask;
	private AbstractDataOperatHandel dataOperateHandel;
	
	public BatchDataHandelControl(String batchId, String groupId, DataBatchTaskExtEntity ext) {
		this.batchId=batchId;
		this.groupId=groupId;
		this.dataBatchTask=ext;
	}

	/**
	 * 开始数据处理
	 * @author 王帆
	 * @throws Exception 
	 * @date 2019年7月26日 上午8:59:24
	 */
	public void start() throws Exception {
		//初始批处理任务环境
		initBatchTask();
		if(dataBatchTask==null || dataBatchTask.getType()==0) {
			//处理本地数据
			dataOperateHandel=new SingleDataOperatHandel(groupId,dataBatchTask);
		}else if(dataBatchTask.getType()==1) {
			//处理所有节点数据
			dataOperateHandel=new MultipleDataOperatHandel(batchId,groupId,dataBatchTask);
		}
		if(dataOperateHandel!=null) {
			dataOperateHandel.handelData();
		}
	}
	
	
	
	private void initBatchTask() throws Exception {
		DataBatchTaskMapper dataBatchTaskMapper=SpringUtil.getBean(DataBatchTaskMapper.class);
		dataBatchTask = dataBatchTaskMapper.queryExtByTask(batchId);
		if(dataBatchTask==null) {
			dataBatchTask=new DataBatchTaskExtEntity();
			dataBatchTask.setTaskId(batchId);
		}
	}
	

	
	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	public String getGtoupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
}
