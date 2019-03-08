package com.sky.sm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sky.pub.Page;
import com.sky.pub.Result;
import com.sky.pub.ResultUtil;
import com.sky.pub.common.exception.ResultException;
import com.sky.sm.bean.PropertyValueEntity;
import com.sky.sm.bean.PropertyValueHistoryEntity;
import com.sky.sm.bean.req.PropertyValueReqEntity;
import com.sky.sm.service.PropertyValueService;

/**
 *   	属性值操作接口
 * @author 王帆
 * @date  2019年3月7日 下午2:18:52
 */
@RestController
@RequestMapping("/property")
public class PropertyValueController {
	
	@Autowired
	private PropertyValueService  propertyValueService;
	
	@RequestMapping(value="page",method=RequestMethod.POST)
	public Result<Page<PropertyValueEntity>> queryPageProperty(@RequestBody PropertyValueReqEntity property) {
		return ResultUtil.getOk(propertyValueService.queryPageOfProperty(property));
	}
	@RequestMapping(value="add",method=RequestMethod.POST)
	public Result<Boolean> addProperty(@RequestBody PropertyValueEntity property) throws ResultException {
		return ResultUtil.getOk(propertyValueService.addProperty(property));
	}
	@RequestMapping(value="update",method=RequestMethod.POST)
	public Result<Boolean> updateProperty(@RequestBody PropertyValueEntity property) throws ResultException {
		return ResultUtil.getOk(propertyValueService.updateProperty(property));
	}
	
	@RequestMapping("query")
	public Result<PropertyValueEntity> queryByID(Integer id) {
		return ResultUtil.getOk(propertyValueService.queryById(id));
	}
	@RequestMapping("query/his")
	public Result<List<PropertyValueHistoryEntity>> queryByPID(Integer id) {
		return ResultUtil.getOk(propertyValueService.queryByPId(id));
	}
}
