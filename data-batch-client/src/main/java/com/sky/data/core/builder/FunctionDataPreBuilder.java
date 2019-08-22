package com.sky.data.core.builder;

import com.sky.data.bean.DataBatchTaskExtEntity;
import com.sky.data.bean.FunctionDataPreBean;

/**
 * 计算公式准备建造器
 * @author 王帆
 * @date  2019年8月17日 下午3:10:55
 */
public interface FunctionDataPreBuilder {
	
	/**
	 * 根据批处理任务信息  构建批处理公式准备数据
	 * @param task
	 * @return
	 * @throws Exception
	 * @author 王帆
	 * @date 2019年8月17日 下午4:56:04
	 */
	public  FunctionDataPreBean build(DataBatchTaskExtEntity task) throws Exception;
}
