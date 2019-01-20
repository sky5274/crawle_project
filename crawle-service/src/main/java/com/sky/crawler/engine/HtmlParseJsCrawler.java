package com.sky.crawler.engine;

import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;
import java.util.concurrent.TimeUnit;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleScriptContext;
import org.springframework.util.StringUtils;
import com.sky.pub.ResultCode;
import com.sky.pub.common.exception.ResultException;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * html页面爬虫
 * @author 王帆
 * @date  2019年1月18日 下午1:35:48
 */
public class HtmlParseJsCrawler extends BreadthCrawler{
	private String initJsPath="/js/init.evn.js";
	private String jsContents=null;
	private ScriptEngine engine;
	private Writer writer;
	private String urlRegex=" /^((ht|f)tps?):\\/\\/([\\w\\-]+(\\.[\\w\\-]+)*\\/)*[\\w\\-]+(\\.[\\w\\-]+)*\\/?(\\?([\\w\\-\\.,@?^=%&:\\/~\\+#]*)+)?/";
	
	
	public HtmlParseJsCrawler(String crawlPath, boolean autoParse) throws ResultException  {
		super(crawlPath, autoParse);
		initJsEngine();
	};
	public HtmlParseJsCrawler(String crawlPath, boolean autoParse,Writer writer) throws ResultException  {
		super(crawlPath, autoParse);
		this.writer=writer;
		initJsEngine();
	};
	public HtmlParseJsCrawler(String crawlPath, boolean autoParse,String jsInitPath,Writer writer) throws ResultException  {
		super(crawlPath, autoParse);
		this.initJsPath=jsInitPath;
		this.writer=writer;
		initJsEngine();
	};
	public HtmlParseJsCrawler(String crawlPath, boolean autoParse,String jsInitPath) throws ResultException  {
		super(crawlPath, autoParse);
		this.initJsPath=jsInitPath;
		initJsEngine();
	};

	public void initJsEngine() throws ResultException  {
		loadJsFile(initJsPath);
	}
	
	public void loadJsFile(String path) throws ResultException {
		if(engine==null) {
			engine = new ScriptEngineManager().getEngineByName("javascript");
			engine.setContext(getJsContext());
		}
		try {
			if(path.matches(urlRegex)) {
				engine.eval(loadRemoteFile(path));
			}else {
				engine.eval(new FileReader(getClass().getResource(path).getFile()));
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
			engine.put("page", page);
			engine.put("url", page.url());
			engine.put("document", page.doc());
			engine.put("html", page.html());
			if(!StringUtils.isEmpty(jsContents)) {
				engine.eval(jsContents);
			}
			try {
				invocable.invokeFunction("crawler", page.url(),page,this);	
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch ( ScriptException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void afterStop() {
		super.afterStop();
		try {
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Writer getWriter() {
		return writer;
	}
	public HtmlParseJsCrawler setWriter(Writer writer) {
		this.writer=writer;
		return this;
	}
	
	private SimpleScriptContext getJsContext() {
		SimpleScriptContext context=new SimpleScriptContext();
		if(writer!=null) {
			context.setWriter(writer);
		}
		return context;
	}
}
