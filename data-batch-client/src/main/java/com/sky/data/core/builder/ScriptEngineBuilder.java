package com.sky.data.core.builder;

import javax.script.ScriptEngine;
import com.sky.data.bean.DataBatchTaskExtEntity;


/**
 * java script engine builder
 * @author 王帆
 * @date  2019年8月17日 下午3:01:20
 */
public interface ScriptEngineBuilder {
	
	/**
	 * 构建java sript engine
	 * @param task
	 * @return
	 * @author 王帆
	 * @throws Exception 
	 * @date 2019年8月17日 下午3:13:45
	 */
	public  ScriptEngine build(DataBatchTaskExtEntity task) throws Exception;
}
