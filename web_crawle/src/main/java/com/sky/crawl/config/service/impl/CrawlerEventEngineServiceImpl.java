package com.sky.crawl.config.service.impl;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.sky.crawl.config.service.CrawlerEventEngineService;
import com.sky.crawl.data.config.dao.ConfigLogFileMapper;
import com.sky.crawl.data.config.dao.ConfigSqlExcuteMapper;
import com.sky.crawl.data.config.dao.entity.ConfigFileDefined;
import com.sky.crawl.data.config.dao.entity.CrawlerConfigEntity;
import com.sky.crawl.data.config.dao.entity.CrawlerConfigFilterEntity;
import com.sky.crawl.data.config.dao.entity.CrawlerConfigUrlEntity;
import com.sky.crawl.util.CrawlerHttpEventWriter;
import com.sky.crawler.core.CrawlerUrlDatum;
import com.sky.crawler.engine.BaseJsonCrawlerBreath;
import com.sky.crawler.engine.HtmlParseClassNameCrawler;
import com.sky.crawler.engine.HtmlParseJsCrawler;
import com.sky.crawler.engine.JsonParseClassNameCrawler;
import com.sky.crawler.engine.JsonParseJsCrawler;
import com.sky.crawler.engine.SqlExcuteMapper;
import com.sky.pub.ResultCode;
import com.sky.pub.common.exception.ResultException;
import com.sky.pub.thread.PubThreadPool;

import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;

@Service
public class CrawlerEventEngineServiceImpl implements CrawlerEventEngineService{

	@Resource
	private ConfigLogFileMapper configLogFileMapper;
	@Resource
	private ConfigSqlExcuteMapper configSqlExcuteMapper;
	
	private List<String> typeList=Arrays.asList("html","json");
	
	private Log log=LogFactory.getLog(getClass());
	
	@Override
	public ConfigFileDefined excute(CrawlerConfigEntity config) throws Exception {
		ConfigFileDefined fileDefined=null;
		try {
			validCrawlerConfigType(config);
			fileDefined=new ConfigFileDefined(config);
			configLogFileMapper.insert(fileDefined);
			log.info("crawler config type :"+config.getType().toLowerCase());
			switch (config.getType().toLowerCase()) {
			case "html":
				excuteHtmlCrawler(config,fileDefined);
				break;
			case "json":
				excuteJSONCrawler(config,fileDefined);
				break;

			default:
				break;
			}
			return fileDefined;
		} catch (Exception e) {
			if(fileDefined !=null) {
				fileDefined.setStatus(4);
				configLogFileMapper.updateByPrimaryKeySelective(fileDefined);
			}
			throw e;
		}
	}


	/**
	 *  	执行jsonhttp接口爬虫计划
	 * @param config
	 * @param fileDefined
	 * @author 王帆
	 * @throws IOException 
	 * @throws ResultException 
	 * @date 2019年1月21日 下午4:39:11
	 */
	private void excuteJSONCrawler(CrawlerConfigEntity config, ConfigFileDefined fileDefined) throws ResultException, IOException {
		final Integer id = fileDefined.getId();
		BaseJsonCrawlerBreath jsonCrawler=getJsonCrawler(config,fileDefined);
		PubThreadPool.excute(PubThreadPool.getCommonThreadExcutor(), ()->{
			try {
				jsonCrawler.start();
			} catch (Exception e) {
				e.printStackTrace();
				//出现异常系统更新  临时任务状态（终止）
				ConfigFileDefined temp=new ConfigFileDefined();
				temp.setId(id);
				temp.setStatus(4);
				configLogFileMapper.updateByPrimaryKeySelective(temp);
			}
		});
		
	}


	/**
	 * 	执行html爬虫
	 * @param config
	 * @param fileDefined
	 * @author 王帆
	 * @throws IOException 
	 * @throws ResultException 
	 * @date 2019年1月21日 下午4:37:10
	 */
	private void excuteHtmlCrawler(CrawlerConfigEntity config, ConfigFileDefined fileDefined) throws ResultException, IOException {
		final Integer id = fileDefined.getId();
		BreadthCrawler crawler=	getCrawler(config,fileDefined);
		PubThreadPool.excute(PubThreadPool.getCommonThreadExcutor(), ()->{
			try {
				crawler.start(config.getDepth());
			} catch (Exception e) {
				e.printStackTrace();
				//出现异常系统更新  临时任务状态（终止）
				ConfigFileDefined temp=new ConfigFileDefined();
				temp.setId(id);
				temp.setStatus(4);
				configLogFileMapper.updateByPrimaryKeySelective(temp);
			}
		});
	}
	
	/**
	 *	校验爬虫配置类型
	 * @param config
	 * @throws ResultException
	 * @author 王帆
	 * @date 2019年1月18日 下午4:36:04
	 */
	private void validCrawlerConfigType(CrawlerConfigEntity config) throws ResultException {
		if(StringUtils.isEmpty(config.getType())) {
			throw new ResultException(ResultCode.FAILED,"请确认爬取类型:"+typeList);
		}
		boolean flag=true;
		for(String type:typeList) {
			if(type.equals(config.getType().toLowerCase())) {
				flag=false;
				break;
			}
		}
		if(flag) {
			throw new ResultException(ResultCode.FAILED,"爬虫配置类型:"+config.getType()+"  不在默认范围内："+typeList);
		}
	}
	
	/**
	 * 设置页面爬虫
	 * @param config
	 * @return
	 * @throws ResultException
	 * @author 王帆
	 * @param fileDefined 
	 * @throws IOException 
	 * @date 2019年1月19日 下午1:31:54
	 */
	public BreadthCrawler getCrawler(CrawlerConfigEntity config, ConfigFileDefined fileDefined) throws ResultException, IOException {
		BreadthCrawler crawler=null;
		Map<String, List<String>> urlMap=new HashMap<>();
		if(!StringUtils.isEmpty(config.getClassName())) {
			crawler= new HtmlParseClassNameCrawler(config.getCrawlPath(), true, config.getClassName(),new CrawlerHttpEventWriter(fileDefined, configLogFileMapper));
		}else  if(!StringUtils.isEmpty(config.getJs()) || !StringUtils.isEmpty(config.getJsContent())){
			HtmlParseJsCrawler crawlerTemp = new HtmlParseJsCrawler(config.getCrawlPath(), true,new CrawlerHttpEventWriter(fileDefined, configLogFileMapper));
			if(!StringUtils.isEmpty(config.getJs())) {
				crawlerTemp.loadJsFile(config.getJs());
			}else if(!StringUtils.isEmpty(config.getJsContent())) {
				crawlerTemp.loadjs(config.getJsContent());
			}
			crawlerTemp.setSqlExcuteMapper(new SqlExcuteMapper() {
				
				@Override
				public Map<String, Object> queryOne(String sql) {
					List<Map<String, Object>> list = queryList(sql);
					return list==null || list.isEmpty() ?null:list.get(0);
				}
				
				@Override
				public List<Map<String, Object>> queryList(String sql) {
					return configSqlExcuteMapper.queryBySql(sql);
				}
				
				@Override
				public int excute(String sql) {
					return configSqlExcuteMapper.excuteBySql(sql);
				}
			});
			crawler=crawlerTemp;
		}else {
			throw new ResultException(ResultCode.FAILED,"爬虫配置信息错误,确认调用class或js");
		}
		if(config.getUrls()!=null && config.getUrls().size()>0) {
			for(CrawlerConfigUrlEntity url:config.getUrls()) {
				if(!StringUtils.isEmpty(url.getUrl())) {
					List<String> urls = url.bulidUrl();
					urlMap=analyzeUrls(urls,urlMap);
					crawler.addSeed(urls);
				}
			}
		}else {
			throw new ResultException(ResultCode.FAILED,"爬虫配置信息错误,请添加要爬取的url路径");
		}
		Set<String> regurls = urlMap.keySet();
		for(String url:regurls) {
			crawler.addRegex(url+".*");
		}
		if(config.getFilters()!=null) {
			for(CrawlerConfigFilterEntity filter:config.getFilters()) {
				if(!StringUtils.isEmpty(filter.getValue())) {
					crawler.addRegex(filter.getValue());
				}
			}
		}
		crawler.setThreads(config.getThreads());
		crawler.getConf().setTopN(config.getTopN());
		return crawler;
	}

	/**
	 * 	分析url域名分布
	 * @param urls
	 * @param urlMap
	 * @return
	 * @author 王帆
	 * @date 2019年1月21日 下午2:30:12
	 */
	private Map<String, List<String>> analyzeUrls(List<String> urls, Map<String, List<String>> urlMap) {
		for(String u:urls) {
			String ukey=getUrlOrginPath(u);
			if(!StringUtils.isEmpty(ukey)) {
				List<String> temps = urlMap.get(ukey);
				if(temps==null) {
					temps=new LinkedList<>();
				}
				temps.add(u);
				urlMap.put(ukey, temps);
			}
		}
		return urlMap;
	}
	
	/**
	 * 	获取json爬取配置
	 * @param config
	 * @param fileDefined
	 * @return
	 * @author 王帆
	 * @throws ResultException 
	 * @throws IOException 
	 * @date 2019年1月24日 下午1:08:20
	 */
	private BaseJsonCrawlerBreath getJsonCrawler(CrawlerConfigEntity config, ConfigFileDefined fileDefined) throws ResultException, IOException {
		BaseJsonCrawlerBreath crawler=null;
		if(!StringUtils.isEmpty(config.getClassName())) {
			crawler= new JsonParseClassNameCrawler(config.getClassName(),new CrawlerHttpEventWriter(fileDefined, configLogFileMapper));
		}else  if(!StringUtils.isEmpty(config.getJs()) || !StringUtils.isEmpty(config.getJsContent())){
			JsonParseJsCrawler crawlerTemp = new JsonParseJsCrawler(new CrawlerHttpEventWriter(fileDefined, configLogFileMapper));
			if(!StringUtils.isEmpty(config.getJs())) {
				crawlerTemp.loadJsFile(config.getJs());
			}else if(!StringUtils.isEmpty(config.getJsContent())) {
				crawlerTemp.loadjs(config.getJsContent());
			}
			crawlerTemp.setSqlExcuteMapper(new SqlExcuteMapper() {
				
				@Override
				public Map<String, Object> queryOne(String sql) {
					List<Map<String, Object>> list = queryList(sql);
					return list==null || list.isEmpty() ?null:list.get(0);
				}
				
				@Override
				public List<Map<String, Object>> queryList(String sql) {
					return configSqlExcuteMapper.queryBySql(sql);
				}
				
				@Override
				public int excute(String sql) {
					return configSqlExcuteMapper.excuteBySql(sql);
				}
			});
			crawler=crawlerTemp;
		}else {
			throw new ResultException(ResultCode.FAILED,"爬虫配置信息错误,确认调用class或js");
		}
		if(config.getUrls()!=null && config.getUrls().size()>0) {
			for(CrawlerConfigUrlEntity url:config.getUrls()) {
				if(!StringUtils.isEmpty(url.getUrl())) {
					List<String> urls = url.bulidUrl();
					for(String u:urls) {
						crawler.addSeed(getUrlDatums(url,u));
					}
				}
			}
		}else {
			throw new ResultException(ResultCode.FAILED,"爬虫配置信息错误,请添加要爬取的url路径");
		}
		crawler.setThreads(config.getThreads());
		return crawler;
	}
	
	/**
	 * 	设置json   url  参数
	 * @param url
	 * @param u
	 * @return
	 * @author 王帆
	 * @date 2019年1月24日 下午3:36:41
	 */
	public List<CrawlerUrlDatum> getUrlDatums(CrawlerConfigUrlEntity url, String u) {
		return getUrlDatumsByConditon(url,u,url.getCondtion(),new LinkedList<>());
	}
	
	@SuppressWarnings("unchecked")
	public List<CrawlerUrlDatum> getUrlDatumsByConditon(CrawlerConfigUrlEntity url, String u, String condition, List<CrawlerUrlDatum> list) {
		if(condition.trim().startsWith("[")) {
			for(Object obj:JSON.parseArray(url.getCondtion())) {
				list.addAll(getUrlDatumsByConditon(url,u,JSON.toJSONString(obj),list));
			}
		}else if(condition.startsWith("{")){
			CrawlerUrlDatum datum=new CrawlerUrlDatum(u);
			datum.setParam(JSON.parseObject(url.getCondtion(),Map.class));
			list.add(datum);
		}else {
			CrawlerUrlDatum datum=new CrawlerUrlDatum(u);
			datum.setContent(url.getCondtion());
			list.add(datum);
		}
		return list;
	}
	
	/**
	 * 	 获取url的域名路径
	 * @param u
	 * @return
	 * @author 王帆
	 * @date 2019年1月21日 下午2:32:02
	 */
	private String getUrlOrginPath(String u) {
		if(StringUtils.isEmpty(u)) {
			return null;
		}
		int f_index = u.indexOf("://")+3;
		if(f_index<3){
			return null;
		}
		int l_index = u.indexOf("/", f_index);
		return u.substring(0, l_index+1);
	}


	@Override
	public ConfigFileDefined queryTempLogDefined(int id) {
		return configLogFileMapper.selectByPrimaryKey(id);
	}
	
}
