package com.sky.sm.service.impl;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.sky.sm.bean.ProjectInfoBean;
import com.sky.sm.service.ProjectInfoRedisService;

@Service
public class ProjectInfoRedisServiceImpl extends BaseRedisServiceImpl<ProjectInfoBean> implements ProjectInfoRedisService{

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
			key.append("/").append(info.getServiceName()==null?"*":info.getServiceName())
				.append("-").append(info.getProfile()==null?"*":info.getProfile())
				.append("-").append(info.getVersion()==null?"*":info.getVersion());
		}else {
			key.append("*");
		}
		return key.toString();
	}
	
	@Override
	protected Class<ProjectInfoBean> getExtendClass() {
		return ProjectInfoBean.class;
	}

	@Override
	public List<ProjectInfoBean> queryInfo(String service, String[] profiles, String version) {
		List<ProjectInfoBean> list=new LinkedList<>();
		Set<String> keys=new HashSet<>();
		if(profiles!=null && profiles.length>0) {
			for(String profile:profiles) {
				StringBuilder key=new StringBuilder();
				key.append("/").append(service)
					.append("-").append(profile)
					.append("-").append(version==null?"*":version);
				keys.addAll(doGetObjKeys(key.toString()));
			}
		}else {
			StringBuilder key=new StringBuilder();
				key.append("/").append(service)
					.append("-").append("*")
					.append("-").append(version==null?"*":version);
			keys.addAll(doGetObjKeys(key.toString()));
		}
		if(!keys.isEmpty()) {
			for(String key:keys) {
				list.add(doGet(key));
			}
		}
		return list;
	}

}
