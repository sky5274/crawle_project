package com.sky.sm.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sky.sm.bean.ProjectInfoBean;

@Service
public interface ProjectInfoRedisService {
	
	/**
	 * 注册项目
	 * @param info
	 * @author 王帆
	 * @date 2019年3月4日 下午3:45:38
	 */
	public void registProject(ProjectInfoBean info);
	
	/**
	 * 卸载项目
	 * @param info
	 * @author 王帆
	 * @date 2019年3月4日 下午3:46:25
	 */
	public void dumpProject(ProjectInfoBean info);
	
	/**
	 * 	查询项目信息以及接口 
	 * @param service
	 * @param profiles
	 * @param version
	 * @author 王帆
	 * @return 
	 * @date 2019年3月5日 下午2:22:52
	 */
	public List<ProjectInfoBean> queryInfo(String service, String[] profiles, String version);
}
