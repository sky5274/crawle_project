package com.sky.pub.util;

import java.util.Collection;

/**
 * 	集合工具类
 * @author 王帆
 * @date  2019年1月24日 下午5:22:48
 */
public class ListUtils {
	public static boolean isEmpty(Collection<?> list) {
		return list==null || list.isEmpty();
	}
}
