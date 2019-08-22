package com.sky.sm.controller;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.sky.pub.Result;
import com.sky.pub.ResultCode;
import com.sky.pub.ResultUtil;
import com.sky.pub.common.exception.ResultException;
import com.sky.sm.bean.ProjectInfoBean;
import com.sky.sm.service.ProjectInfoRedisService;
import com.sky.sm.service.ProjectInfoService;

@RestController
@RequestMapping("project")
public class TempProjectController {
	@Resource
	private ProjectInfoRedisService  projectInfoRedisService;
	@Resource
	private ProjectInfoService projectInfoService;
	
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
		//添加项目信息
		try {
			projectInfoService.addProJectInfo(project);
		} catch (ResultException e) {
		}
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
	
	@RequestMapping("/info/{service}/{profiles}/{version:.+}")
	public Result<List<ProjectInfoBean>> queryProjectInfo(@PathVariable("service")String service,@PathVariable("profiles")String profiles,@PathVariable("version")String version) {
		return ResultUtil.getOk(projectInfoRedisService.queryInfo(service,profiles.split(","),version));
	}
	@RequestMapping("/info/{service}/{profiles}")
	public Result<List<ProjectInfoBean>> queryProjectInfo(@PathVariable("service")String service,@PathVariable("profiles")String profiles) {
		return ResultUtil.getOk(projectInfoRedisService.queryInfo(service,profiles.split(","),null));
	}
	@RequestMapping("/info/{service}")
	public Result<List<ProjectInfoBean>> queryProjectInfo(@PathVariable("service")String service) {
		return ResultUtil.getOk(projectInfoRedisService.queryInfo(service,null,null));
	}
	
	/**
	 * 手动添加项目信息
	 * @param project
	 * @return
	 * @author 王帆
	 * @throws ResultException 
	 * @date 2019年5月9日 上午11:51:47
	 */
	@PostMapping("/add")
	public Result<Integer> addProjectInfo(ProjectInfoBean project) throws ResultException{
		return ResultUtil.getOk(projectInfoService.addProJectInfo(project));
	}
	
	/**
	 * 手动删除项目信息
	 * @param project  （项目名称与项目分支）
	 * @return
	 * @author 王帆
	 * @throws ResultException 
	 * @date 2019年5月9日 上午11:52:17
	 */
	@PostMapping("/delete")
	public Result<Integer> deleteProjectInfo(ProjectInfoBean project) throws ResultException{
		return ResultUtil.getOk(projectInfoService.deleteProJectInfo(project));
	}
	
	/**
	 * 根据项目信息查询关联的项目
	 * @param project
	 * @return
	 * @author 王帆
	 * @date 2019年5月9日 上午11:52:32
	 */
	@RequestMapping("/querylike")
	public Result<List<ProjectInfoBean>> queryProjectInfolist(ProjectInfoBean project){
		return ResultUtil.getOk(projectInfoService.queryProject(project));
	}
}
