package com.sky.sm.controller;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.sky.pub.Result;
import com.sky.pub.ResultCode;
import com.sky.pub.ResultUtil;
import com.sky.sm.bean.ProjectInfoBean;
import com.sky.sm.bean.ProjectPropertyBean;
import com.sky.sm.bean.PropertyValueEntity;
import com.sky.sm.service.ProjectInfoRedisService;
import com.sky.sm.service.PropertyValueService;

@RestController
@RequestMapping("project")
public class TempProjectController {
	@Resource
	private ProjectInfoRedisService  projectInfoRedisService;
	@Resource
	private PropertyValueService propertyValueService;
	
	/**
	 * 项目注册
	 * @param project
	 * @param req
	 * @return
	 * @author 王帆
	 * @date 2019年3月8日 下午3:48:30
	 */
	@RequestMapping(value="/regist",method=RequestMethod.POST)
	public Result<ResultCode> registProject(@RequestBody ProjectInfoBean project,HttpServletRequest req) {
		project.setIp(req.getRemoteHost());
		projectInfoRedisService.registProject(project);
		return ResultUtil.getOk(ResultCode.OK);
	}
	
	/**
	 * 项目卸载
	 * @param project
	 * @return
	 * @author 王帆
	 * @date 2019年3月8日 下午3:48:20
	 */
	@RequestMapping(value="/dump")
	public Result<ResultCode> dumpProject(ProjectInfoBean project) {
		projectInfoRedisService.dumpProject(project);
		return ResultUtil.getOk(ResultCode.OK);
	}
	
	@RequestMapping("/{service}/{profiles}/{version}")
	public Result<List<ProjectInfoBean>> queryProjectInfo(@PathVariable("service")String service,@PathVariable("profiles")String profiles,@PathVariable("version")String version) {
		return ResultUtil.getOk(projectInfoRedisService.queryInfo(service,profiles.split(","),version));
	}
	@RequestMapping("/{service}/{profiles}")
	public Result<List<ProjectInfoBean>> queryProjectInfo(@PathVariable("service")String service,@PathVariable("profiles")String profiles) {
		return ResultUtil.getOk(projectInfoRedisService.queryInfo(service,profiles.split(","),null));
	}
	@RequestMapping("/{service}")
	public Result<List<ProjectInfoBean>> queryProjectInfo(@PathVariable("service")String service) {
		return ResultUtil.getOk(projectInfoRedisService.queryInfo(service,null,null));
	}
	
	@RequestMapping("property")
	public Result<String>  getProperty(String key,ProjectInfoBean project) {
		return ResultUtil.getOk(propertyValueService.getPropertyValue(getPropertyValue(key,project)));
	}
	
	@RequestMapping("/query/properties")
	public Result<List<ProjectPropertyBean>> getProjectProperty(ProjectInfoBean project) {
		return ResultUtil.getOk(propertyValueService.getProperties(getPropertyValue(null, project)));
	}
	
	private PropertyValueEntity getPropertyValue(String key,ProjectInfoBean project) {
		PropertyValueEntity property=new PropertyValueEntity();
		property.setKey(key);
		property.setProject(project.getServiceName());
		property.setProfile(project.getProfile());
		property.setVersionCode(project.getVersion());
		return property;
	}
	
}
