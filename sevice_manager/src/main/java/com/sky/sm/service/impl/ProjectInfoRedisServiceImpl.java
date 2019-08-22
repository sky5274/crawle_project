package com.sky.sm.service.impl;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.sky.pub.ResultAssert;
import com.sky.pub.common.exception.ResultException;
import com.sky.pub.service.impl.BaseRedisServiceImpl;
import com.sky.pub.util.ListUtils;
import com.sky.sm.bean.ProjectInfoBean;
import com.sky.sm.dao.ProjectInfoBeanMapper;
import com.sky.sm.service.ProjectInfoRedisService;
import com.sky.sm.service.ProjectInfoService;

@Service
public class ProjectInfoRedisServiceImpl extends BaseRedisServiceImpl<ProjectInfoBean> implements ProjectInfoRedisService,ProjectInfoService{

	@Autowired
	private ProjectInfoBeanMapper projectInfoBeanMapper;
	
	@Override
	public void registProject(ProjectInfoBean info) {
		doStringSet(getKey(info), info);
	}

	@Override
	public void dumpProject(ProjectInfoBean info) {
		doDeleteKey(getKey(info));
	}
	
	private String getKey(ProjectInfoBean info) {
		StringBuilder key=new StringBuilder();
		key.append("/").append(info.getServiceName())
			.append("-").append(info.getProfile()==null?"null":info.getProfile())
			.append("-").append(info.getVersion()==null?"null":info.getVersion());
		return key.toString();
	}
	
	/**
	 * 模糊查询key
	 * @param info
	 * @return
	 * @author 王帆
	 * @date 2019年3月5日 上午10:31:02
	 */
	private String getQueryKey(ProjectInfoBean info) {
		StringBuilder key=new StringBuilder();
		if(info!=null) {
			key.append("/").append(getNullTxt(info.getServiceName()))
				.append("-").append(getNullTxt(info.getProfile()))
				.append("-").append(getNullTxt(info.getVersion()));
		}else {
			key.append("*");
		}
		return key.toString();
	}
	
	@Override
	protected Class<ProjectInfoBean> getExtendClass() {
		return ProjectInfoBean.class;
	}
	private String getNullTxt(String txt) {
		return StringUtils.isEmpty(txt)?"*":txt;
	}
	
	@Override
	public List<ProjectInfoBean> queryInfo(String service, String[] profiles, String version) {
		List<ProjectInfoBean> list=new LinkedList<>();
		Set<String> keys=new HashSet<>();
		if(profiles!=null && profiles.length>0) {
			for(String profile:profiles) {
				StringBuilder key=new StringBuilder();
				key.append("/").append(service)
					.append("-").append(getNullTxt(profile))
					.append("-").append(getNullTxt(version));
				keys.addAll(doGetObjKeys(key.toString()));
			}
		}else {
			StringBuilder key=new StringBuilder();
				key.append("/").append(service)
					.append("-").append("*")
					.append("-").append(getNullTxt(version));
			keys.addAll(doGetObjKeys(key.toString()));
		}
		if(!keys.isEmpty()) {
			for(String key:keys) {
				list.add(doGet(key));
			}
		}
		return list;
	}

	@Override
	public Integer addProJectInfo(ProjectInfoBean project) throws ResultException{
		ResultAssert.isBlank(project.getServiceName(), "项目的服务名称为空");
		ResultAssert.isBlank(project.getProfile(), "项目的服务概述为空");
		ProjectInfoBean newproject=new ProjectInfoBean();
		BeanUtils.copyProperties( project,newproject);
		project.setVersion(null);
		List<ProjectInfoBean> newprojects = projectInfoBeanMapper.selectByProject(project);
		//追加版本号
		if(!ListUtils.isEmpty(newprojects)) {
			newproject.appendVersion(newprojects.get(0).getVersion());
		}
		return projectInfoBeanMapper.insert(newproject);
	}

	@Override
	public Integer deleteProJectInfo(ProjectInfoBean project) throws ResultException{
		ResultAssert.isBlank(project.getServiceName(), "项目的服务名称为空");
		ResultAssert.isBlank(project.getProfile(), "项目的服务概述为空");
		return projectInfoBeanMapper.deleteProject(project);
	}

	@Override
	public List<ProjectInfoBean> queryProject(ProjectInfoBean project) {
		return projectInfoBeanMapper.selectByProject(project);
	}

}
