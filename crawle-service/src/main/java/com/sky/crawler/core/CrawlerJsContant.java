package com.sky.crawler.core;

public class CrawlerJsContant {
	/**java8  javaScript  初始话文件*/
	public static String initJs="/js/init.evn.js";
	public static String urlRegex=" /^((ht|f)tps?):\\/\\/([\\w\\-]+(\\.[\\w\\-]+)*\\/)*[\\w\\-]+(\\.[\\w\\-]+)*\\/?(\\?([\\w\\-\\.,@?^=%&:\\/~\\+#]*)+)?/";
	
	/**获取资源文件的实际地址*/
	public static String getFilePath(String path) {
		return CrawlerJsContant.class.getResource(path).getFile();
	}
}
