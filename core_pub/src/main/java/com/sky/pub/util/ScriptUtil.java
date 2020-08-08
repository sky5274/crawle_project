package com.sky.pub.util;

import java.io.InputStreamReader;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import com.sky.pub.contant.PubFileContant;
import jdk.nashorn.api.scripting.ScriptObjectMirror;

/**
 * java script util
 * @author 王帆
 * @date  2019年8月13日 下午3:30:41
 */
public class ScriptUtil {
	
	public static ScriptEngine getScriptEngine() {
		return getScriptEngine("javascript");
	}
	public static ScriptEngine getScriptEngine(String name) {
		return new ScriptEngineManager().getEngineByName(name);
	}
	
	public static ScriptEngine loadJs(ScriptEngine engine,String js) throws Exception  {
		try {
			if(js.matches(PubFileContant.urlRegex)) {
				engine.eval(new InputStreamReader(PubFileContant.getFilePathStream(js)));
			}else {
				engine.eval(js);
			}
		} catch (ScriptException e) {
			throw new Exception("javascript engine load js fialed！js: "+js, e);
		}
		return engine;
	}
	
	/**
	 *	 执行script引擎方法
	 * @param engine
	 * @param functionName
	 * @param args
	 * @return
	 * @throws ScriptException
	 * @author 王帆
	 * @date 2019年8月21日 下午2:59:06
	 */
	@SuppressWarnings("unchecked")
	public static <T> T invoke(ScriptEngine engine,String functionName,Object... args) throws ScriptException {
		Invocable invocable = (Invocable) engine;
		T res=null;
		try {
			res=(T)invocable.invokeFunction(functionName, args);
		} catch (NoSuchMethodException | ScriptException e) {
			ScriptObjectMirror functionScript = getScriptFunction(engine,functionName);
			if(functionScript!=null) {
				res= (T) functionScript.call(null, args);
			}
		}
		return res;
	}
	
	/**
	 * 获取script  引擎中的方法对象
	 * @param engine
	 * @param functionName
	 * @return
	 * @throws ScriptException
	 * @author 王帆
	 * @date 2019年8月13日 下午3:43:20
	 */
	public static ScriptObjectMirror getScriptFunction(ScriptEngine engine,String functionName) throws ScriptException {
		Object script = engine.eval(functionName);
		return getScript(script);
	}
	
	/**
	 * script  是否含有方法
	 * @param engine
	 * @param functionName
	 * @return
	 * @author 王帆
	 * @date 2019年8月13日 下午3:45:50
	 */
	public static boolean hasScriptFunction(ScriptEngine engine,String functionName)  {
		try {
			Object script = engine.eval(functionName);
			return script!=null;
		} catch (Exception e) {
		}
		return false;
	}
	
	/**
	 *	 转换js方法系统对象
	 * @param script
	 * @return
	 * @author 王帆
	 * @date 2019年8月13日 下午3:41:42
	 */
	private static ScriptObjectMirror getScript(Object script) {
		if(script instanceof ScriptObjectMirror) {
			return (ScriptObjectMirror)script;
		}
		return null;
	}
	
}
