package com.sky.sm.service;

import java.util.List;

import com.sky.pub.common.exception.ResultException;
import com.sky.sm.bean.ProjectInfoBean;

/**
 * 项目信息服务
 * @author 王帆
 * @date  2019年5月9日 上午10:13:59
 */
public interface ProjectInfoService {
	
	/**
	 * 新增项目信息
	 * @param project
	 * @return
	 * @author 王帆
	 * @date 2019年5月9日 上午10:19:31
	 */
	Integer addProJectInfo(ProjectInfoBean project) throws ResultException;
	
	/**
	 * 删除项目信息
	 * @param project
	 * @return
	 * @author 王帆
	 * @date 2019年5月9日 上午10:20:28
	 */
	Integer deleteProJectInfo(ProjectInfoBean project) throws ResultException;
	
	/**
	 * 查询项目信息
	 * @param project
	 * @return
	 * @author 王帆
	 * @date 2019年5月9日 上午10:19:52
	 */
	List<ProjectInfoBean> queryProject(ProjectInfoBean project);
}
