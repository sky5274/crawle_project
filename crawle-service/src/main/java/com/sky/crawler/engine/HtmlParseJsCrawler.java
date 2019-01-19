package com.sky.crawler.engine;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;

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
		}
		try {
			engine.setContext(getJsContext());
			engine.eval(new FileReader(getClass().getResource(path).getFile()));
		} catch (FileNotFoundException | ScriptException e) {
			engine=null;
			throw new ResultException(ResultCode.FAILED,"初始化js引擎失败",e);
		}
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
