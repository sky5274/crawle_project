package com.sky.data.factory;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import com.sky.data.bean.FunctionBean;

/**
 * function  公式生成工厂：将公式分组进行整理，生成公式以及依赖的子集公式组
 * @author 王帆
 * @date  2019年8月1日 下午2:04:08
 */
public class FunctionFactory {
	public static  String functionRegix="(?<=\\()([\\S]+)(?=\\))";
	public static  String SumfunctionRegix="(avg|sum)(\\([\\S]+\\))";
	public static  String SumfunctionRegixT="(avg|sum)(\\([^}]+\\))";
	public static Pattern functionPattern = Pattern.compile(functionRegix);
	public static Pattern functionWithFormatPattern = Pattern.compile("(\\([\\S]+\\))");
	public static Pattern sumFunctionPattern = Pattern.compile(SumfunctionRegix);
	public static Pattern sumFunctionTPattern = Pattern.compile(SumfunctionRegixT);
	private static String functionPrefix="(";
	private static String functionSuffix=")";

	public static List<FunctionBean> bulid(String function) throws Exception{
		if(StringUtils.isEmpty(function)) {
			throw new Exception("function is null");
		}
		List<String> functionArray = Arrays.asList(function.split(";"));
		return functionArray.stream().map(f->{
			return parseFunction(f);
		}).collect(Collectors.toList());
	}
	
	private static boolean hasFunction(String func) {
		return func!=null && func.contains("(");
	}
	
	private static FunctionBean parseFunction(String function) {
		function=function.trim();
		Matcher functionmatch = functionPattern.matcher(function);
		if(!functionmatch.find()) {
			throw new RuntimeException("function:["+function+"] is fromat error");
		}
		FunctionBean funcBean=new FunctionBean();
		funcBean.setFileds(functionmatch.group());
		funcBean.setFunction(function);
		int rindex = funcBean.getFileds().indexOf("(");
		int lindex = funcBean.getFileds().indexOf(")");
		if(lindex<rindex &&  lindex>-1) {
			funcBean.setFileds(function.substring(0,function.indexOf(funcBean.getFileds())+funcBean.getFileds().length()+1));
		}else if((rindex<0 || lindex<0) && !(rindex<0 && lindex<0)){
			throw new RuntimeException("function:["+function+"] is fromat error");
		}
		int funcNameLIndex = function.indexOf(funcBean.getFileds());
		funcBean.setFuncName(function.substring(0,funcNameLIndex>-1?funcNameLIndex:0).replace("(", ""));
		int gbIndex=function.toUpperCase().lastIndexOf("GROUP BY");
		if(gbIndex>-1) {
			funcBean.setGroupBy(function.substring(gbIndex+8).trim());
		}
		String[] nickNames = function.substring(function.lastIndexOf(")")+1,gbIndex>-1?gbIndex:function.length()).split(" ");
		if(nickNames!=null && nickNames.length>1) {
			funcBean.setNickName(nickNames[nickNames.length-1]);
		}else {
			funcBean.setNickName(funcBean.getFuncName()+"("+funcBean.getFileds()+")");
		}
		if(hasFunction(funcBean.getFileds())) {
			funcBean.setChild(parseFunctionChild(funcBean));
		}
		return funcBean;
	}

	private static List<FunctionBean> parseFunctionChild(FunctionBean funcBean) {
		List<String> functions = spliteFunction(funcBean.getFileds());
		if(!CollectionUtils.isEmpty(functions)) {
			return functions.stream().map(f->{
				return parseFunction(f);
			}).collect(Collectors.toList());
		}
		return null;
	}
	
	/**
	 * 分割公式
	 * @param function
	 * @return
	 * @author 王帆
	 * @date 2019年8月1日 下午4:13:22
	 */
	private static List<String> spliteFunction(String function){
		List<String> functions=new LinkedList<String>();
		int index=0;
		int pindex=0;
		int sindex=0;
		/*
		 * 检索匹配（）
		 * 规则：1，查询（ 和），如果在两个之间发现多少个（，则向后推移多少个）位
		 */
		while(index>=0) {
			pindex=function.indexOf(functionPrefix, index-1);
			sindex=function.indexOf(functionSuffix, index);
			if(pindex<0 || sindex<0) {
				index=-1;
				continue;
			}
			if(pindex>sindex ) {
				throw new RuntimeException("fuction["+function+"] formate for () error");
			}
			
			int size=0;
			int tempindex=pindex;
			tempindex=function.indexOf(functionPrefix,tempindex+1);
			while(tempindex<sindex&& tempindex>-1) {
				size++;
				tempindex=function.indexOf(functionPrefix,tempindex+1);
			}
			while(size>0) {
				tempindex=function.indexOf(functionPrefix,tempindex+1);
				if(tempindex>-1&& tempindex<sindex) {
					size++;
					int sindextemp = function.indexOf(functionSuffix, sindex+1);
					if(sindextemp>=0) sindex=sindextemp;
					tempindex=sindex;
				}else if(tempindex<0){
					int sindextemp = function.indexOf(functionSuffix, sindex+1);
					if(sindextemp>=0) sindex=sindextemp;
					size=0;
				}else {
					size--;
				}
			}
			if(index>=0 && sindex>=index) {
				String func = function.substring(index,function.length()>sindex?sindex+1:function.length());
				Pattern p = Pattern.compile("^([-|+|*|/])");
				Matcher fp = p.matcher(func);
				if(fp.find()) func=func.replace(fp.group(), "");
				//如果存在加减乘除符号且符号在第一个（的外面，则根据符号进行分割公式
				Pattern pft = Pattern.compile("([-|+|*|/])");
				Matcher fpft = pft.matcher(func);
				while(fpft.find()) {
					String fh = fpft.group();
					int fhi = func.indexOf(fh);
					if(fhi>-1 && fhi<func.indexOf("(")) {
						func=func.substring(fhi+1);
					}
				}
				functions.add(func);
				index=sindex+1;
			}else {
				index=-1;
			}
		}
		
		return functions;
	}
	
}
