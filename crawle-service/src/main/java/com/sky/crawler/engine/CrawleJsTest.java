package com.sky.crawler.engine;



public class CrawleJsTest {
	public static void main(String[] args) throws Exception {
		HtmlParseJsCrawler crawler = new HtmlParseJsCrawler("crawle/test", true);
		crawler.addSeed("https://blog.csdn.net/");
		crawler.addRegex("https://blog\\.csdn\\.net/.*");
		crawler.loadjsFile("/js/crawle.js");
		crawler.setAutoParse(true);
		crawler.setThreads(50);
		crawler.getConf().setTopN(100);
		crawler.start(3);
	}
}
