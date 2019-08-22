package com.sky.data.entity;


import java.util.HashMap;
import java.util.Map;

import com.sky.pub.BaseTableEntity;

/**
 * 	任务分组
 * @author 王帆
 * @date  2019年2月2日 下午8:15:20
 */
public class BatchTaskGroupEntity extends BaseTableEntity{
	private static Map<Integer, String> statusMap=new HashMap<>();
	static {
		statusMap.put(-1, "作废");
		statusMap.put(0, "正常");
	}

	/***/
	private static final long serialVersionUID = -8109392535526679785L;
	private String groupId;
	private String groupName;
	/**-1:作废，0：正常*/
	private Byte status;
	private String remark;
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public Byte getStatus() {
		return status;
	}
	public void setStatus(Byte status) {
		this.status = status;
	}
	@SuppressWarnings("unlikely-arg-type")
	public String getStatusName() {
		return statusMap.get(getStatus());
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}