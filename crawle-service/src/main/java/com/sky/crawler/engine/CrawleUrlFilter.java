package com.sky.crawler.engine;

/**
 * 	爬虫url过滤
 * @author 王帆
 * @date  2019年1月12日 下午2:55:54
 */
public interface CrawleUrlFilter {
	boolean filterUrl(String url);
}
