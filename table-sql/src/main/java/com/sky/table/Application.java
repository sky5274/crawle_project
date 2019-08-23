package com.sky.table;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import com.sky.table.bean.DataSource;
import com.sky.table.handel.TableHandle;

/**
 * Hello world!
 *
 */
public class Application{
	public static void main( String[] args ) throws Exception{
		DataSource source=new DataSource("jdbc:mysql://localhost:3306/demo?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8&useSSL=false&allowPublicKeyRetrieval=true",
				"temp", "123456", "mysql");
		TableHandle handel = new TableHandle();

//		queryTableColume(handel,source);

//		querySqlExcute(handel,source);

		addTableData(handel,source);

	}

	public static void queryTableColume(TableHandle handel, DataSource source) {
		//查询表结构
		List<String> columns = handel.getTableColumus(source, "config");
		System.err.println(columns);
	}

	public static void querySqlExcute(TableHandle handel, DataSource source) throws Exception {
		String sql="select * from menu where version=#{version}";
		Map<String, Object> param=new HashMap<>();
		param.put("version", 0);
		//执行sql
		List<Map<String, Object>> results = handel.excute(source, sql,param);
		System.err.println(results);
	}

	public static void addTableData(TableHandle handel, DataSource source) {
		List<Map> datas=new LinkedList<>();
		Map<String, Object> val1=new HashMap<>();
		val1.put("key", "name");
		val1.put("value", "time");
		val1.put("ts", new Date());
		datas.add(val1);
		//向表增加数据
		handel.addData(source,"demo_test",datas);
	}

}
