package com.sky.data.core.factory;

import com.sky.data.bean.DataBatchTaskExtEntity;
import com.sky.data.bean.FunctionDataPreBean;
import com.sky.data.core.builder.BatchFunctionDataBuild;
import com.sky.data.core.builder.FunctionDataPreBuilder;

/**
 * function data handel builder factory
 * @author 王帆
 * @date  2019年8月17日 下午4:55:18
 */
public class BatchFunctionDataFactory implements FunctionDataPreBuilder{
	private static  BatchFunctionDataFactory factory=null;
	
	/**
	 * 获取构建工厂
	 * @return
	 * @author 王帆
	 * @date 2019年8月17日 下午4:58:02
	 */
	public static BatchFunctionDataFactory factory() {
		if(factory==null) {
			factory=new BatchFunctionDataFactory();
		}
		return factory;
	}

	@Override
	public  FunctionDataPreBean build(DataBatchTaskExtEntity task) throws Exception {
		BatchFunctionDataBuild builder = new BatchFunctionDataBuild();
		return builder.taskId(task.getTaskId()).function(task.getFunctions()).build();
	}
}
