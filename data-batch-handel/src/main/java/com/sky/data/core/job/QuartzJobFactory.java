package com.sky.data.core.job;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.sky.data.core.control.BatchDataHandelControl;
import com.sky.data.bean.DataBatchTaskExtEntity;

public class QuartzJobFactory implements Job{
	private StringBuilder str=new StringBuilder();

	@Override
	public void execute(JobExecutionContext args) throws JobExecutionException {
		JobDataMap jobData = args.getMergedJobDataMap();
		JobClient jobClient=new JobClient(jobData,args.getJobDetail().getKey());
		excuteJobAction(jobClient);
	}

	public  void excuteJobAction(JobClient jobClient) throws JobExecutionException {
		try {
			new BatchDataHandelControl(jobClient.getTaskId(), jobClient.getGroupId(),StringUtils.isEmpty(jobClient.ext)?null:JSON.parseObject(jobClient.ext, DataBatchTaskExtEntity.class)).start();
		} catch (Exception e) {
			e.printStackTrace();
			addJoErrorMsg(e.getMessage());
		}
		if(!StringUtils.isEmpty(str.toString())) {
			throw new JobExecutionException(str.toString());
		}
	}
	

	
	private void addJoErrorMsg(String msg) {
		str.append(msg).append(";<br/>");
	}
	
	/**
	 * job传递的信息
	 * @author 王帆
	 * @date  2019年2月13日 下午7:58:04
	 */
	public static class JobClient implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String taskId;
		private String groupId;
		private String params;
		private Byte model;
		private String ext;
		private Set<String> limitNodes;
		JobClient(JobDataMap jobData, JobKey jobKey){
			this.setTaskId(jobKey.getName());
			this.setGroupId(jobKey.getGroup());
			this.setParams(jobData.getString("params"));
			String limitnodes = jobData.getString("limitnodes");
			if(!StringUtils.isEmpty(limitnodes)) {
				this.setLimitNodes(new HashSet<>(Arrays.asList(limitnodes.split(";"))));
			}
			Object modle_temp = jobData.get("scheduleModel");
			if(modle_temp!=null) {
				this.setModel(Byte.valueOf(modle_temp.toString()));
			}
			this.setExt(jobData.getString("ext"));
			
		}
		public String getTaskId() {
			return taskId;
		}
		public void setTaskId(String taskId) {
			this.taskId = taskId;
		}
		public String getParams() {
			return params;
		}
		public void setParams(String params) {
			this.params = params;
		}
		public Byte getModel() {
			return model;
		}
		public void setModel(Byte model) {
			this.model = model;
		}
		public Set<String> getLimitNodes() {
			return limitNodes;
		}
		public void setLimitNodes(Set<String> limitNodes) {
			this.limitNodes = limitNodes;
		}
		public String getGroupId() {
			return groupId;
		}
		public void setGroupId(String groupId) {
			this.groupId = groupId;
		}
		public String getExt() {
			return ext;
		}
		public void setExt(String ext) {
			this.ext = ext;
		}
		
	}
	
}
