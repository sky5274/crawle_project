package com.sky.demo.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.sky.pub.Result;
import com.sky.pub.ResultUtil;

@RestController
public class PageController {
	
	@RequestMapping("/page/{name}")
	public ModelAndView goPage(@PathVariable String name) {
		return new ModelAndView(name);
	}
	@RequestMapping("/say/{word}")
	public Result<String> say(@PathVariable String word) {
		return ResultUtil.getOk("you say:"+word);
	}
	@RequestMapping("/talk")
	public Result<String> talk(String word) {
		return ResultUtil.getOk("you talk:"+word);
	}
	@RequestMapping("/writer")
	public Result<Map<String, String[]>> say(HttpServletRequest req) {
		return ResultUtil.getOk(req.getParameterMap());
	}
	
	@RequestMapping(value="do",method=RequestMethod.POST)
	public Result<Map<String, String[]>> dosome(HttpServletRequest req) {
		return ResultUtil.getOk(req.getParameterMap());
	}
}
