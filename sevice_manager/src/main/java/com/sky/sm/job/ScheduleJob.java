package com.sky.sm.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.sky.sm.service.TraceRecordService;

@Component
public class ScheduleJob {
	@Autowired
	private TraceRecordService traceRecordServce;
	
	@Scheduled(cron="0 0/1  * * * *")
	public void recordTraceNode() {
		traceRecordServce.reflushTraceRecordData();
	}
}
