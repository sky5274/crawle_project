package com.sky.crawl.controller;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sky.crawl.config.service.MenuService;
import com.sky.crawl.data.config.dao.ConfigSqlExcuteMapper;
import com.sky.pub.Result;
import com.sky.pub.ResultCode;
import com.sky.pub.ResultUtil;

@Controller
@RequestMapping("/config")
public class ConfigController {
	@Resource
	private MenuService menuService;
	@Resource
	private SqlSessionFactory sqlSessionFactory;
	@Resource
	private ConfigSqlExcuteMapper configSqlExcuteMapper;


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
	public String goConfigPagePath(@PathVariable("path") String path,@PathVariable("html")String html,HttpServletRequest req,Model mod) {
		Enumeration<String> params = req.getParameterNames();
		while(params.hasMoreElements()) {
			String key=params.nextElement();
			mod.addAttribute(key, req.getParameter(key));
		}
		return "/config/"+path+"/"+html;
	}

	@RequestMapping("config.js")
	public ResponseEntity<byte[]> getConfigJs(HttpServletRequest req) throws IOException {
		HttpHeaders headers = new HttpHeaders();    
		File file = new File(getClass().getResource("/static/js/com/config.js").getFile());
		BufferedReader read=new BufferedReader(new FileReader(file));
		StringBuilder str=new StringBuilder();
		String temp=null;
		while((temp=read.readLine())!=null) {
			str.append(temp);
		}
		headers.set(HttpHeaders.CONTENT_TYPE,"application/javascript");    
		headers.set("Accept-Ranges", "bytes"); 
		String url=req.getRequestURL().toString();
		String context=str.toString().replace("#{contextPath}", "\""+url.substring(0,url.indexOf(req.getServletPath()))+"\"");
		return new ResponseEntity<byte[]>( context.getBytes(), headers, HttpStatus.CREATED) ; 
	}

	@ResponseBody
	@RequestMapping(value="/sql/excute",produces=MediaType.APPLICATION_JSON_VALUE)
	public Result<Map<String, Object>> excuteSql(String query){
		Map<String, Object> map=new HashMap<>();
		try {
			map.put("results",excuteStatments(query.split(";")));
		} catch (Exception e) {
			map.put("exception", e.getClass().getName());
			String errmsg=e.getMessage();
			for(StackTraceElement trace:e.getStackTrace()) {
				errmsg+=trace;
			}
			map.put("errMsg", errmsg);
		}
		return ResultUtil.getOk(ResultCode.OK, map);
	}

	public List<Map<String, Object>> excuteStatments(String... stats) throws Exception{
		SqlSession session = sqlSessionFactory.openSession();
		Connection connect = session.getConnection();
		List<Map<String, Object>> list=new LinkedList<>();
		try {
			connect.setAutoCommit(false);
			for(String query :stats) {
				list.add(excutStatment(session,query));
			}
			connect.commit();
		} catch (Exception e) {
			connect.rollback();
			throw new Exception(e);
		}
		return list;
	}

	private Map<String, Object> excutStatment(SqlSession session, String query) {
		Map<String, Object> map=new HashMap<>();
		String temp = query.toUpperCase().trim();
		if(temp.startsWith("SELECT")) {
			List<Map<String, Object>> list = configSqlExcuteMapper.queryBySql(query);
			map.put("excute_size", list.size());
			map.put("results", list);
		}else {
			map.put("excute_size",configSqlExcuteMapper.excuteBySql(query));
		}
		return map;
	}
}
