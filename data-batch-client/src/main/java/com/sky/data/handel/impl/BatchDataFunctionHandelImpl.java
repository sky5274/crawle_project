package com.sky.data.handel.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.sky.data.bean.FunctionDataBaseBean;
import com.sky.data.bean.FunctionDataBean;
import com.sky.data.bean.FunctionDataPreBean;
import com.sky.data.core.factory.ScriptEngineCacheBuilderFactory;
import com.sky.data.bean.FunctionBean;
import com.sky.data.factory.FunctionValidFactory;
import com.sky.data.handel.BatchDataFunctionHandel;
import com.sky.pub.util.ScriptUtil;
import com.sky.pub.util.SpringExpressionUtil;
import com.sky.rpc.annotation.RpcProvider;

/**
 * batch data function handel impl
 * @author 王帆
 * @date  2019年7月25日 上午8:42:04
 */
@RpcProvider
public class BatchDataFunctionHandelImpl implements BatchDataFunctionHandel{
	List<JSONObject> dataList=null;
	//统计数据
	Map<String, JSONObject> count=null;
	Log log=LogFactory.getLog(getClass());
	ScriptEngine engine=null;

	@Override
	public FunctionDataBean excute(FunctionDataPreBean countdata) throws Exception {
		count=new HashMap<String, JSONObject>();
		List<FunctionBean> func = countdata.getFunctions();
		dataList = countdata.getDatas();
		if(CollectionUtils.isEmpty(dataList) || CollectionUtils.isEmpty(func)) {
			return null;
		}
		if(countdata.getExt()!=null) {
			engine =null;
			try {
				engine = ScriptEngineCacheBuilderFactory.getFactory().build(countdata.getExt());
			} catch (Exception e) {
				throw e;
			}
		}
		FunctionDataBean countbean=new FunctionDataBean();
		countbean.setSize(dataList.size());
		long t1 = System.currentTimeMillis();
		func.stream().forEach(f->{
			long fs = System.currentTimeMillis();
			//生成公式的数据统计字段
			try {
				excuteFunction(dataList,f);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			log.debug("function "+f.getFunction()+" cost:"+(System.currentTimeMillis()-fs));
			//将公式的统计机制查询出来汇总到结果中
			JSONObject data = count.get(f.getFunction());
			if(data !=null) {
				if(StringUtils.isEmpty(f.getGroupBy())) {
					FunctionDataBaseBean val = data.getObject("value", FunctionDataBaseBean.class);
					if(!StringUtils.isEmpty(f.getFuncName()) &&f.getFuncName().toLowerCase().equals("avg")){
						val.setValue(val.getValue().divide(new BigDecimal(val.getSize())));
					}
					val.setName(f.getNickName());
					countbean.put(f.getFunction(),val);
				}else {
					for(String key:data.keySet()) {
						FunctionDataBaseBean val = data.getObject(key, FunctionDataBaseBean.class);
						if(!StringUtils.isEmpty(f.getFuncName()) &&f.getFuncName().toLowerCase().equals("avg")){
							val.setValue(val.getValue().divide(new BigDecimal(val.getSize())));
						}
						val.setName(f.getNickName());
						countbean.put(f.getFunction(),key,val);
					}
				}
			}
		});
		log.debug("data list count cost:"+(System.currentTimeMillis()-t1));
		return countbean;
	}

	private void excuteFunction(List<JSONObject> dataList, FunctionBean function) throws Exception {
		if(function!=null) {
			if(!CollectionUtils.isEmpty(function.getChild())) {
				function.getChild().stream().forEach(f->{
					try {
						excuteFunction(dataList,f);
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				});
			}
			if(!StringUtils.isEmpty(function.getFunction())) {
				for(JSONObject d:dataList) {
					if(!CollectionUtils.isEmpty(function.getChild())) {
						for(FunctionBean f:function.getChild()) {
							//封装公式计算结果
							d=setDataTempValue(d,f);
						}
					}
					//执行公式计算
					setFunctionData(d,function);
				}
			}

		}
	}

	/**
	 * 执行公式计算字段
	 * @param data
	 * @param function
	 * @author 王帆
	 * @throws Exception 
	 * @date 2019年8月3日 下午1:59:41
	 */
	private void setFunctionData(JSONObject data, FunctionBean function) throws Exception {
		BigDecimal val =new BigDecimal(SpringExpressionUtil.parse(function.getFileds(), data).toString());
		FunctionDataBaseBean dc;
		JSONObject countval = count.get(function.getFunction());
		if(countval==null) {
			countval=new JSONObject();
		}
		String key=StringUtils.isEmpty(function.getGroupBy())?"value":getGroupByKey(function.getGroupBy(),data);
		dc=countval.getObject(key, FunctionDataBaseBean.class);
		if(dc==null) {
			dc=new FunctionDataBaseBean();
			dc.setSize(0);
			dc.setValue(new BigDecimal(0));
		}
		dc.setSize(dc.getSize()+1);

		if(FunctionValidFactory.isSumFunction(function.getFuncName())) {
			//汇总公式（元素累加）
			dc.setValue(dc.getValue().add(val));
		}else {
			//其他
			if(engine==null || !ScriptUtil.hasScriptFunction(engine, function.getFuncName())) {
				throw new Exception("the function: "+function.getFunction()+" is not support");
			}
			if(FunctionValidFactory.isSumFunction(engine, function.getFuncName())){
				BigDecimal value = ScriptUtil.invoke(engine, function.getFuncName(), val,data,dc.getValue());
				if(value==null) {
					throw new Exception("the function: "+function.getFunction()+" invoke to value is null");
				}
				dc.setValue(val);
			}
		}

		countval.put(key, dc);
		count.put(function.getFunction(), countval);
	}

	/**
	 * 根据function 向data中注入数值
	 * @param data
	 * @param function
	 * @return
	 * @author 王帆
	 * @throws ScriptException 
	 * @date 2019年8月3日 下午1:58:57
	 */
	private JSONObject setDataTempValue(JSONObject d, FunctionBean f) throws ScriptException {
		JSONObject val = count.get(f.getFunction());
		if(val!=null) {
			/*
			 * 1：先从本地缓存中获取本批次计算的（sum类型）数据
			 * 2：如果未获取则在script 引擎中进行数据计算
			 */
			FunctionDataBaseBean dc=null;
			String key=StringUtils.isEmpty(f.getGroupBy())?"value":getGroupByKey(f.getGroupBy(),d);
			dc=val.getObject(key,  FunctionDataBaseBean.class);
			if(dc!=null) {
				d.put(f.getFunction(), dc);
			}else {
				//script 引擎数据计算
				BigDecimal filed_v =new BigDecimal(SpringExpressionUtil.parse(f.getFileds(), d).toString());
				BigDecimal value = ScriptUtil.invoke(engine, f.getFuncName(), filed_v,d);
				d.put(f.getFunction(), value);
			}

		}
		return d;
	}

	/**
	 * group by 的值
	 * @param groupBy
	 * @param data
	 * @return
	 * @author 王帆
	 * @date 2019年8月3日 下午1:58:31
	 */
	private String getGroupByKey(String groupBy,JSONObject data) {
		String[] gbs = groupBy.split(",");
		List<String> gkeys=new LinkedList<>();
		for(String g:gbs) {
			gkeys.add(data.getString(g));
		}
		return String.join(",", gkeys);
	}

	/**
	 * 汇总数据，数字列，定义公式
	 * @param d
	 * @param value
	 * @return
	 * @author 王帆
	 * @param outFields    排除字段
	 * @param targetFields 目标字段
	 * @param func 
	 * @date 2019年7月25日 下午2:44:53
	 */
	private JSONObject countFiled(JSONObject d, JSONObject value, List<FunctionBean> func) {

		Set<String> kfeild = d.keySet();
		if(kfeild!=null) {
			if(!func.isEmpty()) {
				//按公式汇总数据
				func.stream().forEach(f->{
					try {
						BigDecimal val =new BigDecimal(SpringExpressionUtil.parse(f.getFileds(), d).toString());
						BigDecimal vals = value.getBigDecimal(f.getFunction());
						if(vals!= null) {
							val=val.add(vals);
						}
						value.put(f.getFunction(), val);
					} catch (Exception e) {
					}
				});
			}
		}
		return value;
	}

}
