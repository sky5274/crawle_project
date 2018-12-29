package com.crawl.controller;


import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.crawl.config.service.MenuService;

@Controller
@RequestMapping("/config")
public class ConfigController {
	@Resource
	private MenuService menuService;
	
	
	@RequestMapping("/main")
	public String goConfigMainPage(Model mod) {
		mod.addAttribute("menu", menuService.getMenuNode());
		return "/config/main";
	}
	
	@RequestMapping("page/{path}")
	public String goConfigPage( @PathVariable("path") String path) {
		return "/config/"+path;
	}
	
	@RequestMapping("/page/{path}/{html}")
	public String goConfigPagePath(@PathVariable("path") String path,@PathVariable("html")String html) {
		return "/config/"+path+"/"+html;
	}
}
