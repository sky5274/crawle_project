package com.sky.data.core.handel;

import java.math.BigDecimal;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import javax.script.ScriptEngine;
import javax.script.ScriptException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sky.data.bean.FunctionDataBaseBean;
import com.sky.data.bean.FunctionDataBean;
import com.sky.data.bean.FunctionDataPreBean;
import com.sky.data.callback.MultiMethodCallBack;
import com.sky.data.bean.DataBatchTaskExtEntity;
import com.sky.data.bean.FunctionBean;
import com.sky.data.core.bean.DataBatchBean;
import com.sky.data.core.factory.BatchFunctionDataCacheFactory;
import com.sky.data.core.factory.ScriptEngineCacheBuilderFactory;
import com.sky.data.factory.FunctionValidFactory;
import com.sky.data.handel.BatchDataActionHandel;
import com.sky.data.handel.BatchDataFunctionHandel;
import com.sky.pub.util.ListUtils;
import com.sky.pub.util.ScriptUtil;
import com.sky.rpc.core.RpcProxy;
import com.sky.rpc.zk.RpcConfig;
import com.sky.rpc.zk.RpcConfig.nodeData;

/**
 * 数据处理handel
 * @author 王帆
 * @date  2019年8月3日 下午2:56:42
 */
public abstract class AbstractDataOperatHandel {
	private Log log=LogFactory.getLog(getClass());
	private ScriptEngine engine;
	private boolean hasFilter=false;
	private boolean hasConvert=false;
	private String groupId;
	protected DataBatchTaskExtEntity taskContent;

	public AbstractDataOperatHandel(String groupId,DataBatchTaskExtEntity taskExt) throws Exception {
		this.groupId=groupId;
		if(taskExt!=null) {
			if(!StringUtils.isEmpty(taskExt.getFilterJs())) {
				hasFilter=true;
			}
			if(!StringUtils.isEmpty(taskExt.getConvertJs())) {
				hasConvert=true;
			}
			
		}else {
			taskExt=new DataBatchTaskExtEntity();
		}
		taskContent=taskExt;
		if(!hasClass(BatchDataActionHandel.class)) {
			throw new Exception("未找到[group:"+groupId+",taskid:"+taskExt.getTaskId()+"]数据处理器");
		}
		engine= ScriptEngineCacheBuilderFactory.getFactory().build(taskContent);
	}
	
	public  void handelData() {
		//1.数据获取（分布式数据收集端）
		List<DataBatchBean> datas = getAllBatchDatas();
		if(!CollectionUtils.isEmpty(datas)) {
			//流程处理获取的数据
			datas.stream().forEach(fd->{
				try {
					//执行文件数据处理
					invokeData(fd);
				} catch (Exception e) {
					log.error(e.getMessage(),e);
					//异常文件数据处理
					setExceptionBataData(fd);
				}
			});
		}
	}

	
	/**
	 * 处理数据
	 * @param fd
	 * @author 王帆
	 * @throws Exception 
	 * @date 2019年7月27日 上午10:57:53
	 */
	protected void invokeData(DataBatchBean fd) throws Exception {
		List<JSONObject> datas = fd.getDatas().toJavaList(JSONObject.class);
		//2.数据转换，过滤
		datas=filterAndConvertData(datas);
		
		//3.数据分批次
		List<List<JSONObject>> array=split(datas, taskContent.getSplitSize());
		
		if(!StringUtils.isEmpty(taskContent.getFunctions())) {
			//4.数据归类汇总（分布式数据汇总）,在有公式的情况下原因
			FunctionDataBean countionData = invokeFunctionData(array);
			if(countionData!=null) {
				setCountDataIntoArray(countionData,array);
			}
		}
		
		//5.数据处理（分布式处理端）
		activeBatchData(array);
		
	}
	
	/**
	 * 分批次处理梳理
	 * @param array
	 * @throws Exception
	 * @author 王帆
	 * @date 2019年8月5日 上午9:16:56
	 */
	private void activeBatchData(List<List<JSONObject>> array) throws Exception {
		List<BatchDataActionHandel> actionHandels = getAllTaskClass(BatchDataActionHandel.class);
		if(CollectionUtils.isEmpty(actionHandels)) {
			throw new Exception("find no  data action handels");
		}
		int handelsize = actionHandels.size();
		for(int i=0;i<array.size();i++) {
			int index=i>handelsize?i%handelsize:i;
			actionHandels.get(index).active(array.get(i));
		}
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
	 * 将计算数据封装到各个数据对象中
	 * @param countionData
	 * @param array
	 * @author 王帆
	 * @throws Exception 
	 * @date 2019年8月4日 下午7:01:15
	 */
	private void setCountDataIntoArray(FunctionDataBean countionData, List<List<JSONObject>> array) throws Exception {
		if(!CollectionUtils.isEmpty(countionData.getFuncData())) {
			for(List<JSONObject> list:array) {
				for(JSONObject d:list) {
					for(String key:countionData.getFuncData().keySet()) {
						FunctionDataBaseBean countd = countionData.getFuncData().get(key);
						if(countd!=null) {
							d.put(countd.getName(), countd.getValue());
						}
					}
					if(countionData.getGroupByFunction()!=null) {
						for(String function:countionData.getGroupByFunction().keySet()) {
							String fields=function.substring(function.toLowerCase().indexOf("group by")+8);
							String filedKey=getGroupByKey(fields, d);
							FunctionDataBaseBean fcountdata = countionData.getGroupByFunction().get(function).get(filedKey);
							if(fcountdata!=null) {
								d.put(fcountdata.getName(), fcountdata.getValue());
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * 过滤并转化数据
	 * @param datas
	 * @return
	 * @author 王帆
	 * @date 2019年8月5日 上午9:20:19
	 */
	private List<JSONObject> filterAndConvertData(List<JSONObject> datas) {
		if(hasFilter || hasConvert) {
			
			List<JSONObject> list = datas.stream()
					.filter(d->{
						//过滤
						if(hasFilter  && ScriptUtil.hasScriptFunction(engine, "filter")) {
							try {
								return  (Boolean)invoke("filter",d);
							} catch ( ScriptException e) {
								e.printStackTrace();
							}
						}
						return true;
					})
					.map(d->{
						//转换
						if(hasConvert && ScriptUtil.hasScriptFunction(engine, "convert")) {
							try {
								return JSON.parseObject(JSON.toJSONString(invoke("convert", d)));
							} catch ( ScriptException e) {
								e.printStackTrace();
							}
						}
						return d;
					}).collect(Collectors.toList());
			datas=list;
		}
		return datas;
	}
	
	private Object invoke(String func,Object ...param) throws ScriptException {
		return ScriptUtil.invoke(engine, "filter", param);
	}
	
	/**
	 * 
	 * @param array
	 * @throws Exception
	 * @author 王帆
	 * @return 
	 * @date 2019年8月3日 下午10:26:59
	 */
	private FunctionDataBean invokeFunctionData(List<List<JSONObject>> array) throws Exception {
		List<BatchDataFunctionHandel> countHandels = getDataCountHandel();
		if(CollectionUtils.isEmpty(countHandels)) {
			throw new Exception("batch data handel could not find");
		}
		int counthandelSize=countHandels.size();
		FunctionDataPreBean countpre=BatchFunctionDataCacheFactory.factory().build(taskContent);
		countpre.setExt(taskContent);
		List<FunctionDataBean> countDataList=new LinkedList<>();
		for(int i=0;i<array.size();i++) {
			countpre.setDatas(array.get(i));
			int index=i>counthandelSize?i%counthandelSize:i;
			countDataList.add(excuteCountFunctionWithMoreTimes(countHandels.get(index),countpre));
		}
		return countData(countDataList,countpre.getFunctions());
	}
	
	//单词执行
	private FunctionDataBean excuteCountFunction(BatchDataFunctionHandel batchDataFunctionHandel, FunctionDataPreBean countpre) throws Exception {
		return batchDataFunctionHandel.excute(countpre);
	}
	//允许5次出错
	private FunctionDataBean excuteCountFunctionWithMoreTimes(BatchDataFunctionHandel batchDataFunctionHandel, FunctionDataPreBean countpre) throws InterruptedException, ExecutionException {
		//多次计算，默认最多计算五次，获取计算结果
		return MultiMethodCallBack.methodCallBack(()->{
			try {
				//数据提供
				return excuteCountFunction(batchDataFunctionHandel,countpre);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}).excute();
	}
	
	/**
	 * 统计数据汇总
	 * @param countDataList
	 * @param functions
	 * @return
	 * @author 王帆
	 * @date 2019年8月4日 下午5:11:58
	 */
	private FunctionDataBean countData(List<FunctionDataBean> countDataList, List<FunctionBean> functions) {
		if(CollectionUtils.isEmpty(countDataList)) {
			return null;
		}
		FunctionDataBean data=countDataList.get(0);
		for(int i=1;i<countDataList.size();i++) {
			FunctionDataBean d = countDataList.get(i);
			
			data=setCountData(data,d,functions);
		}
		return data;
	}
	
	/**
	 * 根据公式汇总数据进行合并
	 * @param data
	 * @param newData
	 * @param functions
	 * @return
	 * @author 王帆
	 * @date 2019年8月4日 下午5:12:21
	 */
	private FunctionDataBean setCountData(FunctionDataBean data, FunctionDataBean newData, List<FunctionBean> functions) {
		data.setSize(data.getSize()+newData.getSize());
		Map<String, FunctionDataBaseBean> functionData = newData.getFuncData();
		Map<String, Map<String, FunctionDataBaseBean>> functionGroupByData = newData.getGroupByFunction();
		functionNameMap=null;
		for(String key:functionData.keySet()) {
			FunctionDataBaseBean fdata = functionData.get(key);
			//汇总统计数据
			data.put(key,setCountBaseData(data.getFuncData().get(key),fdata,getFunctionByNikeName(key, functions, 0)));
		}
		for(String key:functionGroupByData.keySet()) {
			//汇总groupby  数据
			Map<String, FunctionDataBaseBean> fgbdata = functionGroupByData.get(key);
			Map<String, FunctionDataBaseBean> databgd = data.getGroupByFunction().get(key);
			for(String kgb:fgbdata.keySet()) {
				FunctionDataBaseBean fdata = fgbdata.get(kgb);
				//汇总统计数据
				databgd.put(kgb,setCountBaseData(databgd.get(kgb),fdata,getFunctionByNikeName(key, functions, 0)));
			}
			data.setFuncData(fgbdata);
		}
		
		return data;
	}
	
	/**
	 * 公式具体数据汇总合并
	 * @param dataCountBaseBean
	 * @param fdata
	 * @param function
	 * @return
	 * @author 王帆
	 * @date 2019年8月4日 下午5:13:06
	 */
	private FunctionDataBaseBean setCountBaseData(FunctionDataBaseBean dataCountBaseBean, FunctionDataBaseBean fdata, FunctionBean function) {
		if(dataCountBaseBean==null) {
			dataCountBaseBean=fdata;
		}else {
			if(function!=null) {
				if(FunctionValidFactory.isSumFunction(function.getFuncName())) {
					dataCountBaseBean.setSize(dataCountBaseBean.getSize()+fdata.getSize());
					if("avg".equals(function.getFuncName().toLowerCase())){
						BigDecimal val1 = dataCountBaseBean.getValue().multiply(new BigDecimal(dataCountBaseBean.getSize()));
						BigDecimal val2 = fdata.getValue().multiply(new BigDecimal(fdata.getSize()));
						dataCountBaseBean.setValue(val1.add(val2));
					}else {
						dataCountBaseBean.setValue(dataCountBaseBean.getValue().add(fdata.getValue()));
					}
				}
			}
		}
		return dataCountBaseBean;
	}
	
	private Map<String, FunctionBean> functionNameMap=null;
	
	/**
	 * 根据昵称识别公式
	 * @param name
	 * @param functions
	 * @param type
	 * @return
	 * @author 王帆
	 * @date 2019年8月4日 下午5:13:34
	 */
	private FunctionBean getFunctionByNikeName(String name,List<FunctionBean> functions,int type) {
		if(functionNameMap!=null) {
			FunctionBean f = functionNameMap.get(name);
			if(f!=null) {
				return f;
			}
		}
		functionNameMap=new HashMap<>();
		for(FunctionBean f:functions) {
			boolean flag=false;
			if(name.equals(f.getNickName()) ) {
				if(type==0) {
					flag=StringUtils.isEmpty(f.getGroupBy());
				}else {
					flag=!StringUtils.isEmpty(f.getGroupBy());
				}
			}
			if(flag) {
				functionNameMap.put(name,f);
				return f;
			}
		}
		return null;
	}

	/**
	 * 分割数组
	 * @param list
	 * @param listLength
	 * @return
	 * @author 王帆
	 * @date 2019年8月4日 下午5:14:07
	 */
	private <T> List<List<T>> split(List<T> list ,Integer listLength){
		List<List<T>> arrays=new LinkedList<List<T>>();
		if(listLength==null) {
			listLength=1;
		}
		if(listLength>1 && !CollectionUtils.isEmpty(list)) {
			double size=list.size()/listLength;
			for(int i=0;i<=size;i++) {
				List<T> temp=new LinkedList<>();
				for(int index=i*listLength;index<(i+1)*listLength;i++) {
					temp.add(list.get(index));
				}
				arrays.add(temp);
			}
		}else {
			arrays.add(list);
		}
		return arrays;
	}
	
	protected String getBaseRpcPath() {
		return "/"+groupId+"/"+taskContent.getTaskId();
	}
	
	protected boolean hasClass(Class<?> clazz) {
		List<nodeData> nodes = RpcConfig.getRcpNodeDatas(getBaseRpcPath()+"/"+clazz.getName());
		return !ListUtils.isEmpty(nodes);
	}
	
	protected <T> List<T> getAllTaskClass(Class<T> clazz) {
		List<nodeData> nodes = RpcConfig.getRcpNodeDatas(getBaseRpcPath()+"/"+clazz.getName());
		List<T> list=new LinkedList<T>();
		for(nodeData n:nodes) {
			list.add(RpcProxy.getRemoteProxyObj(clazz, new InetSocketAddress(n.getIp(), n.getPort())));
		}
		return list;
	}
	
	
	/**
	 * 公式处理及统计方法获取方法
	 * @return
	 * @author 王帆
	 * @date 2019年8月3日 下午2:58:29
	 */
	protected abstract List<BatchDataFunctionHandel> getDataCountHandel();

	/**
	 * 出现异常时，处理方式
	 * @param data
	 * @author 王帆
	 * @throws Exception 
	 * @date 2019年8月3日 下午2:57:55
	 */
	protected abstract void setExceptionBataData(DataBatchBean data);

	/**
	 * 根据任务id获取所有要处理的数据
	 * @return
	 * @author 王帆
	 * @date 2019年7月26日 上午9:31:45
	 */
	protected abstract  List<DataBatchBean> getAllBatchDatas() ;

}
