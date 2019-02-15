package com.sky.task.core;

import java.io.Serializable;
import java.util.List;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.util.StringUtils;
import com.sky.pub.util.ListUtils;
import com.sky.pub.util.SpringUtil;
import com.sky.task.core.RpcConfig.NodeData;

public class QuartzJobFactory implements Job{
	private StringBuilder str=new StringBuilder();

	@Override
	public void execute(JobExecutionContext args) throws JobExecutionException {
		JobDataMap jobData = args.getMergedJobDataMap();
		JobClient jobClient=new JobClient(jobData);
		excuteJobAction(jobClient);
	}

	public  void excuteJobAction(JobClient jobClient) throws JobExecutionException {
		RpcConfig rpcConfig=SpringUtil.getBean(RpcConfig.class);
		List<NodeData> nodes = rpcConfig.getAllNodeDataByClassName(jobClient.getTargetClass());
		if(!ListUtils.isEmpty(nodes)) {
			if(jobClient.getModel()==1) {
				excuteJob(nodes.get(0).getUrl(),jobClient.getParams());
			}else {
				for (NodeData n:nodes) {
					excuteJob(n.getUrl(), jobClient.getParams());
				}
			}
		}else {
			addJoErrorMsg(String.format("未找到[%s]的节点",jobClient.getTargetClass()));
		}
		if(!StringUtils.isEmpty(str)) {
			throw new JobExecutionException(str.toString());
		}
	}
	

	private  void excuteJob(String url, String params) {
		try {
			HttpTaskRpcClient.invoke(url, params);
		} catch (Throwable e) {
			addJoErrorMsg(org.apache.commons.lang.exception.ExceptionUtils.getStackTrace(e));
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
		private String targetClass;
		private String taskId;
		private String params;
		private Byte model;
		JobClient(JobDataMap jobData){
			this.setTargetClass(jobData.getString("class"));
			this.setTaskId(jobData.getString("taskId"));
			this.setParams(jobData.getString("params"));
			Object modle_temp = jobData.get("scheduleModel");
			if(modle_temp!=null) {
				this.setModel(Byte.valueOf(modle_temp.toString()));
			}
			
		}
		public String getTargetClass() {
			return targetClass;
		}
		public void setTargetClass(String targetClass) {
			this.targetClass = targetClass;
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
		
	}
	
}
