package com.sky.data.bean;

import java.io.Serializable;
import java.util.List;
import com.alibaba.fastjson.JSONObject;

/**
 * 任务公式准备数据
 * @author 王帆
 * @date  2019年8月17日 下午12:15:50
 */
public class FunctionDataPreBean implements Serializable{
	/***/
	private static final long serialVersionUID = 1L;
	/**任务编码*/
	private String taskId;
	/**计算公式*/
	private List<FunctionBean> functions;
	/**任务信息*/
	private DataBatchTaskExtEntity ext;
	/**计算数据*/
	private List<JSONObject> datas;
	public List<JSONObject> getDatas() {
		return datas;
	}
	public void setDatas(List<JSONObject> datas) {
		this.datas = datas;
	}
	public List<FunctionBean> getFunctions() {
		return functions;
	}
	public void setFunctions(List<FunctionBean> functions) {
		this.functions = functions;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public DataBatchTaskExtEntity getExt() {
		return ext;
	}
	public void setExt(DataBatchTaskExtEntity ext) {
		this.ext = ext;
	}
	
}
