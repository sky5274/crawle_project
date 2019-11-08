package com.sky.container.bean;

import java.io.Serializable;

import com.sky.container.util.DoubleParseUtil;

import lombok.Data;

/**
 * docker container stats data
 * @author 王帆
 * @date  2019年11月5日 下午4:10:16
 */
@Data
public class ContainerStatsBean implements Serializable{
	/***/
	private static final long serialVersionUID = 6149814777939362588L;
	
	private String containerId;
	private String containerName;
	private String cupUsing;			//CPU % 
	private String memberCacheUsing; 	//MEM USAGE / LIMIT
	private String memberUsing;			//MEM %
	private String netIO;				//NET I/O
	private String blockIO;				//BLOCK I/O
	private String PID;					//PIDS
	
	public double getCupUsingSize() {
		return cupUsing==null?0:DoubleParseUtil.parseDoube(cupUsing);
	}
	public double getMemberUsingSize() {
		return memberUsing==null?0:DoubleParseUtil.parseDoube(memberUsing);
	}
	public String getMemberUseCache() {
		return memberCacheUsing==null?null:memberCacheUsing.split("/")[0].trim();
	}
	public String getMemberLimitCache() {
		return memberCacheUsing==null?null:memberCacheUsing.split("/")[1].trim();
	}
	public String getNetInput() {
		return netIO==null?null:netIO.split("/")[0].trim();
	}
	public String getNetOutput() {
		return netIO==null?null:netIO.split("/")[1].trim();
	}
	public String getBlockInput() {
		return blockIO==null?null:blockIO.split("/")[0].trim();
	}
	public String getBlockOutput() {
		return blockIO==null?null:blockIO.split("/")[1].trim();
	}
}
