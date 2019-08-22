package com.sky.data.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * data list count bean
 * @author 王帆
 * @date  2019年8月2日 下午8:21:52
 */
public class FunctionDataBean implements Serializable{
	
	/***/
	private static final long serialVersionUID = 1L;
	private int size;
	/**按公式进行存储*/
	private Map<String,FunctionDataBaseBean> funcData;
	private Map<String, Map<String, FunctionDataBaseBean>> groupByFunction;
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	
	public void put(String func,FunctionDataBaseBean value) {
		if(funcData==null) {
			funcData=new HashMap<String, FunctionDataBaseBean>(16);
		}
		funcData.put(func, value);
	}
	public void put(String func,String key,FunctionDataBaseBean value) {
		if(groupByFunction==null) {
			groupByFunction=new HashMap<>(16);
		}
		Map<String, FunctionDataBaseBean> gf = groupByFunction.get(func);
		if(gf==null) {
			gf=new HashMap<>();
		}
		gf.put(key, value);
		groupByFunction.put(func, gf);
	}
	
	
	public Map<String, FunctionDataBaseBean> getFuncData() {
		return funcData;
	}
	public void setFuncData(Map<String, FunctionDataBaseBean> funcData) {
		this.funcData = funcData;
	}
	public Map<String, Map<String, FunctionDataBaseBean>> getGroupByFunction() {
		return groupByFunction;
	}
	public void setGroupByFunction(Map<String, Map<String, FunctionDataBaseBean>> groupByFunction) {
		this.groupByFunction = groupByFunction;
	}
}
