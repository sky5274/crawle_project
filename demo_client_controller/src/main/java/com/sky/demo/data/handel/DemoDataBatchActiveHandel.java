package com.sky.demo.data.handel;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sky.data.handel.BatchDataActionHandel;
import com.sky.rpc.annotation.RpcProvider;


@RpcProvider(group="data_key",version="db3060eaaecb40ab87a7e2dbf72bd243")
public class DemoDataBatchActiveHandel implements BatchDataActionHandel{

	@Override
	public int active(List<JSONObject> array) {
		System.err.println(JSON.toJSONString(array));
		return array.size();
	}

}
