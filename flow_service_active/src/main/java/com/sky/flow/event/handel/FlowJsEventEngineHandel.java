package com.sky.flow.event.handel;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleScriptContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.sky.flow.bean.FlowNodeEventBean;
import com.sky.flow.bean.TaskFlowBean;
import com.sky.flow.bean.TaskFlowNodeBean;
import com.sky.flow.contant.FlowContant;
import com.sky.flow.dao.FlowSqlMapper;
import com.sky.flow.event.FlowEventEngineHandel;
import com.sky.flow.exception.FlowException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 流程事件 js类型的事件执行引擎
 * @author 王帆
 * @date  2019年6月7日 下午5:26:05
 */
@Component
public class FlowJsEventEngineHandel implements FlowEventEngineHandel{
	
	@Autowired
	private FlowSqlMapper flowSqlMapper;
	
	private ScriptEngine engine;
	private String initJsPath="classpath:static/js/init_js_event.js";
	private String excuteFuntionName="excute";


	@Override
	public String type() {
		return "js";
	}

	@Override
	public void invoke(FlowNodeEventBean event, TaskFlowNodeBean node, TaskFlowBean task) throws FlowException {
		String content = event.getContent();
		if(StringUtils.isEmpty(content)) {
			throw new FlowException("流程节点："+node.getNodeKey()+"-"+"事件类型："+event.getEventType()+" js事件无执行js");
		}
		String[] contents = content.split(",");
		loadJsFile(initJsPath);
		engine.put("this", this);
		engine.put("sql", flowSqlMapper);
		engine.put("task", task);
		engine.put("detail", node);
		Invocable invocable = (Invocable) engine;
		boolean hasFunc=false;
		for(String c:contents) {
			try {
				engine.eval(c);
			} catch (ScriptException e) {
			}
			hasFunc=!hasFunc?c.contains(excuteFuntionName):hasFunc;
		}
		if(hasFunc) {
			try {
				invocable.invokeFunction(excuteFuntionName, node,task);
			} catch (NoSuchMethodException | ScriptException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public void loadJsFile(String path) throws FlowException {
		if(engine==null) {
			engine = new ScriptEngineManager().getEngineByName("javascript");
			engine.setContext(getJsContext());
		}
		try {
			if(path.matches(FlowContant.httpRegex)) {
				engine.eval(loadRemoteFile(path));
			}else {
				engine.eval(new InputStreamReader(FlowJsEventEngineHandel.class.getResourceAsStream(path)));
			}
		} catch ( ScriptException | IOException e) {
			engine=null;
			throw new FlowException("初始化js引擎失败",e);
		}
	}
	
	private SimpleScriptContext getJsContext() {
		SimpleScriptContext context=new SimpleScriptContext();
		return context;
	}
	
	/**
	 * 加载远程文件
	 * @param path
	 * @return
	 * @throws IOException
	 * @author 王帆
	 * @date 2019年6月16日 上午9:49:25
	 */
	private String loadRemoteFile(String path) throws IOException {
		OkHttpClient client = new OkHttpClient.Builder()
			    .connectTimeout(60, TimeUnit.SECONDS)
			    .readTimeout(60, TimeUnit.SECONDS)
			    .writeTimeout(60, TimeUnit.SECONDS)
			    .retryOnConnectionFailure(true)
			    .build();
		Request request = new Request.Builder()
			    .url(path)
			    .build();
		Response response = client.newCall(request).execute();
		return response.body().string();
	}
}
