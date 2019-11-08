package com.sky.container.util;

import java.util.regex.Pattern;

public class DoubleParseUtil {
	static Pattern p = Pattern.compile("[(\\d+\\.\\d+)|\\d]");
	
	/**
	 * 	识别字符串的数字以外的文字
	 * @param str
	 * @return
	 * @author 王帆
	 * @date 2019年11月5日 下午5:04:23
	 */
	public static String matchString(String str) {
		return p.matcher(str).replaceAll("");
	}
	
	/**
	 *  	识别字符串中的数字int
	 * @param str
	 * @return
	 * @author 王帆
	 * @date 2019年11月5日 下午5:01:22
	 */
	public static Integer parseInt(String str) {
		return Integer.valueOf(mathchNum(str,matchString(str)));
	}
	
	/**
	 *  	识别字符串中的数字double
	 * @param str
	 * @return
	 * @author 王帆
	 * @date 2019年11月5日 下午5:01:22
	 */
	public static Double parseDoube(String str) {
		return Double.valueOf(mathchNum(str,matchString(str)));
	}
	
	private static String mathchNum(String target,String source) {
		StringBuffer str=new StringBuffer();
		if(target !=null) {
			for(String c:target.split("")) {
				if(!source.contains(c)) {
					str.append(c);
				}
			}
		}
		return str.toString();
	}
}