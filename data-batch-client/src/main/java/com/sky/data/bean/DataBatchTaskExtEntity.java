package com.sky.data.bean;

import java.io.Serializable;

/**
 * batch data task ext 
 * 
 * @author 王帆
 * @date  2019年7月27日 上午9:45:55
 */
public class DataBatchTaskExtEntity implements Serializable{

	/***/
	private static final long serialVersionUID = -58215642920863940L;
	private Integer id;
	private String taskId;
	private int type;
	private String functions;	//公式
	private Integer splitSize;	//数据分割数
	private String funtionJs;	//额外公式
	private String convertJs;	//数据转换js
	private String filterJs;	//数据过滤js
	public Integer getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public Integer getSplitSize() {
		return splitSize;
	}
	public void setSplitSize(int splitSize) {
		this.splitSize = splitSize;
	}
	public String getConvertJs() {
		return convertJs;
	}
	public void setConvertJs(String convertJs) {
		this.convertJs = convertJs;
	}
	public String getFilterJs() {
		return filterJs;
	}
	public void setFilterJs(String filterJs) {
		this.filterJs = filterJs;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getFunctions() {
		return functions;
	}
	public void setFunctions(String functions) {
		this.functions = functions;
	}
	public String getFuntionJs() {
		return funtionJs;
	}
	public void setFuntionJs(String funtionJs) {
		this.funtionJs = funtionJs;
	}
}
