package com.sky.sm.controller;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 页面通用控制器
 * @author 王帆
 * @date  2019年4月19日 下午2:21:33
 */

@Controller
@RequestMapping("/page")
public class PageController {
	
	/**
	 * 权限页面控制
	 * @param path
	 * @param req
	 * @param mod
	 * @return
	 * @author 王帆
	 * @date 2019年4月19日 下午2:23:38
	 */
	@RequestMapping("/{model}/{path}")
	public String goConfigPagePath(@PathVariable("path") String path,@PathVariable("model") String model,HttpServletRequest req,Model mod) {
		Enumeration<String> params = req.getParameterNames();
		while(params.hasMoreElements()) {
			String key=params.nextElement();
			mod.addAttribute(key, req.getParameter(key));
		}
		return model+"/"+path;
	}
}
