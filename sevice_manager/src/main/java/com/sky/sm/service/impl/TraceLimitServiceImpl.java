package com.sky.sm.service.impl;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.util.StringUtils;

import com.sky.pub.Page;
import com.sky.pub.PageRequest;
import com.sky.pub.ResultAssert;
import com.sky.pub.common.exception.ResultException;
import com.sky.pub.util.ListUtils;
import com.sky.sm.bean.TraceLimitBean;
import com.sky.sm.bean.req.TraceLimitReqBean;
import com.sky.sm.dao.TraceLimitBeanMapper;
import com.sky.sm.service.TraceLimitService;

/**
 * 链路--限流服务实现
 * @author 王帆
 * @date  2019年5月8日 下午5:32:16
 */
@Service
@Transactional
public class TraceLimitServiceImpl implements TraceLimitService{
	
	@Autowired
	private TraceLimitBeanMapper traceLimitBeanMapper;

	@Override
	public TraceLimitBean queryMaxLike(TraceLimitReqBean limit) {
		List<TraceLimitBean> list = queryMatchUrlList(limit);
		return ListUtils.isEmpty(list)?null:list.get(0);
	}
	
	/**
	 * 查询服务配置的全局可用配置
	 * @param limit
	 * @return
	 * @author 王帆
	 * @date 2019年5月9日 下午2:23:50
	 */
	private List<TraceLimitBean> queryMatchUrlList(TraceLimitReqBean limit){
		List<TraceLimitBean> resultlist=new LinkedList<>();
		if(StringUtils.isEmpty(limit.getUrl())) {
			return resultlist;
		}
		limit.setShowGlobal(true);
		limit.setCount(null);
		limit.setMatchUrl(null);
		limit.setPriod(null);
		List<TraceLimitBean> list = traceLimitBeanMapper.queryLimitBeans(null, limit);
		if(!ListUtils.isEmpty(list)) {
			PathMatcher pathmatch=new AntPathMatcher();
			for(TraceLimitBean deflimit:list) {
				if(pathmatch.match(deflimit.getMatchUrl(), limit.getUrl())) {
					resultlist.add(deflimit);
				}
			}
		}
		return resultlist;
	}

	@Override
	public int addLimitBean(TraceLimitBean limit) throws ResultException {
		ResultAssert.isBlank(limit.getServerName(), "缺少服务编码");
		return traceLimitBeanMapper.insert(limit);
	}

	@Override
	public int updateLimitBean(TraceLimitBean limit) {
		return traceLimitBeanMapper.updateByPrimaryKey(limit);
	}

	@Override
	public int deleteLimitBean(TraceLimitBean limit) {
		return traceLimitBeanMapper.deleteByPrimaryKey(limit.getId());
	}

	@Override
	public Page<TraceLimitBean> queryLimitPage(PageRequest<TraceLimitReqBean> limitpage) {
		limitpage.initPage();
		Page<TraceLimitBean> page=new Page<>();
		page.setPageData(limitpage);
		if(limitpage !=null && limitpage.getData() !=null && !StringUtils.isEmpty(limitpage.getData().getUrl())) {
			//根据url 匹配全局限流配置
			List<TraceLimitBean> list = queryMatchUrlList(limitpage.getData());
			page.setList(list);
			page.setTotal(list.size());
		}else {
			page.setTotal(traceLimitBeanMapper.accoutLimitBean(limitpage.getData()));
			page.setList(traceLimitBeanMapper.queryLimitBeans(limitpage,limitpage.getData()));
		}
		return page;
	}

	@Override
	public int deleteLimits(String id) {
		return StringUtils.isEmpty(id)?0:traceLimitBeanMapper.deleteLimit(Arrays.asList(id.split(",")));
	}

	@Override
	public TraceLimitBean queryById(int id) {
		return traceLimitBeanMapper.selectByPrimaryKey(id);
	}

}
