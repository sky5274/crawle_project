package com.sky.crawl.config.service.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import com.sky.crawl.config.service.CrawlerConfigService;
import com.sky.crawl.data.config.dao.CrawlerConfigEntityMapper;
import com.sky.crawl.data.config.dao.CrawlerConfigFilterEntityMapper;
import com.sky.crawl.data.config.dao.CrawlerConfigUrlEntityMapper;
import com.sky.crawl.data.config.dao.entity.CrawlerConfigEntity;
import com.sky.crawl.data.config.dao.entity.CrawlerConfigFilterEntity;
import com.sky.crawl.data.config.dao.entity.CrawlerConfigUrlEntity;
import com.sky.pub.Page;
import com.sky.pub.ResultCode;
import com.sky.pub.common.exception.ResultException;
import com.sky.pub.util.ListUtils;

/**
 * 爬虫配置service实现
 * @author 王帆
 * @date  2019年1月16日 下午5:14:25
 */
@Service
@Transactional(rollbackFor=Exception.class)
public class CrawlerConfigServiceImpl implements CrawlerConfigService{
	@Resource
	private CrawlerConfigEntityMapper crawlerConfigMapper;
	@Resource
	private CrawlerConfigFilterEntityMapper crawlerConfigFilterMapper;
	@Resource
	private CrawlerConfigUrlEntityMapper crawlerConfigUrlMapper;
	
	private List<String> typeList=Arrays.asList("html","json");

	@Override
	public Page<CrawlerConfigEntity> queryPageCrawlerConfig(CrawlerConfigEntity config) {
		Page<CrawlerConfigEntity> page=new Page<CrawlerConfigEntity>();
		page.setList(crawlerConfigMapper.queryPageByCrawlerConfig(config));
		page.setTotal(crawlerConfigMapper.account(config));
		return page;
	}

	@Override
	public List<CrawlerConfigEntity> queryListCrawlerConfig(CrawlerConfigEntity config) {
		return crawlerConfigMapper.queryListByCrawlerConfig(config);
	}

	@Override
	public CrawlerConfigEntity saveCrawlerConfig(CrawlerConfigEntity config) throws ResultException {
		comValidateConfig(config,false);
		String code = getUuid();
		config.setCode(code);
		int size=crawlerConfigMapper.insertSelective(config);
		if(size!=1) {
			throw new ResultException(ResultCode.FAILED,"爬虫配置新增失败");
		}
		addConfigDetails(config);
		return getById(config.getId());
	}
	
	/**
	 * 	新增爬虫配置url\filter信息
	 * @param code
	 * @param config
	 * @throws ResultException
	 * @author 王帆
	 * @date 2019年1月24日 下午5:10:41
	 */
	private void addConfigDetails(CrawlerConfigEntity config) throws ResultException{
		List<CrawlerConfigFilterEntity> filters = config.getFilters();
		if(!ListUtils.isEmpty(filters)) {
			for(CrawlerConfigFilterEntity f:filters) {
				f.setConfigCode(config.getCode());
			}
			crawlerConfigFilterMapper.insertBatch(filters);
		}
		List<CrawlerConfigUrlEntity> urls = config.getUrls();
		if(!ListUtils.isEmpty(urls)) {
			for(CrawlerConfigUrlEntity u:urls) {
				u.setConfigCode(config.getCode());
			}
			crawlerConfigUrlMapper.insertBatch(urls);
		}
	}
	/**
	 * 	删除爬虫配置明细信息
	 * @param config
	 * @throws ResultException
	 * @author 王帆
	 * @date 2019年1月24日 下午5:11:39
	 */
	private void delConfigDetails(CrawlerConfigEntity config) throws ResultException{
		crawlerConfigFilterMapper.deleteByCode(config.getCode());
		crawlerConfigUrlMapper.deleteByCode(config.getCode());
	}
	
	/**
	 *	根据配置编码删除对应的明细信息 
	 * @param codes
	 * @throws ResultException
	 * @author 王帆
	 * @date 2019年1月24日 下午5:14:20
	 */
	private void delConfigDetailsByCodeSet(Set<String> codes) throws ResultException{
		crawlerConfigFilterMapper.deleteByCodeSet(codes);
		crawlerConfigUrlMapper.deleteByCodeSet(codes);
	}
	
	/**
	 * 	爬虫配置，通用校验
	 * @param config
	 * @author 王帆
	 * @throws ResultException 
	 * @date 2019年1月24日 下午3:48:39
	 */
	private void comValidateConfig(CrawlerConfigEntity config, boolean flag) throws ResultException {
		List<String> errors=new LinkedList<>();
		if(flag) {
			if(StringUtils.isEmpty(config.getCode())) {
				errors.add("爬虫项目编码缺失");
			}
		}
		if(StringUtils.isEmpty(config.getName())) {
			errors.add("爬虫项目名称缺失");
		}
		if(StringUtils.isEmpty(config.getType())) {
			errors.add("爬虫页面类型缺少");
		}else if(!typeList.contains(config.getType().toLowerCase())) {
			errors.add("爬虫页面类型:"+config.getType().toLowerCase()+"不在默认范围时："+typeList);
		}
		if(StringUtils.isEmpty(config.getJs()) && StringUtils.isEmpty(config.getClassName())) {
			errors.add("爬虫核心解析文件缺失");
		}
		if(config.getUrls()==null || config.getUrls().isEmpty()) {
			errors.add("爬取url配置缺失");
		}
		throw new ResultException(ResultCode.PARAM_VALID,"爬虫配置校验"+String.join("！;", errors));
	}
	
	@Override
	public CrawlerConfigEntity editCrawlerConfig(CrawlerConfigEntity config) throws ResultException{
		comValidateConfig(config,true);
		int size=crawlerConfigMapper.updateByPrimaryKey(config);
		if(size!=1) {
			throw new ResultException(ResultCode.FAILED,"爬虫配置更新失败");
		}
		delConfigDetails(config);
		addConfigDetails(config);
		return  getById(config.getId());
	}

	@Override
	public boolean delCrawlerConfig(List<CrawlerConfigEntity> list) throws ResultException {
		if(list==null ||list.isEmpty()) {
			throw new ResultException(ResultCode.PARAM_VALID,"请输入要删除的爬虫配置数据");
		}
		Set<String >codeSet=new HashSet<>();
		for(CrawlerConfigEntity conf:list) {
			codeSet.add(conf.getCode());
		}
		if(crawlerConfigMapper.deleteByIdList(list)==list.size()) {
			delConfigDetailsByCodeSet(codeSet);
			return true;
		}
		return false;
	}

	@Override
	public CrawlerConfigEntity getById(int id) {
		return crawlerConfigMapper.queryCrawlerCnfig(id);
	}
	
	private String getUuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

}
