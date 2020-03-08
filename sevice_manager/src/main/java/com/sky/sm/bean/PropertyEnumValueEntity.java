package com.sky.sm.bean;

import com.sky.pub.BaseTableEntity;

/**
 * 字典枚举键值
 * @author 王帆
 * @date  2019年3月8日 下午2:14:45
 */
public class PropertyEnumValueEntity extends BaseTableEntity{
	/***/
	private static final long serialVersionUID = 1L;
	private String enumNo;
	private String enumName;
	private Integer groupId;
	private String local;
	public String getEnumNo() {
		return enumNo;
	}
	public void setEnumNo(String enumNo) {
		this.enumNo = enumNo;
	}
	public String getEnumName() {
		return enumName;
	}
	public void setEnumName(String enumName) {
		this.enumName = enumName;
	}
	public Integer getGroupId() {
		return groupId;
	}
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
	public String getLocal() {
		return local;
	}
	public void setLocal(String local) {
		this.local = local;
	}
}
