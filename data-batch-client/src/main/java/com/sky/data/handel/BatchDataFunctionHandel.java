package com.sky.data.handel;

import com.sky.data.bean.FunctionDataBean;
import com.sky.data.bean.FunctionDataPreBean;

/**
 * 批处理数据公式处理器
 * @author 王帆
 * @date  2019年7月25日 上午8:37:10
 */
public interface BatchDataFunctionHandel {
	
	/**
	 * 公式执行
	 * req:  key拼接， outFields:非统计字段，function  使用公式计算字段（使用";"分割）
	 * @param req
	 * @return
	 * @author 王帆
	 * @date 2019年7月25日 下午3:58:01
	 */
	public FunctionDataBean excute(FunctionDataPreBean req) throws Exception;
}
