package com.sky.demo.data.service;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.sky.demo.dao.FlowSqlMapper;
import com.sky.transaction.annotation.MTransaction;

@Component
public class DemoSqlService {
	@Autowired
	private FlowSqlMapper sqlMapper;

	@MTransaction(timeout=60*1000)
	public List<Map<String, String>> queryTable(String table) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				sqlMapper.queryBySql("select * from "+table);
			}
		}).start();
//		ConnectTransactionNodeFactory.connectGroupMap.values().stream().forEach((it)->{
//			System.err.println(it.getGroupId()+":"+it.getId());
//			it.getConnects().forEach((cns)->{
//				System.err.println(cns.getConnect());
//			});
//		});
		return sqlMapper.queryBySql("select * from "+table);
	}
}
