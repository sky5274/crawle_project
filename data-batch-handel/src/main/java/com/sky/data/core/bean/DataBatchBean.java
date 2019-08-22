package com.sky.data.core.bean;

import java.io.Serializable;

import com.alibaba.fastjson.JSONArray;

/**
 * 数据批次任务对应
 * @author 王帆
 * @date  2019年7月22日 上午9:16:36
 */
public class DataBatchBean implements Serializable{

	/** */
	private static final long serialVersionUID = -2811734354721983474L;
	private String taskId;
	private String ip;
	private int times;
	private String fileName;
	private JSONArray datas;
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public JSONArray getDatas() {
		return datas;
	}
	public void setDatas(JSONArray datas) {
		this.datas = datas;
	}
	public int getTimes() {
		return times;
	}
	public void setTimes(int times) {
		this.times = times;
	}
}
