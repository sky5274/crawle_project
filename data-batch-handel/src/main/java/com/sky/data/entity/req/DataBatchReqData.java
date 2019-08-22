package com.sky.data.entity.req;

import com.sky.pub.BasePageRequest;

public class DataBatchReqData extends BasePageRequest{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String groupId;
	private String taskId;
	private String taskName;
	/**-1:作废；0：创建，1：运行中;2:停止*/
	private Byte status;
	/** 运行模式：0：单次；1：循环调度*/
	private Byte runType;
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public Byte getStatus() {
		return status;
	}
	public void setStatus(Byte status) {
		this.status = status;
	}
	public Byte getRunType() {
		return runType;
	}
	public void setRunType(Byte runType) {
		this.runType = runType;
	}
}
