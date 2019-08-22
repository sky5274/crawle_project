package com.sky.data.handel;

import java.util.List;
import com.alibaba.fastjson.JSONObject;

/**
 * 批处理数据收集器
 * @author 王帆
 * @date  2019年7月21日 下午4:15:02
 */
public interface BatchDataCollectHandel {
	
	/**
	 * 保存收集数据
	 * @param taskId
	 * @param array
	 * @return  filepath
	 * @author 王帆
	 * @date 2019年7月21日 下午4:24:43
	 */
	public String save(String taskId,List<JSONObject> array);
}
