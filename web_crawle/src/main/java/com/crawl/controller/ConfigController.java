package com.crawl.controller;

import java.util.Arrays;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.crawl.config.service.MenuService;

@Controller
@RequestMapping("/config")
public class ConfigController {
	@Resource
	private MenuService menuService;
	
	
	@RequestMapping("main")
	public String goConfigMainPage(Model mod) {
		mod.addAttribute("menu", menuService.getMenuNode());
		return "/config/main";
	}
	@RequestMapping("setting")
	public String goConfigSetingPage() {
		return "/config/setting";
	}
}
