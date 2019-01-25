package com.sky.pub.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *   	时间工具类
 * @author 王帆
 * @date  2019年1月24日 下午5:23:48
 */
public class DateUtil {
	public static String def_format="yyyy-MM-dd hh:mm:ss";
	private static SimpleDateFormat dateFormate=new SimpleDateFormat(def_format);
	
	public static String format(Date date) {
		return dateFormate.format(date);
	}
	public static String format(String format,Date date) {
		dateFormate.applyPattern(format);
		String date_str=dateFormate.format(date);
		dateFormate.applyPattern(def_format);
		return date_str; 
	}
	public static Date parse(String str) {
		try {
			return dateFormate.parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static Date parse(String format,String str) {
		Date date=null;
		try {
			dateFormate.applyPattern(format);
			date = dateFormate.parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			dateFormate.applyPattern(def_format);
		}
		return date;
	}
	
	public String getNowDate() {
		dateFormate.applyPattern("yyyy-MM-dd");
		String date = dateFormate.format(new Date());
		dateFormate.applyPattern(def_format);
		return date;
	}
}
