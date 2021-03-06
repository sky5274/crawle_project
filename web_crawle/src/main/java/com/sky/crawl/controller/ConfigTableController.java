package com.sky.crawl.controller;

import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sky.crawl.config.service.ConfigTableService;
import com.sky.crawl.data.config.dao.entity.ConfigTableEntity;
import com.sky.pub.Page;
import com.sky.pub.Result;
import com.sky.pub.ResultCode;
import com.sky.pub.ResultUtil;
import javax.servlet.http.HttpServletRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.sky.pub.common.exception.ResultException;

/**
 * 数据表定义
 * @author 王帆
 * @date  2018年12月26日 下午3:57:39
 */
@RestController
@RequestMapping("/config/table")
public class ConfigTableController {
	@Resource
	private ConfigTableService configTableService;
	
	@RequestMapping("/page")
	public Result<Page<ConfigTableEntity>> queryUserDefinTableByPage(ConfigTableEntity table){
		return ResultUtil.getOk(ResultCode.OK, configTableService.getConfigTablePage(table));
	}
	
	@RequestMapping(value="/add" ,method=RequestMethod.POST)
	public Result<ConfigTableEntity> addTable(@RequestBody ConfigTableEntity table) throws ResultException{
		return ResultUtil.getOk(ResultCode.OK, configTableService.addConfigTable(table));
	}
	
	@RequestMapping(value="/modfiy" ,method=RequestMethod.POST)
	public Result<ConfigTableEntity> modifyTable(@RequestBody ConfigTableEntity table) throws ResultException{
		return ResultUtil.getOk(ResultCode.OK, configTableService.updateConfigTable(table));
	}
	
	@RequestMapping(value="/del")
	public Result<Boolean> delTable(@RequestBody ConfigTableEntity table) throws ResultException{
		return ResultUtil.getOk(ResultCode.OK, configTableService.delConfigTable(table));
	}
	
	@RequestMapping(value="/sql/createtable")
	public Result<String> createTableSql(ConfigTableEntity table) throws ResultException{
		return ResultUtil.getOk(ResultCode.OK, configTableService.getConfigTableCreateSql(table));
	}
	
	@RequestMapping(value="/sql/temp/createtable")
	public Result<String> createTempTableSql(@RequestBody ConfigTableEntity table) throws ResultException{
		return ResultUtil.getOk(ResultCode.OK, configTableService.getTableCreateConfigSqlString(table));
	}
	
	@RequestMapping("/mod/page")
	public ModelAndView getModifyPage(Integer type,HttpServletRequest req) {
		ModelAndView  view=new ModelAndView("config/table/addTable");
		String id=req.getParameter("id");
		if(!StringUtils.isEmpty(id)) {
			view.addObject("table",configTableService.getConfigTableById(Integer.valueOf(id)));
		}
		view.addObject("type", type);
		return view;
	}
	
	@RequestMapping("/cart/page")
	public ModelAndView getCartPage(Integer id) {
		ModelAndView  view=new ModelAndView("config/table/cartTable");
		ConfigTableEntity table = configTableService.getConfigTableById(id);
		view.addObject("table",table);
		try {
			view.addObject("sql", configTableService.getTableCreateConfigSqlString(table));
		} catch (ResultException e) {
			e.printStackTrace();
		}
		return view;
	}
}
