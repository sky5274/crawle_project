package com.sky.data.bean;

import java.io.Serializable;
import java.util.List;
import org.springframework.util.StringUtils;

/**
 * function bean
 * @author 王帆
 * @date  2019年7月30日 上午9:15:27
 */
public class FunctionBean implements Serializable{
	/***/
	private static final long serialVersionUID = 1L;
	private String fileds;
	private String funcName;
	private String nickName;
	private String function;
	private String groupBy;
	private List<FunctionBean> child;
	
	public FunctionBean() {}
	public FunctionBean(String function) {
		this.setFuncName(funcName);
	}
	
	/**获取function name*/
	public String getFunctionName() {
		if(function!=null) {
			StringBuilder func=new StringBuilder(function);
			if(!StringUtils.isEmpty(groupBy)) {
				String groupbyfield="GROUP BY "+groupBy.toUpperCase().trim();
				int gbfi = function.toUpperCase().lastIndexOf(groupbyfield);
				String funcgroupby = func.substring(gbfi, gbfi+groupbyfield.length());
				func=new StringBuilder(function.replace(funcgroupby, ""));
			}
			func=new StringBuilder(func.toString().replaceAll(" "+nickName, "").replace(" AS", "").replace(" as", "").trim());
			return func.toString();
		}
		return null;
	}
	
	public String getFileds() {
		return fileds;
	}
	public void setFileds(String fileds) {
		this.fileds = fileds;
	}
	public String getFunction() {
		return function;
	}
	public void setFunction(String function) {
		this.function = function;
//		this.setFuncName(function.substring(function.indexOf("(")).trim());
//		this.setFileds(function.substring(function.indexOf("("),function.lastIndexOf("")));
//		this.setGroupBy(function.substring(function.toLowerCase().lastIndexOf("group by")+8));
	}
	public List<FunctionBean> getChild() {
		return child;
	}
	public void setChild(List<FunctionBean> child) {
		this.child = child;
	}
	public String getFuncName() {
		return funcName;
	}
	public void setFuncName(String funcName) {
		this.funcName = funcName;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getGroupBy() {
		return groupBy;
	}
	public void setGroupBy(String groupBy) {
		this.groupBy = groupBy;
	}
}
