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
	
	@RequestMapping("limit")
	public Result<TraceLimitBean> limit(TraceLimitReqBean limit) {
		return ResultUtil.getOk(traceLimitService.queryMaxLike(limit));
	}
	
	@RequestMapping("/trace/start")
	public Result<Boolean> traceStart(TraceRecordEntity trace) throws ResultException {
		return ResultUtil.getOk(traceRecordSerive.saveFast(trace));
	}
	
	@RequestMapping("/trace/end")
	public  Result<Boolean> traceEnd(TraceRecordEntity trace) throws ResultException {
		return ResultUtil.getOk(traceRecordSerive.updateFast(trace));
	}
	
	@RequestMapping("/query/trace")
	public Result<List<TraceRecordBean>> queryTrace(TraceRecordEntity trace){
		return ResultUtil.getOk(traceRecordSerive.queryTrace(trace));
	}
	
}
