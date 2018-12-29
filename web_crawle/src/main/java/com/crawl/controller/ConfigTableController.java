package com.crawl.controller;

import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.crawl.config.service.ConfigTableService;
import com.crawl.data.config.dao.entity.ConfigTableEntity;
import com.sky.pub.Page;
import com.sky.pub.Result;
import com.sky.pub.ResultCode;
import com.sky.pub.ResultUtil;

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
}
