package com.sky.demo.data.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sky.demo.dao.FlowSqlMapper;
import com.sky.transaction.annotation.MTransaction;

@Service
public class DemoService {
	
	@Autowired
	private FlowSqlMapper sqlMapper;
	@Autowired
	private DemoSqlService sqlService;
	
	@MTransaction(timeout=30*1000)
	public List<Map<String, String>> queryTable(String table) {
		sqlService.queryTable(table);
		sqlMapper.excuteBySql("insert into demo (name) values('temp')");
		sqlMapper.excuteBySql("update demo set name='temp1' where name='temp'");
		return sqlMapper.queryBySql("select * from "+table);
	}
}
