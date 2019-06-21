package com.sky.crawler.engine;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.util.concurrent.TimeUnit;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleScriptContext;
import org.springframework.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.sky.crawler.core.CrawlerJsContant;
import com.sky.crawler.core.CrawlerUrlDatum;
import com.sky.pub.ResultCode;
import com.sky.pub.common.exception.ResultException;
import com.sky.pub.contant.PubFileContant;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * json  爬虫  js适应器
 * @author 王帆
 * @date  2019年1月24日 上午10:14:19
 */
@SuppressWarnings("restriction")
public class JsonParseJsCrawler extends BaseJsonCrawlerBreath{
	private String initJsPath=CrawlerJsContant.initJs;
	private String jsContents=null;
	private ScriptEngine engine;
	private SqlExcuteMapper sqlExcuteMapper;
	
	public JsonParseJsCrawler() throws ResultException {
		super();
		initJsEngine();
	}
	public JsonParseJsCrawler(Writer writer) throws ResultException {
		super(writer);
		initJsEngine();
	}
	public JsonParseJsCrawler(String initJsPath) throws ResultException {
		super();
		this.initJsPath=initJsPath;
		initJsEngine();
	}

	public void initJsEngine() throws ResultException  {
		loadJsFile(initJsPath);
	}
	
	
	public void loadJsFile(String path) throws ResultException {
		if(engine==null) {
			engine = new ScriptEngineManager().getEngineByName("javascript");
			engine.setContext(getJsContext());
		}
		try {
			if(path.matches(PubFileContant.urlRegex)) {
				engine.eval(loadRemoteFile(path));
			}else {
				engine.eval(new InputStreamReader(PubFileContant.getFilePathStream(path)));
			}
		} catch ( ScriptException | IOException e) {
			engine=null;
			throw new ResultException(ResultCode.FAILED,"初始化js引擎失败",e);
		}
	}

	/**
	 * 	获取远程文件内容
	 * @param path
	 * @return
	 * @author 王帆
	 * @throws IOException 
	 * @date 2019年1月20日 下午10:15:11
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
	
	public void loadjs(String jsContents ) throws ResultException {
		this.jsContents=jsContents;
	}
	public void loadjsFile(String...jsFiles ) throws ResultException {
			if(jsFiles!=null) {
				for(String js:jsFiles) {
					if(!StringUtils.isEmpty(js)) {
						loadJsFile(js);
					}
				}
			}
	}

	@Override
	public void crawler(JSONObject data, CrawlerUrlDatum datum) {
		Invocable invocable = (Invocable) engine;
		try {
			engine.put("this", this);
			engine.put("sql", sqlExcuteMapper);
			engine.put("results", data);
			engine.put("url", datum.url());
			if(!StringUtils.isEmpty(jsContents)) {
				engine.eval(jsContents);
			}
			try {
				invocable.invokeFunction("crawler", data,this);	
			} catch (Exception e) {
			}
		} catch ( ScriptException e) {
			e.printStackTrace();
		}
		
	}
	
	private SimpleScriptContext getJsContext() {
		SimpleScriptContext context=new SimpleScriptContext();
		if(super.getWriter()!=null) {
			context.setWriter(super.getWriter());
		}
		return context;
	}
	public SqlExcuteMapper getSqlExcuteMapper() {
		return sqlExcuteMapper;
	}
	public JsonParseJsCrawler setSqlExcuteMapper(SqlExcuteMapper sqlExcuteMapper) {
		this.sqlExcuteMapper = sqlExcuteMapper;
		return this;
	}

}
