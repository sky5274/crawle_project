package com.crawl.config.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.crawl.config.service.CrawlerConfigService;
import com.crawl.data.config.dao.CrawlerConfigEntityMapper;
import com.crawl.data.config.dao.CrawlerConfigFilterEntityMapper;
import com.crawl.data.config.dao.CrawlerConfigUrlEntityMapper;
import com.crawl.data.config.dao.entity.CrawlerConfigEntity;
import com.sky.pub.Page;
import com.sky.pub.ResultCode;
import com.sky.pub.common.exception.ResultException;

/**
 * 爬虫配置service实现
 * @author 王帆
 * @date  2019年1月16日 下午5:14:25
 */
@Service
public class CrawlerConfigServiceImpl implements CrawlerConfigService{
	@Resource
	private CrawlerConfigEntityMapper crawlerConfigMapper;
	@Resource
	private CrawlerConfigFilterEntityMapper crawlerConfigFilterMapper;
	@Resource
	private CrawlerConfigUrlEntityMapper crawlerConfigUrlMapper;

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
	public CrawlerConfigEntity saveCrawlerConfig(CrawlerConfigEntity config) {
		String code = getUuid();
		config.setCode(code);
		return null;
	}

	@Override
	public CrawlerConfigEntity editCrawlerConfig(CrawlerConfigEntity config) {
		// TODO Auto-generated method stub
		return null;
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
			crawlerConfigFilterMapper.deleteByCodeSet(codeSet);
			crawlerConfigUrlMapper.deleteByCodeSet(codeSet);
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
