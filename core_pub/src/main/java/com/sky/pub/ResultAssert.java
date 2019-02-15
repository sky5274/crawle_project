package com.sky.pub;

import org.springframework.boot.logging.LogLevel;

import com.sky.pub.common.exception.ResultException;

public class ResultAssert {
	
	/**
	 * 如果 istrue==true  则抛出ResultException
	 * @param isture
	 * @param msg
	 * @throws ResultException
	 * @author 王帆
	 * @date 2018年12月28日 下午1:13:31
	 */
	public static void isFalse(boolean isture,String msg) throws ResultException {
		isFalse(isture, ResultCode.VALID,msg);
	}
	
	/**
	 * 如果 istrue==true  则抛出ResultException
	 * @param isture
	 * @param msg
	 * @param level
	 * @throws ResultException
	 * @author 王帆
	 * @date 2018年12月28日 下午1:14:13
	 */
	public static void isFalse(boolean isture,String msg,LogLevel level) throws ResultException {
		isFalse(isture, ResultCode.VALID,msg,level);
	}
	
	/**
	 * 如果 istrue==true  则抛出ResultException
	 * @param isture
	 * @param code
	 * @param msg
	 * @throws ResultException
	 * @author 王帆
	 * @date 2018年12月28日 下午1:14:21
	 */
	public static void isFalse(boolean isture,ResultCode code,String msg) throws ResultException {
		isFalse(isture, code, msg, LogLevel.INFO);
	}
	
	/**
	 * 如果 istrue==true  则抛出ResultException
	 * @param isture
	 * @param code
	 * @param msg
	 * @param level
	 * @throws ResultException
	 * @author 王帆
	 * @date 2018年12月28日 下午1:14:35
	 */
	public static void isFalse(boolean isture,ResultCode code,String msg,LogLevel level) throws ResultException {
		if(isture) {
			throw new ResultException(code, msg,level);
		}
	}
	
	/**
	 * 如果 obj==null  则抛出ResultException
	 * @param obj
	 * @param msg
	 * @throws ResultException
	 * @author 王帆
	 * @date 2018年12月28日 下午1:14:44
	 */
	public static void isEmpty(Object obj,String msg) throws ResultException {
		isFalse(obj==null,ResultCode.VALID,msg);
	}
	
	/**
	 * 如果 obj==null  则抛出ResultException
	 * @param obj
	 * @param code
	 * @param msg
	 * @throws ResultException
	 * @author 王帆
	 * @date 2018年12月28日 下午1:15:05
	 */
	public static void isEmpty(Object obj,ResultCode code,String msg) throws ResultException {
		isFalse(obj==null,code,msg);
	}
	
	/**
	 * 如果 obj==null  则抛出ResultException
	 * @param obj
	 * @param msg
	 * @param level
	 * @throws ResultException
	 * @author 王帆
	 * @date 2018年12月28日 下午1:15:13
	 */
	public static void isEmpty(Object obj,String msg,LogLevel level) throws ResultException {
		isFalse(obj==null,ResultCode.VALID,msg,level);
	}
	
	/**
	 * 如果 obj==null  则抛出ResultException
	 * @param obj
	 * @param code
	 * @param msg
	 * @param level
	 * @throws ResultException
	 * @author 王帆
	 * @date 2018年12月28日 下午1:15:21
	 */
	public static void isEmpty(Object obj,ResultCode code,String msg,LogLevel level) throws ResultException {
		isFalse(obj==null,code,msg,level);
	}
	
	/**
	 * 如果 str==null || str==""  则抛出ResultException
	 * @param str
	 * @param msg
	 * @throws ResultException
	 * @author 王帆
	 * @date 2018年12月28日 下午1:15:31
	 */
	public static void isBlank(String str,String msg) throws ResultException {
		isBlank(str, ResultCode.VALID,msg);
	}
	
	/**
	 * 如果 str==null || str==""  则抛出ResultException
	 * @param str
	 * @param code
	 * @param msg
	 * @throws ResultException
	 * @author 王帆
	 * @date 2018年12月28日 下午1:15:53
	 */
	public static void isBlank(String str,ResultCode code,String msg) throws ResultException {
		isFalse(str==null || "".equals(str),code,msg);
	}
	
	/**
	 * 如果 str==null || str==""  则抛出ResultException
	 * @param str
	 * @param msg
	 * @param level
	 * @throws ResultException
	 * @author 王帆
	 * @date 2018年12月28日 下午1:16:00
	 */
	public static void isBlank(String str,String msg,LogLevel level) throws ResultException {
		isBlank(str, ResultCode.VALID,msg,level);
	}
	
	/**
	 * 如果 str==null || str==""  则抛出ResultException
	 * @param str
	 * @param code
	 * @param msg
	 * @param level
	 * @throws ResultException
	 * @author 王帆
	 * @date 2018年12月28日 下午1:16:06
	 */
	public static void isBlank(String str,ResultCode code,String msg,LogLevel level) throws ResultException {
		isFalse(str==null || "".equals(str),code,msg,level);
	}
}
