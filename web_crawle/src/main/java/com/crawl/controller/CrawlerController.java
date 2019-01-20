package com.crawl.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.druid.util.StringUtils;
import com.crawl.config.service.CrawlerConfigService;
import com.crawl.data.config.dao.ConfigLogFileMapper;
import com.crawl.data.config.dao.entity.ConfigFileDefined;
import com.crawl.data.config.dao.entity.CrawlerConfigEntity;
import com.crawl.data.config.dao.entity.CrawlerConfigFilterEntity;
import com.crawl.data.config.dao.entity.CrawlerConfigUrlEntity;
import com.crawl.util.CrawlerHttpEventWriter;
import com.sky.crawler.engine.HtmlParseClassNameCrawler;
import com.sky.crawler.engine.HtmlParseJsCrawler;
import com.sky.pub.Page;
import com.sky.pub.Result;
import com.sky.pub.ResultCode;
import com.sky.pub.ResultUtil;
import com.sky.pub.common.exception.ResultException;

import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;

/**
 * 爬虫控制器
 * @author 王帆
 * @date  2019年1月14日 上午11:57:12
 */
@RestController
@RequestMapping("/crawle/config")
public class CrawlerController {
	@Resource
	private CrawlerConfigService crawlerConfigService;
	@Resource
	private ConfigLogFileMapper configLogFileMapper;
	private Log log=LogFactory.getLog(getClass());
	
	/**
	 * 分页查询爬虫配置数据
	 * @param config
	 * @return
	 * @author 王帆
	 * @date 2019年1月17日 下午2:15:46
	 */
	@RequestMapping("/page")
	public Result<Page<CrawlerConfigEntity>> queryPage(CrawlerConfigEntity config){
		return ResultUtil.getOk(ResultCode.OK, crawlerConfigService.queryPageCrawlerConfig(config));
	}
	
	/**
	 * 根据id查询爬虫配置数据
	 * @param id
	 * @return
	 * @author 王帆
	 * @date 2019年1月17日 下午3:25:22
	 */
	@RequestMapping("/query/{id}")
	public Result<CrawlerConfigEntity> queryConfigById(@PathVariable("id") Integer id){
		return ResultUtil.getOk(ResultCode.OK, crawlerConfigService.getById(id));
	}
	
	/**
	 * 保存爬虫配置
	 * @param config
	 * @return
	 * @author 王帆
	 * @date 2019年1月17日 下午3:28:38
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public Result<CrawlerConfigEntity> addCrawlerConfig(@RequestBody CrawlerConfigEntity config){
		return ResultUtil.getOk(ResultCode.OK, crawlerConfigService.saveCrawlerConfig(config));
	}
	
	/**
	 * 修改爬虫配置
	 * @param config
	 * @return
	 * @author 王帆
	 * @date 2019年1月17日 下午3:29:17
	 */
	@RequestMapping(value="/modfiy",method=RequestMethod.POST)
	public Result<CrawlerConfigEntity> modfiyCrawlerConfig(@RequestBody CrawlerConfigEntity config){
		return ResultUtil.getOk(ResultCode.OK, crawlerConfigService.editCrawlerConfig(config));
	}
	
	/**
	 * 卡片页
	 * @param id
	 * @return
	 * @author 王帆
	 * @date 2019年1月17日 下午4:09:23
	 */
	@RequestMapping("page/cart")
	public ModelAndView getCartPage(int id) {
		ModelAndView view=new ModelAndView("/config/crawler/cartPage");
		view.addObject("config",crawlerConfigService.getById(id));
		return view;
	}
	
	/**
	 * 新增修改页面
	 * @param req
	 * @return
	 * @author 王帆
	 * @date 2019年1月17日 下午4:12:15
	 */
	@RequestMapping("page/mod")
	public ModelAndView getmodPage(HttpServletRequest req) {
		ModelAndView view=new ModelAndView("/config/crawler/modPage");
		String id=req.getParameter("id");
		if(id!=null && StringUtils.isNumber(id)) {
			view.addObject("config",crawlerConfigService.getById(Integer.valueOf(id)));
			view.addObject("type",1);
		}else {
			view.addObject("type",0);
			view.addObject("config",new CrawlerConfigEntity());
		}
		return view;
	}
	
	@RequestMapping(value="excute",method=RequestMethod.POST)
	public Result<ConfigFileDefined> excuteCrawlerConfig(@RequestBody CrawlerConfigEntity config) throws Exception {
		return ResultUtil.getOk(ResultCode.OK,excute(config));
	}
	
	/**
	 * 获取临时日志任务信息
	 * @param id
	 * @return
	 * @throws IOException
	 * @author 王帆
	 * @date 2019年1月19日 下午1:55:13
	 */
	@RequestMapping(value="/log/defined")
	public Result<ConfigFileDefined> getFileContent(int id) throws IOException{
		ConfigFileDefined config = configLogFileMapper.selectByPrimaryKey(id);
		if(config!=null && !StringUtils.isEmpty(config.getPath())) {
			BufferedReader read=new BufferedReader(new FileReader(new File(config.getPath()))) ;
			char[] temp = new char[config.getIndex()];
			read.read(temp);
			StringBuffer result=new StringBuffer();
			String str=null;
			while((str=read.readLine())!=null) {
				result.append(str);
			}
			config.setContext(result.toString());
		}
		return ResultUtil.getOk(ResultCode.OK, config);
	}
	
	public ConfigFileDefined excute(CrawlerConfigEntity config) throws Exception {
		ConfigFileDefined fileDefined=null;
		try {
			validCrawlerConfigType(config);
			fileDefined=new ConfigFileDefined(config);
			configLogFileMapper.insert(fileDefined);
			log.info("crawler config type :"+config.getType().toLowerCase());
			switch (config.getType().toLowerCase()) {
			case "html":
				BreadthCrawler crawler=	getCrawler(config,fileDefined);
				crawler.start(config.getDepth());
				break;
			case "json":
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
	
	private List<String> typeList=Arrays.asList("html","json");
	
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
		if(!StringUtils.isEmpty(config.getClassName())) {
			crawler= new HtmlParseClassNameCrawler(config.getCrawlPath(), true, config.getClassName());
		}else  if(!StringUtils.isEmpty(config.getJs()) || !StringUtils.isEmpty(config.getJsContent())){
			HtmlParseJsCrawler crawlerTemp = new HtmlParseJsCrawler(config.getCrawlPath(), true,new CrawlerHttpEventWriter(fileDefined, configLogFileMapper));
			if(!StringUtils.isEmpty(config.getJs())) {
				crawlerTemp.loadJsFile(config.getJs());
			}else if(!StringUtils.isEmpty(config.getJsContent())) {
				crawlerTemp.loadjs(config.getJsContent());
			}
			crawler=crawlerTemp;
		}else {
			throw new ResultException(ResultCode.FAILED,"爬虫配置信息错误,确认调用class或js");
		}
		if(config.getUrls()!=null && config.getUrls().size()>0) {
			for(CrawlerConfigUrlEntity url:config.getUrls()) {
				if(!StringUtils.isEmpty(url.getUrl())) {
					crawler.addSeed(url.bulidUrl());
				}
			}
		}else {
			throw new ResultException(ResultCode.FAILED,"爬虫配置信息错误,请添加要爬取的url路径");
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
	
}
