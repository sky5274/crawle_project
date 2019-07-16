package com.sky.flow.contant;

import java.util.HashMap;
import java.util.Map;

import org.mybatis.spring.annotation.MapperScan;

/**
 * 流程全局变量
 * @author 王帆
 * @date  2019年5月18日 下午3:55:17
 */
@MapperScan("com.sky.flow.dao")
public class FlowContant {
	public static String httpRegex="/^((ht|f)tps?):\\/\\/([\\w\\-]+(\\.[\\w\\-]+)*\\/)*[\\w\\-]+(\\.[\\w\\-]+)*\\/?(\\?([\\w\\-\\.,@?^=%&:\\/~\\+#]*)+)?/";
	
	public enum EVENTTYPE{
		in,out,back,user
	}
	
	public static final String NODE_TYPE_BASE = "base";

	/**节点类型：容器*/
	public static String NODE_TYPE_CONTAINER="container";
	
	private static Map<String, String> NODE_TYPE_MAP=new HashMap<>();
	static {
		NODE_TYPE_MAP.put("start", "开始");
		NODE_TYPE_MAP.put("end", "结束");
		NODE_TYPE_MAP.put(NODE_TYPE_BASE, "基础");
		NODE_TYPE_MAP.put("choose", "选择");
		NODE_TYPE_MAP.put("select", "条件");
		NODE_TYPE_MAP.put(NODE_TYPE_CONTAINER, "容器");
	}
	
	public static Map<String, String> getNodeTypeMap() {
		return NODE_TYPE_MAP;
	}
	
	public static boolean isNodeType(String type) {
		return NODE_TYPE_MAP.keySet().contains(type);
	}
	public static String getNodeType(String type) {
		return NODE_TYPE_MAP.get(type);
	}
}
