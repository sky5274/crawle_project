package com.sky.task.entity;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.validator.constraints.NotBlank;

import com.sky.pub.BaseTableEntity;

/**
 * 	任务定义
 * @author 王帆
 * @date  2019年2月2日 下午8:01:03
 */
public class JobTaskEntity extends BaseTableEntity{
	public static final String STATUS_RUNNING = "1"; // 正在运行

	public static final String STATUS_NOT_RUNNING = "0"; // 已停止

	public static final String CONCURRENT_IS = "1";// 异步，可多个同时执行

	public static final String CONCURRENT_NOT = "0";// 同步，需排队执行

	public static final String STATUS_JOB = "1"; // 有效运行

	public static final String STATUS_NOT_JOB = "0"; // 无效不运行

	private static Map<Byte, String> statusMap=new HashMap<>();
	private static Map<Byte, String> runTypeMap=new HashMap<>();
	private static Map<Byte, String> scheduleModelMap=new HashMap<>();
	static {
		statusMap.put((byte)-1, "作废");
		statusMap.put((byte)0, "停止");
		statusMap.put((byte)1, "暂停");
		statusMap.put((byte)2, "运行中");
		runTypeMap.put((byte)0, "单次执行");
		runTypeMap.put((byte)1, "循环执行");
		scheduleModelMap.put((byte)0, "单点调用");
		scheduleModelMap.put((byte)1, "多点调用");
	}

	/***/
	private static final long serialVersionUID = -63659517800512839L;
	@NotBlank(message="缺少任务所属分组")
	private String groupId;
	private String taskId;
	@NotBlank(message="缺少任务名称")
	private String taskName;
	private String isConcurrent;
	private String targetClass;
	private String cronExpression;
	private String jsonParams;
	/**-1:作废；0：创建，1：运行中;2:停止*/
	private Byte status;
	/** 运行模式：0：单次；1：循环调度*/
	private Byte runType;
	/** 调度模式：0：单点调用，1：多点调用*/
	private Byte scheduleModel;
	private String limitTargetNode;
	private Integer runTimes;
	private Integer runErrTimes;
	private String runErrMsg;
	private String remark;
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getTargetClass() {
		return targetClass;
	}
	public void setTargetClass(String targetClass) {
		this.targetClass = targetClass;
	}
	public String getCronExpression() {
		return cronExpression;
	}
	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}
	public String getJsonParams() {
		return jsonParams;
	}
	public void setJsonParams(String jsonParams) {
		this.jsonParams = jsonParams;
	}
	public Byte getStatus() {
		return status;
	}
	public void setStatus(Byte status) {
		this.status = status;
	}
	public String getStatusName() {
		return statusMap.get(status);
	}
	public Byte getRunType() {
		return runType;
	}
	public void setRunType(Byte runType) {
		this.runType = runType;
	}
	public String getRunTypeName() {
		return runTypeMap.get(runType);
	}
	public Byte getScheduleModel() {
		return scheduleModel;
	}
	public void setScheduleModel(Byte scheduleModel) {
		this.scheduleModel = scheduleModel;
	}
	public String getScheduleModelName() {
		return scheduleModelMap.get(scheduleModel);
	}
	public Integer getRunTimes() {
		return runTimes;
	}
	public void setRunTimes(Integer runTimes) {
		this.runTimes = runTimes;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getIsConcurrent() {
		return isConcurrent;
	}
	public void setIsConcurrent(String isConcurrent) {
		this.isConcurrent = isConcurrent;
	}
	public Integer getRunErrTimes() {
		return runErrTimes;
	}
	public void setRunErrTimes(Integer runErrTimes) {
		this.runErrTimes = runErrTimes;
	}
	public String getRunErrMsg() {
		return runErrMsg;
	}
	public void setRunErrMsg(String runErrMsg) {
		this.runErrMsg = runErrMsg;
	}
	public String getLimitTargetNode() {
		return limitTargetNode;
	}
	public void setLimitTargetNode(String limitTargetNode) {
		this.limitTargetNode = limitTargetNode;
	}
}