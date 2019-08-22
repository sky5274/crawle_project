package com.sky.sm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sky.pub.Result;
import com.sky.pub.ResultUtil;
import com.sky.pub.common.exception.ResultException;
import com.sky.sm.bean.TraceLimitBean;
import com.sky.sm.bean.TraceRecordBean;
import com.sky.sm.bean.TraceRecordEntity;
import com.sky.sm.bean.req.TraceLimitReqBean;
import com.sky.sm.service.TraceLimitService;
import com.sky.sm.service.TraceRecordService;

@RestController
@RequestMapping("http")
public class HttpServiceController {
	@Autowired
	private TraceRecordService traceRecordSerive;
	@Autowired
	private TraceLimitService traceLimitService;
	
	/**
	 * url限流配置查询请求
	 * @param limit
	 * @return
	 * @author 王帆
	 * @date 2019年5月14日 下午1:46:30
	 */
	@RequestMapping("limit")
	public Result<TraceLimitBean> limit(TraceLimitReqBean limit) {
		return ResultUtil.getOk(traceLimitService.queryMaxLike(limit));
	}
	
	/**
	 * 链路追踪--开始
	 * @param trace
	 * @return
	 * @throws ResultException
	 * @author 王帆
	 * @date 2019年5月14日 下午1:46:59
	 */
	@RequestMapping("/trace/start")
	public Result<Boolean> traceStart(TraceRecordEntity trace) throws ResultException {
		return ResultUtil.getOk(traceRecordSerive.saveFast(trace));
	}
	
	/**
	 * 链路追踪--结束
	 * @param trace
	 * @return
	 * @throws ResultException
	 * @author 王帆
	 * @date 2019年5月14日 下午1:47:19
	 */
	@RequestMapping("/trace/end")
	public  Result<Boolean> traceEnd(TraceRecordEntity trace) throws ResultException {
		return ResultUtil.getOk(traceRecordSerive.updateFast(trace));
	}
	
	/**
	 * 链路追踪--查询
	 * @param trace
	 * @return
	 * @author 王帆
	 * @date 2019年5月14日 下午1:47:35
	 */
	@RequestMapping("/query/trace")
	public Result<List<TraceRecordBean>> queryTrace(TraceRecordEntity trace){
		return ResultUtil.getOk(traceRecordSerive.queryTrace(trace));
	}
	
}
