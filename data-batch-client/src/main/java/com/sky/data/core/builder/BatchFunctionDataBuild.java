package com.sky.data.core.builder;

import org.springframework.util.StringUtils;

import com.sky.data.bean.DataBatchTaskExtEntity;
import com.sky.data.bean.FunctionDataPreBean;
import com.sky.data.factory.FunctionFactory;

/**
 * 批处理任务公式准备数据工厂
 * @author 王帆
 * @date  2019年8月17日 下午12:18:15
 */
public class BatchFunctionDataBuild {
	private String taskId;
	private String function;
	private DataBatchTaskExtEntity ext;
	
	public  BatchFunctionDataBuild function(String function) {
		this.function=function;
		return this;
	}
	public  BatchFunctionDataBuild taskId(String taskId) {
		this.taskId=taskId;
		return this;
	}
	public DataBatchTaskExtEntity getExt() {
		return ext;
	}
	public BatchFunctionDataBuild setExt(DataBatchTaskExtEntity ext) {
		this.ext = ext;
		return this.taskId(ext.getTaskId()).function(ext.getFunctions());
	}
	
	public FunctionDataPreBean build() throws Exception {
		if(StringUtils.isEmpty(function)) {
			throw new Exception("no found the function");
		}
		FunctionDataPreBean fdp=new FunctionDataPreBean();
		fdp.setFunctions(FunctionFactory.bulid(function));
		fdp.setTaskId(taskId);
		fdp.setExt(ext);
		return fdp;
	}
}
