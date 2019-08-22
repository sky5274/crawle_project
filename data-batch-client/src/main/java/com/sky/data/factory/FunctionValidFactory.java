package com.sky.data.factory;

import java.util.Arrays;
import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.sky.data.bean.FunctionBean;

/**
 * 公式校验工厂：校验公式是否支持
 * @author 王帆
 * @date  2019年8月2日 上午9:39:26
 */
public class FunctionValidFactory {
	private static List<String> def_function=Arrays.asList("sum","avg");
	private static String def_sum_method="isSum";
	
	public static boolean isSumFunction(String function) {
		return StringUtils.isEmpty(function)?false: def_function.contains(function.toLowerCase());
	}
	
	/**
	 * demo:<pre>
	 * function sum(){
			this.isSum=function(){
				return 0;
			}; 
			return 1;
		}
	 * 
	 * </pre>
	 * @param engine
	 * @param function
	 * @return
	 * @author 王帆
	 * @date 2019年8月21日 下午1:20:41
	 */
	public static boolean isSumFunction(ScriptEngine engine,String function) {
		if(engine!=null && !StringUtils.isEmpty(function)) {
			String js=function+".hasOwnProperty('"+def_sum_method+"')";
			try {
				return (Boolean)engine.eval(js) && (Boolean) engine.eval(function+'.'+def_sum_method+"()");
			} catch (ScriptException e) {
			}
		}
		return false;
	}
	
	/**
	 * 校验信息
	 * @param func
	 * @throws Exception
	 * @author 王帆
	 * @date 2019年8月2日 上午10:28:36
	 */
	public static void valid(FunctionBean func) throws Exception{
		valid(func, 0);
	}
	private static void valid(FunctionBean func,int type) throws Exception{
		if(func!=null) {
			if(!StringUtils.isEmpty(func.getFunction())) {
				if(type==1 && StringUtils.isEmpty(func.getFuncName())) {
					throw new Exception("could not check function:"+func.getFunction()+" for method");
				}
				if(!StringUtils.isEmpty(func.getFuncName()) && !isSumFunction(func.getFuncName())) {
					throw new Exception("function:"+func.getFunction()+" for method is not Support");
				}
				if(!CollectionUtils.isEmpty(func.getChild())) {
					for(FunctionBean f:func.getChild()) {
						valid(f, 1);
					}
				}
			}
		}
	}
}
