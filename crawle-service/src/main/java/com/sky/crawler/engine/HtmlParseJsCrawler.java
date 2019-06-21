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
import com.alibaba.fastjson.JSON;
import com.sky.crawler.core.CrawlerJsContant;
import com.sky.pub.ResultCode;
import com.sky.pub.common.exception.ResultException;
import com.sky.pub.contant.PubFileContant;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * html页面爬虫
 * @author 王帆
 * @date  2019年1月18日 下午1:35:48
 */
@SuppressWarnings("restriction")
public class HtmlParseJsCrawler extends BaseHTMLCrawlerBreadth{
	private String initJsPath=CrawlerJsContant.initJs;
	private String jsContents=null;
	private ScriptEngine engine;
	private SqlExcuteMapper sqlExcuteMapper;
	
	
	public HtmlParseJsCrawler(String crawlPath, boolean autoParse) throws ResultException  {
		this(crawlPath, autoParse,CrawlerJsContant.initJs,null);
	}
	public HtmlParseJsCrawler(String crawlPath, boolean autoParse,Writer writer) throws ResultException  {
		this(crawlPath, autoParse,CrawlerJsContant.initJs,writer);
	}
	public HtmlParseJsCrawler(String crawlPath, boolean autoParse,String jsInitPath) throws ResultException  {
		this(crawlPath, autoParse,jsInitPath,null);
	}
	public HtmlParseJsCrawler(String crawlPath, boolean autoParse,String jsInitPath,Writer writer) throws ResultException  {
		super(crawlPath, autoParse,writer);
		this.initJsPath=jsInitPath;
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

	/**
	 * 当一个页面被提取并准备好被你的程序处理时，这个函数被调用。
	 */
	@Override
	public void visit(Page page, CrawlDatums next) {
		Invocable invocable = (Invocable) engine;
		try {
			engine.put("this", this);
			engine.put("sql", sqlExcuteMapper);
			engine.put("page", page);
			engine.put("url", page.url());
			engine.put("document", page.doc());
			if(page.contentType().contains("application/json")) {
				engine.put("json", JSON.parse(page.html()));
			}else {
				engine.put("html", page.html());
			}
			if(!StringUtils.isEmpty(jsContents)) {
				engine.eval(jsContents);
			}
			try {
				invocable.invokeFunction("crawler", page.url(),page,this);	
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
	public HtmlParseJsCrawler setSqlExcuteMapper(SqlExcuteMapper sqlExcuteMapper) {
		this.sqlExcuteMapper = sqlExcuteMapper;
		return this;
	}
}
