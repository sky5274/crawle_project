package com.sky.data.core.factory;

import javax.script.ScriptEngine;
import com.sky.data.core.builder.ScriptEngineBuilder;
import com.sky.data.bean.DataBatchTaskExtEntity;
import com.sky.pub.cache.LocalCacheManager;

/**
 * 
 * @author 王帆
 * @date  2019年8月17日 下午4:51:02
 */
public class ScriptEngineCacheBuilderFactory implements ScriptEngineBuilder{
	private static String splitStr="_";
	
	private static ScriptEngineCacheBuilderFactory factory=null;
	
	public static ScriptEngineCacheBuilderFactory getFactory() {
		if(factory==null) {
			factory=new ScriptEngineCacheBuilderFactory();
		}
		return factory;
	}
	
	@Override
	public ScriptEngine build(DataBatchTaskExtEntity task) throws Exception {
		String key=getKey(task);
		ScriptEngine script=LocalCacheManager.get(key);
		if(script==null) {
			script=ScriptEngineBuilderFactory.newInstence().build(task);
			LocalCacheManager.put(key, script);
		}
		return script;
	}
	
	private String getKey(DataBatchTaskExtEntity task) {
		StringBuilder str=new StringBuilder();
		str.append(task.getTaskId()).append(splitStr)
			.append(task.getFuntionJs()).append(splitStr)
			.append(task.getFilterJs()).append(splitStr)
			.append(task.getConvertJs());
		return str.toString();
	}

}
