package com.sky.sm.service;

import java.util.List;

import com.sky.pub.common.exception.ResultException;
import com.sky.sm.bean.TraceRecordBean;
import com.sky.sm.bean.TraceRecordEntity;

public interface TraceRecordService {
	
	public boolean saveFast(TraceRecordEntity trace) throws ResultException;
	public boolean updateFast(TraceRecordEntity trace) throws ResultException;
	public boolean save(TraceRecordEntity trace);
	
	/**
	 * 批量添加链路数据
	 * @param traces  链路数据集合
	 * @return  变更数量
	 * @author 王帆
	 * @date 2019年3月16日 下午5:11:37
	 */
	public int addTraceList(List<TraceRecordEntity> traces);
	public boolean update(TraceRecordEntity trace);
	
	public TraceRecordEntity queryTraceBean(TraceRecordEntity trace) throws ResultException;
	public List<TraceRecordBean> queryTraceByUrl(String url);
	public List<TraceRecordBean> queryTraceByGroup(String groupId);
	public List<TraceRecordBean> queryTraceBytrace(String traceId);
	public List<TraceRecordBean> queryTraceBySession(String sessionId);
	public List<TraceRecordBean> queryTrace(TraceRecordEntity trace);
	
	/**
	 * 更新链路缓存数据
	 * 
	 * @author 王帆
	 * @date 2019年3月16日 下午9:16:26
	 */
	public void reflushTraceRecordData();
	
}
