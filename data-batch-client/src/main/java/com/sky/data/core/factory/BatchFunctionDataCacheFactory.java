package com.sky.data.core.factory;

import org.springframework.util.StringUtils;

import com.sky.data.bean.DataBatchTaskExtEntity;
import com.sky.data.bean.FunctionDataPreBean;
import com.sky.data.core.builder.FunctionDataPreBuilder;
import com.sky.pub.cache.LocalCacheManager;

/**
 * 本地缓存  任务公式数据
 * @author 王帆
 * @date  2019年8月17日 下午3:02:42
 */
public class BatchFunctionDataCacheFactory implements FunctionDataPreBuilder{
	private static String linkStr="_function_";
	private static BatchFunctionDataCacheFactory factory=null;
	
	/**
	 * 获取构建工厂
	 * @return
	 * @author 王帆
	 * @date 2019年8月17日 下午4:57:05
	 */
	public static BatchFunctionDataCacheFactory factory() {
		if(factory==null) {
			factory=new BatchFunctionDataCacheFactory();
		}
		return factory;
	}

	@Override
	public FunctionDataPreBean build(DataBatchTaskExtEntity task) throws Exception {
		String key = getFunctionBuliderKey(task);
		//使用本地缓存管理
		FunctionDataPreBean func = LocalCacheManager.get(key);
		System.err.println("cache key: "+key+" is null  :"+(func==null));
		if(func==null) {
			func=BatchFunctionDataFactory.factory().build(task);
			LocalCacheManager.put(key, func);
		}
		func.setDatas(null);
		return func;
	}
	
	/**
	 * 生成任务对应键值
	 * @param task
	 * @return
	 * @throws Exception
	 * @author 王帆
	 * @date 2019年8月17日 下午4:57:27
	 */
	private String getFunctionBuliderKey(DataBatchTaskExtEntity task) throws Exception {
		validTaskExt(task);
		StringBuilder str=new StringBuilder();
		str.append(task.getTaskId())
			.append(linkStr).append(task.getFunctions());
		return str.toString();
	}
	
	private void validTaskExt(DataBatchTaskExtEntity task) throws Exception {
		if(task==null) {
			throw new Exception("no task info");
		}
		if(StringUtils.isEmpty(task.getFunctions())) {
			throw new Exception("no task function info");
		}
	}
}
