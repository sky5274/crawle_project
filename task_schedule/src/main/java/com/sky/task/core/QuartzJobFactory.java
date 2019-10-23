package com.sky.task.core;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.reflection.ExceptionUtil;
import org.apache.tomcat.util.ExceptionUtils;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.springframework.util.StringUtils;
import com.sky.pub.util.ListUtils;
import com.sky.pub.util.SpringUtil;
import com.sky.pub.web.exception.SpringHandlerExceptionResolver;
import com.sky.task.core.RpcConfig.NodeData;

public class QuartzJobFactory implements Job{
	private StringBuilder str=new StringBuilder();

	@Override
	public void execute(JobExecutionContext args) throws JobExecutionException {
		JobDataMap jobData = args.getMergedJobDataMap();
		JobClient jobClient=new JobClient(jobData,args.getJobDetail().getKey());
		excuteJobAction(jobClient);
	}

	public  void excuteJobAction(JobClient jobClient) throws JobExecutionException {
		List<NodeData> nodes = getActiveNode(jobClient);
		if(!ListUtils.isEmpty(nodes)) {
			if(jobClient.getModel()==1) {
				excuteJob(nodes.get(0).getUrl(),jobClient.getParams());
			}else {
				for (NodeData n:nodes) {
					excuteJob(n.getUrl(), jobClient.getParams());
				}
			}
		}
		if(!StringUtils.isEmpty(str.toString())) {
			throw new JobExecutionException(str.toString());
		}
	}
	
	public List<NodeData> getActiveNode(JobClient jobClient) {
		RpcConfig rpcConfig=SpringUtil.getBean(RpcConfig.class);
		List<NodeData> nodes = rpcConfig.getAllNodeDataByClassName(jobClient.getGroupId(),jobClient.getTargetClass());
		List<NodeData> temp=new LinkedList<>();
		if(ListUtils.isEmpty(nodes)) {
			addJoErrorMsg(String.format("未找到[%s]的节点",jobClient.getTargetClass()));
		}else {
			if(!ListUtils.isEmpty(jobClient.getLimitNodes())) {
				for(NodeData n:nodes) {
					String ip=n.getIp()+":"+n.getPort();
					if(jobClient.getLimitNodes().contains(ip)) {
						temp.add(n);
					}
				}
				if(ListUtils.isEmpty(temp)) {
					addJoErrorMsg("system has the node no in"+jobClient.getLimitNodes());
				}
			}else {
				temp=nodes;
			}
		}
		return temp;
	}
	

	private  void excuteJob(String url, String params) {
		try {
			HttpTaskRpcClient.invoke(url, params);
		} catch (Throwable e) {
			addJoErrorMsg(SpringHandlerExceptionResolver.getExceptionStrace(e));
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
		private String groupId;
		private String params;
		private Byte model;
		private Set<String> limitNodes;
		JobClient(JobDataMap jobData, JobKey jobKey){
			this.setTargetClass(jobData.getString("class"));
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
		
	}
	
}
