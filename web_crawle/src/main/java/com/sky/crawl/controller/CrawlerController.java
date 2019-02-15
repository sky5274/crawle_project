package com.sky.crawl.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.druid.util.StringUtils;
import com.sky.crawl.config.service.CrawlerConfigService;
import com.sky.crawl.config.service.CrawlerEventEngineService;
import com.sky.crawl.data.config.dao.entity.ConfigFileDefined;
import com.sky.crawl.data.config.dao.entity.CrawlerConfigEntity;
import com.sky.pub.Page;
import com.sky.pub.Result;
import com.sky.pub.ResultCode;
import com.sky.pub.ResultUtil;
import com.sky.pub.common.exception.ResultException;

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
	private CrawlerEventEngineService crawlerEventEngineService;
	
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
	 * @throws ResultException 
	 * @date 2019年1月17日 下午3:28:38
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public Result<CrawlerConfigEntity> addCrawlerConfig(@RequestBody CrawlerConfigEntity config) throws ResultException{
		return ResultUtil.getOk(ResultCode.OK, crawlerConfigService.saveCrawlerConfig(config));
	}
	
	/**
	 * 修改爬虫配置
	 * @param config
	 * @return
	 * @author 王帆
	 * @throws ResultException 
	 * @date 2019年1月17日 下午3:29:17
	 */
	@RequestMapping(value="/modfiy",method=RequestMethod.POST)
	public Result<CrawlerConfigEntity> modfiyCrawlerConfig(@RequestBody CrawlerConfigEntity config) throws ResultException{
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
		return ResultUtil.getOk(ResultCode.OK,crawlerEventEngineService.excute(config));
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
		ConfigFileDefined config = crawlerEventEngineService.queryTempLogDefined(id);
		if(config!=null && !StringUtils.isEmpty(config.getPath())) {
			@SuppressWarnings("resource")
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
	
}
