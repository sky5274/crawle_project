package com.sky.sm.bean.req;

import com.sky.sm.bean.TraceLimitBean;

/**
 * 限流模糊查询请求参数
 * @author 王帆
 * @date  2019年5月11日 下午4:19:20
 */
public class TraceLimitReqBean extends TraceLimitBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//项目名称
	private String serverNameLike;
	//项目环境
	private String profileLike;
	//项目版本
	private String serverVersionLike;
	//限制次数
	private Integer countNum;
	//持续时间
	private Integer priodNum;
	private Boolean showGlobal=false;
	public String getServerNameLike() {
		return serverNameLike;
	}
	public void setServerNameLike(String serverNameLike) {
		this.serverNameLike = serverNameLike;
	}
	public String getProfileLike() {
		return profileLike;
	}
	public void setProfileLike(String profileLike) {
		this.profileLike = profileLike;
	}
	public String getServerVersionLike() {
		return serverVersionLike;
	}
	public void setServerVersionLike(String serverVersionLike) {
		this.serverVersionLike = serverVersionLike;
	}
	public Integer getCountNum() {
		return countNum;
	}
	public void setCountNum(Integer countNum) {
		this.countNum = countNum;
	}
	public Integer getPriodNum() {
		return priodNum;
	}
	public void setPriodNum(Integer priodNum) {
		this.priodNum = priodNum;
	}
	public boolean isShowGlobal() {
		return showGlobal;
	}
	public void setShowGlobal(Boolean showGlobal) {
		this.showGlobal = showGlobal;
	}
	
}
