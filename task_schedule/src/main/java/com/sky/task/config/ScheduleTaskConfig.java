package com.sky.task.config;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger.TriggerState;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;
import com.sky.pub.util.ListUtils;
import com.sky.task.dao.JobTaskEntityMapper;

@Component
@EnableScheduling
public class ScheduleTaskConfig {
	@Autowired
	private SchedulerFactoryBean schedulerFactoryBean;
	@Autowired
	private JobTaskEntityMapper jobTaskEntityMapper;
	
	private Log log=LogFactory.getLog(getClass());
	
	@Scheduled(cron = "0/5 * * * * ?")
	public void autoMintorScheduleState() {
		Scheduler schudeler = schedulerFactoryBean.getScheduler();
		Set<TriggerKey> triggers=new HashSet<>();
		try {
			List<String> groupset = schudeler.getTriggerGroupNames();
			log.debug("schedule status listener run,groupset: "+groupset);
			for(String group:groupset) {
				log.debug("schedule status listener run,group: "+group);
				GroupMatcher<TriggerKey> match=GroupMatcher.triggerGroupEquals(group);
				triggers.addAll(schudeler.getTriggerKeys(match));
			}
			changeTaskJobStatus(triggers,schudeler);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 更新调入任务定义的任务状态
	 * @param triggers
	 * @param schudeler
	 * @throws SchedulerException
	 * @author 王帆
	 * @date 2019年2月18日 下午5:27:12
	 */
	private void changeTaskJobStatus(Set<TriggerKey> triggers, Scheduler schudeler) throws SchedulerException {
		if(!ListUtils.isEmpty(triggers)) {
			Map<Integer, Set<String>> triggerKey_Status_Map=new HashMap<>();
			for(TriggerKey t:triggers) {
				int status = getTaskJobStatus(t,schudeler);
				Set<String> triggerKeys = triggerKey_Status_Map.get(status);
				if(ListUtils.isEmpty(triggers)) {
					triggerKeys=new HashSet<>();
				}
				triggerKeys.add(t.getGroup()+"-"+t.getName());
				triggerKey_Status_Map.put(status, triggerKeys);
			}
			if(!ListUtils.isEmpty(triggerKey_Status_Map.keySet())) {
				for(Integer status:triggerKey_Status_Map.keySet()) {
					jobTaskEntityMapper.updateJobTasksStatus(status,triggerKey_Status_Map.get(status));
				}
			}
		}
	}

	private int getTaskJobStatus(TriggerKey t, Scheduler schudeler) throws SchedulerException {
		TriggerState status = schudeler.getTriggerState(t);
		int stat=0;
		switch (status) {
		case ERROR:
			stat= 2;
			break;
		case BLOCKED:
			stat=2;
			break;
		case NORMAL:
			stat= 2;
			break;
		case PAUSED:
			stat= 1;
			break;
		case COMPLETE:
			stat= 0;
			schudeler.resumeTrigger(t);
			break;
		case NONE:
			stat= 0;
			break;
			
		default:
			break;
		}
		return stat;
	}
}
