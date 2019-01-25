package com.sky.crawler.engine;

import java.io.Writer;
import java.util.LinkedList;
import java.util.List;

import com.sky.crawler.service.CrawlerJsonService;
import com.sky.pub.ResultCode;
import com.sky.pub.common.exception.ResultException;
import com.sky.pub.util.SpringUtil;

public class JsonParseClassNameCrawler extends JsonParseClassCrawler{
	private String className;
	private Class<CrawlerJsonService> superClass=CrawlerJsonService.class;
	
	public JsonParseClassNameCrawler(String className) throws ResultException {
		super();
		this.className=className;
		bulid();
	}
	
	public JsonParseClassNameCrawler(String className, Writer writer) throws ResultException {
		super();
		this.className=className;
		bulid();
		super.setWriter(writer);
	}

	
	public JsonParseClassNameCrawler bulid() throws ResultException {
		if(className==null) {
			throw new ResultException(ResultCode.PARAM_VALID,"请确认调用的java Class");
		}
		try {
			Class<?> clazz = Class.forName(className);
			if(!isCrawlerSerivce(clazz)) {
				throw new ResultException(ResultCode.PARAM_VALID,"调用的 "+className+" is not extends "+superClass);
			}
			CrawlerJsonService crawlerService=(CrawlerJsonService) SpringUtil.getBean(clazz);
			super.setCrawlerService(crawlerService);
		} catch (ClassNotFoundException  e) {
			throw new ResultException(ResultCode.PARAM_VALID,className+"对应类不在系统中",e);
		}
		return this;
	}
	
	/**
	 * 判定是否继承
	 * @param clazz
	 * @return
	 * @author 王帆
	 * @date 2019年1月18日 下午3:02:49
	 */
	private boolean isCrawlerSerivce(Class<?> clazz) {
		boolean flag=false;
		List<Class<?>> superClasses = getAllSuperClass(clazz);
		for(Class<?> sup:superClasses) {
			if(sup.getName().equals(superClass.getName())) {
				flag=true;
			}
		}
		return flag;
	}
	
	private List<Class<?>> getAllSuperClass(Class<?> clazz) {
		List<Class<?>> supers=new LinkedList<Class<?>>();
		return getAllSuperClass(clazz,supers);
	}
	
	private List<Class<?>> getAllSuperClass(Class<?> clazz, List<Class<?>> supers) {
		if(!Object.class.getName().equals(clazz.getName())) {
			Class<?> superClass = clazz.getSuperclass();
			supers.add(superClass);
			return getAllSuperClass(superClass,supers);
		}
		return supers;
	}
	
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	
}
