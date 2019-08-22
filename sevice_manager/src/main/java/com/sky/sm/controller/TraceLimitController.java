package com.sky.sm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sky.pub.Page;
import com.sky.pub.PageRequest;
import com.sky.pub.Result;
import com.sky.pub.ResultUtil;
import com.sky.pub.common.exception.ResultException;
import com.sky.sm.bean.TraceLimitBean;
import com.sky.sm.bean.req.TraceLimitReqBean;
import com.sky.sm.service.TraceLimitService;

/**
 * 链路限流controller
 * @author 王帆
 * @date  2019年5月9日 下午3:17:10
 */
@RestController
@RequestMapping("/project/limit")
public class TraceLimitController {
	
	@Autowired
	private TraceLimitService traceLimitService;
	
	@PostMapping("/query/page")
	public Result<Page<TraceLimitBean>> queryLimitsByPage(@RequestBody PageRequest<TraceLimitReqBean> pageLimit) {
		return ResultUtil.getOk(traceLimitService.queryLimitPage(pageLimit));
	}
	
	@PostMapping("/add")
	public Result<Integer> addLimitBean(@RequestBody TraceLimitBean limit) throws ResultException {
		return ResultUtil.getOk(traceLimitService.addLimitBean(limit));
	}
	
	@PostMapping("/update")
	public Result<Integer> updateLimitBean(@RequestBody TraceLimitBean limit) throws ResultException {
		return ResultUtil.getOk(traceLimitService.updateLimitBean(limit));
	}
	
	@RequestMapping("delete")
	public Result<Integer> deleteLimitBean(String id) throws ResultException {
		return ResultUtil.getOk(traceLimitService.deleteLimits(id));
	}
	
	@RequestMapping("/query")
	public Result<TraceLimitBean> queryLlistById(int id) throws ResultException {
		return ResultUtil.getOk(traceLimitService.queryById(id));
	}
}
