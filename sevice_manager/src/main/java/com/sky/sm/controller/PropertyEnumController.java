package com.sky.sm.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.sky.pub.Page;
import com.sky.pub.PageRequest;
import com.sky.pub.Result;
import com.sky.pub.ResultUtil;
import com.sky.pub.common.exception.ResultException;
import com.sky.sm.bean.ProjectPropertyEnumBean;
import com.sky.sm.bean.PropertyEnumEntity;
import com.sky.sm.bean.PropertyEnumValueEntity;
import com.sky.sm.service.PropertyEnumService;

/**
 *   	属性值操作接口
 * @author 王帆
 * @date  2019年3月7日 下午2:18:52
 */
@RestController
@RequestMapping("/property/enum")
public class PropertyEnumController {
	
	@Autowired
	private PropertyEnumService  propertyEnumService;
	
	@RequestMapping(value="page/group",method=RequestMethod.POST)
	public Result<Page<PropertyEnumEntity>> queryEnumGroupPage(@RequestBody PageRequest<PropertyEnumEntity> enump) {
		return ResultUtil.getOk(propertyEnumService.queryEnumPage(enump));
	}
	
	/**
	 * 新增枚举分组
	 * @param enump
	 * @return
	 * @throws ResultException
	 * @author wangfan
	 * @date 2020年1月31日 下午9:53:41
	 */
	@RequestMapping(value="add/group",method=RequestMethod.POST)
	public Result<Integer> addPropertyEnumGroup(@RequestBody PropertyEnumEntity enump) throws ResultException {
		return ResultUtil.getOk(propertyEnumService.addEnumGroup(enump));
	}
	
	/**
	 * 枚举分组--更新
	 * @param property
	 * @return
	 * @throws ResultException
	 * @author 王帆
	 * @date 2019年5月14日 下午1:48:14
	 */
	@RequestMapping(value="update/group",method=RequestMethod.POST)
	public Result<Integer> updatePropertyEnumGroup(@RequestBody PropertyEnumEntity enump) throws ResultException {
		return ResultUtil.getOk(propertyEnumService.updateEnumGroup(enump));
	}
	
	
	@RequestMapping("delet/group")
	public Result<Integer> deleteEnumGroupByID(PropertyEnumEntity enump) {
		return ResultUtil.getOk(propertyEnumService.delEnumGroup(enump));
	}
	
	@RequestMapping("query/group")
	public Result<ProjectPropertyEnumBean> queryEnumGroupByID(Integer id) {
		return ResultUtil.getOk(propertyEnumService.queryEnumbById(id));
	}
	
	/**
	 * 枚举分组内容分页
	 * @param enump
	 * @return
	 * @author wangfan
	 * @date 2020年1月31日 下午10:28:42
	 */
	@RequestMapping(value="page/value",method=RequestMethod.POST)
	public Result<Page<PropertyEnumValueEntity>> queryEnumGroupValue(@RequestBody PageRequest<PropertyEnumValueEntity> enump) {
		return ResultUtil.getOk(propertyEnumService.queryEnumValuePage(enump));
	}
	
	/**
	 * 新增枚举
	 * @param enump
	 * @return
	 * @throws ResultException
	 * @author wangfan
	 * @date 2020年1月31日 下午9:53:41
	 */
	@RequestMapping(value="add/value",method=RequestMethod.POST)
	public Result<Integer> addPropertyEnumValue(@RequestBody PropertyEnumValueEntity enumv) throws ResultException {
		return ResultUtil.getOk(propertyEnumService.addEnumValue(enumv));
	}
	
	/**
	 * 更新枚举
	 * @param property
	 * @return
	 * @throws ResultException
	 * @author 王帆
	 * @date 2019年5月14日 下午1:48:14
	 */
	@RequestMapping(value="update/value",method=RequestMethod.POST)
	public Result<Integer> updatePropertyEnumValue(@RequestBody PropertyEnumValueEntity enump) throws ResultException {
		return ResultUtil.getOk(propertyEnumService.updateEnumValue(enump));
	}
	
	@RequestMapping(value="delete/value",method=RequestMethod.POST)
	public Result<Integer> deletePropertyEnumValue(@RequestBody PropertyEnumValueEntity enump) throws ResultException {
		return ResultUtil.getOk(propertyEnumService.delEnumValue(enump));
	}
	
	@RequestMapping("/get")
	public Result<List<ProjectPropertyEnumBean>>  getProperty(String enumNo,ProjectPropertyEnumBean enump) {
		return ResultUtil.getOk(propertyEnumService.getPropertyEnmuValue(enumNo,enump));
	}
	
	/**
	 * 查询项目的配置枚举
	 * @param project
	 * @return
	 * @author wangfan
	 * @date 2020年1月31日 下午10:00:35
	 */
	@RequestMapping("/for/project")
	public Result<List<ProjectPropertyEnumBean>> getProjectProperty(ProjectPropertyEnumBean enump) {
		return ResultUtil.getOk(propertyEnumService.queryProjectEnumList(enump));
	}
	
}
