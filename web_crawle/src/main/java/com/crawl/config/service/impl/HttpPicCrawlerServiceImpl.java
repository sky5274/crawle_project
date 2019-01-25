package com.crawl.config.service.impl;

import java.util.List;

import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.sky.crawler.service.CrawlerService;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;

/**
 *  http 爬虫页面
 * @author 王帆
 * @date  2019年1月21日 上午9:26:41
 */
@Service
public class HttpPicCrawlerServiceImpl extends CrawlerService{

	@Override
	public void crawler(Page page, CrawlDatums next) {
		writer(page.url());
//		writer(page.html());
		 List<Element> imgs = page.select("li img");
		 imgs.stream()
		 .forEach(img->{
			String src=img.attr("src");
			String name=img.attr("alt");
			if(StringUtils.isEmpty(name)) {
				writer(name+"\t\t"+src+"\n");	
			}
		 });
		
	}

}
