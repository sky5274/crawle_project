package com.sky.data.core.factory;

import javax.script.ScriptEngine;
import org.springframework.util.StringUtils;
import com.sky.data.bean.DataBatchTaskExtEntity;
import com.sky.data.core.builder.ScriptEngineBuilder;
import com.sky.pub.util.ScriptUtil;

/**
 *   java script builder factory
 * @author 王帆
 * @date  2019年8月17日 下午3:18:07
 */
public class ScriptEngineBuilderFactory implements ScriptEngineBuilder{
	
	public static ScriptEngineBuilderFactory newInstence() {
		return new ScriptEngineBuilderFactory();
	}

	@Override
	public ScriptEngine build(DataBatchTaskExtEntity task) throws Exception {
		ScriptEngine engine = ScriptUtil.getScriptEngine();
		if(task !=null) {
			if(!StringUtils.isEmpty(task.getConvertJs())) {
				engine =ScriptUtil.loadJs(engine, task.getConvertJs());
			}
			if(!StringUtils.isEmpty(task.getFilterJs())) {
				engine =ScriptUtil.loadJs(engine, task.getFilterJs());
			}
			if(!StringUtils.isEmpty(task.getFuntionJs())) {
				engine =ScriptUtil.loadJs(engine, task.getFilterJs());
			}
		}
		return engine;
	}

}
