package com.sky.test;

import java.util.List;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sky.data.bean.FunctionDataBean;
import com.sky.data.bean.FunctionDataPreBean;
import com.sky.data.bean.FunctionBean;
import com.sky.data.factory.FunctionFactory;
import com.sky.data.factory.FunctionValidFactory;
import com.sky.data.handel.BatchDataFunctionHandel;
import com.sky.data.handel.impl.BatchDataFunctionHandelImpl;

public class BatchDataFunctionHandelTest {
	
	@Test
	public void testDataBatch() {
		BatchDataFunctionHandel handel=new BatchDataFunctionHandelImpl();
		String datas="["
					+"{\"id\":\"2\",\"key\":\"test1\",\"price\":30,\"num\":23,\"group\":\"deg\"},"
					+"{\"id\":\"4\",\"key\":\"test1\",\"price\":30,\"num\":20,\"group\":\"deg\"},"
					+"{\"id\":\"5\",\"key\":\"test1\",\"price\":20,\"num\":13,\"group\":\"deg\"},"
					+"{\"id\":\"6\",\"key\":\"test2\",\"price\":200,\"num\":13,\"group\":\"deg\"},"
					+"{\"id\":\"7\",\"key\":\"test2\",\"price\":250,\"num\":13,\"group\":\"deg\"},"
				+ "]";
		
		FunctionDataPreBean req=new FunctionDataPreBean();
		req.setDatas(JSON.parseArray(datas,JSONObject.class));
//		req.setKeys("key");
//		req.setOutCountKeys("id,key,price,group");
//		req.setFunction("sum(num) as num");
		try {
			req.setFunctions(FunctionFactory.bulid("sum(num) as num;avg(price)"));
			FunctionDataBean count = handel.excute(req);
			System.err.println("handel count array: "+JSON.toJSONString(req));
			System.err.println("handel count result: "+JSON.toJSONString(count));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			FunctionDataBean count = handel.excute(req);
			System.err.println("handel count array: "+JSON.toJSONString(req));
			System.err.println("handel count result: "+JSON.toJSONString(count));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	@Test
	public void testFunctionFactory() {
//		String functions="sum(num) as num;avg(tag+amount) group by id,user_no;sdsde;tan(an+sum(val)+avg(del));sum(avg(val)-sum(size)+sin(rote+avg(deg))) group by rol";
		String functions="sum(num) as num;avg(tag+amount) group by id,user_no;SUM(VAL)+TAN(DEL) AS STVAL;tan(an+sum(val)+avg(del));sum(avg(val)-sum(size)*avg(vl)/sim(de)+sin(rote+avg(deg))) group by rol";
		try {
			List<FunctionBean> funcs = FunctionFactory.bulid(functions);
			System.err.println(JSON.toJSONString(funcs));
			for(FunctionBean f:funcs) {
				FunctionValidFactory.valid(f);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
