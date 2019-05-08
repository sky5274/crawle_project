package com.sky.sm.service.impl;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.sky.pub.ResultCode;
import com.sky.pub.common.exception.ResultException;
import com.sky.pub.service.impl.BaseRedisServiceImpl;
import com.sky.pub.util.ListUtils;
import com.sky.sm.bean.TraceRecordBean;
import com.sky.sm.bean.TraceRecordEntity;
import com.sky.sm.dao.TraceRecordEntityMapper;
import com.sky.sm.service.TraceRecordService;

@Service
public class TraceRecordServiceImpl extends BaseRedisServiceImpl<TraceRecordEntity> implements TraceRecordService{
	@Autowired
	private TraceRecordEntityMapper traceRecordMapper;
	
	@Override
	protected Class<TraceRecordEntity> getExtendClass() {
		return TraceRecordEntity.class;
	}
	
	private String getKey(TraceRecordEntity trace) throws ResultException {
		if(trace==null) {
			throw new ResultException(ResultCode.VALID,"链路数据为空");
		}
		StringBuilder str=new StringBuilder();
		str.append(trace.getGroupId())
			.append("-").append(trace.getSessionId())
			.append("-").append(trace.getTraceId())
			.append("-").append(trace.getTracePId())
			.append("$").append(trace.getUrl());
		return str.toString();
	}
	private String getLikeKey(TraceRecordEntity trace) {
		StringBuilder str=new StringBuilder();
		if(trace!=null) {
			str.append(StringUtils.isEmpty(trace.getGroupId())?"*":trace.getGroupId())
			.append("-").append(StringUtils.isEmpty(trace.getSessionId())?"*":trace.getSessionId())
			.append("-").append(StringUtils.isEmpty(trace.getTraceId())?"*":trace.getTraceId())
			.append("-").append(StringUtils.isEmpty(trace.getTracePId())?"*":trace.getTracePId())
			.append("$").append(StringUtils.isEmpty(trace.getUrl())?"*":trace.getUrl());
		}else {
			str.append("*");
		}
	
		return str.toString();
	}

	@Override
	public boolean saveFast(TraceRecordEntity trace) throws ResultException {
		trace.setTs(new Timestamp(System.currentTimeMillis()));
		saveCache(trace);
		return true;
	}

	@Override
	public boolean updateFast(TraceRecordEntity trace) throws ResultException {
		TraceRecordEntity bean = doStringGet(getKey(trace));
		if(bean !=null) {
			bean.setCost(System.currentTimeMillis()-bean.getTs().getTime());
			bean.setResponseBody(trace.getResponseBody());
			bean.setStatus(trace.getStatus());
			saveCache(bean);
		}
		return true;
	}
	
	private void saveCache(TraceRecordEntity trace) throws ResultException {
		//定义十秒的存储时间
		doStringExprie(getKey(trace), 10, trace,TimeUnit.MINUTES);
	}

	@Override
	public boolean save(TraceRecordEntity trace) {
		return traceRecordMapper.insertSelective(trace)>0;
	}

	@Override
	public boolean update(TraceRecordEntity trace) {
		return traceRecordMapper.updateByPrimaryKeySelective(trace)>0;
	}

	@Override
	public List<TraceRecordBean> queryTraceByUrl(String url) {
		TraceRecordEntity trace=new TraceRecordEntity();
		trace.setUrl(url);
		trace.setIsLike(1);
		return queryTrace(trace);
	}

	@Override
	public List<TraceRecordBean> queryTraceByGroup(String groupId) {
		TraceRecordEntity trace=new TraceRecordEntity();
		trace.setGroupId(groupId);
		return queryTrace(trace);
	}

	@Override
	public List<TraceRecordBean> queryTraceBytrace(String traceId) {
		TraceRecordEntity trace=new TraceRecordEntity();
		trace.setTraceId(traceId);
		return queryTrace(trace);
	}

	@Override
	public List<TraceRecordBean> queryTraceBySession(String sessionId) {
		TraceRecordEntity trace=new TraceRecordEntity();
		trace.setSessionId(sessionId);
		return queryTrace(trace);
	}

	@Override
	public List<TraceRecordBean> queryTrace(TraceRecordEntity trace) {
		List<TraceRecordEntity> list = queryCacheTraces(trace);
		List<TraceRecordBean> traces=new LinkedList<TraceRecordBean>();
		if(!ListUtils.isEmpty(list)) {
			traces.addAll(transfrom(list));
		}
		traces.add(traceRecordMapper.queryTraceGroup(trace));
		return traces;
	}
	
	/**
	 * 将链路数据分组转换
	 * @param list
	 * @return
	 * @author 王帆
	 * @date 2019年3月16日 上午10:09:42
	 */
	private List<TraceRecordBean> transfrom(List<TraceRecordEntity> list) {
		List<TraceRecordBean> traces=new LinkedList<TraceRecordBean>();
		return traces;
	}
	
	/**
	 * 根据数据模糊查询链路缓存数据
	 * @param trace
	 * @return
	 * @author 王帆
	 * @date 2019年3月16日 上午10:08:42
	 */
	private List<TraceRecordEntity> queryCacheTraces(TraceRecordEntity trace) {
		Set<String> keys = doGetObjKeys(getLikeKey(trace));
		List<TraceRecordEntity> traces=new LinkedList<TraceRecordEntity>();
		if(keys!=null) {
			for(String key:keys) {
				traces.add(doGet(key));
			}
		}
		//按ts排序输出
		return traces.stream().sorted(Comparator.comparing(TraceRecordEntity::getTs)).collect(Collectors.toList());
	}
	

	@Override
	public TraceRecordEntity queryTraceBean(TraceRecordEntity trace) throws ResultException {
		return doStringGet(getKey(trace));
	}

	@Override
	public int addTraceList(List<TraceRecordEntity> traces) {
		return traceRecordMapper.insertBatch(traces);
	}

	@Override
	public void reflushTraceRecordData() {
		List<TraceRecordEntity> traces = queryCacheTraces(null);
		List<TraceRecordEntity> list=new LinkedList<>();
		for(TraceRecordEntity trace:traces) {
			if(trace.getCost()!=null && trace.getCost()>0) {
				list.add(trace);
				try {
					doDeleteKey(getKey(trace));
				} catch (ResultException e) {
				}
			}
		}
		if(!ListUtils.isEmpty(list)) {
			traceRecordMapper.insertBatch(list);
		}
	}

}
