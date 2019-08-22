package com.sky.data.handel;

import java.util.List;
import com.alibaba.fastjson.JSONObject;

/**
 * 批处理数据处理
 * @author 王帆
 * @date  2019年7月21日 下午4:31:04
 */
public interface BatchDataActionHandel {
	
	/**
	 * 数据处理
	 * @param array
	 * @return
	 * @author 王帆
	 * @date 2019年7月21日 下午4:31:22
	 */
	public int active(List<JSONObject> array);
}
