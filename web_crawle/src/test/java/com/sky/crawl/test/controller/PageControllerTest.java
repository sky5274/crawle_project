package com.crawl.test.controller;

import java.util.Arrays;
import java.util.List;
import javax.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.crawl.WebCrawleApplication;
import com.crawl.data.config.dao.MenuEntityMapper;
import com.crawl.test.MenuServicePageTest;
import com.crawl.util.RestTemplateUtil;

/**
 *	 页面测试
 * @author 王帆
 * @date  2018年12月27日 下午5:17:21
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT ,classes=WebCrawleApplication.class)
public class PageControllerTest extends RestTemplateUtil{
	Logger log=LoggerFactory.getLogger(getClass());
	
	@Resource
	private MenuEntityMapper menuMapper;
	@Autowired
	private TestRestTemplate restTemplate;

	/**
	 * 测试初始定义页面路径测试
	 * 
	 * @author 王帆
	 * @date 2018年12月27日 下午1:33:29
	 */
	@Test
	public void testDefinedPage() {
		List<String> pages = Arrays.asList("/config/main","/config/page/setting","/config/page/table/index");
		for(String page:pages) {
			getPagewithoutParam(restTemplate,page);
		}
	}

	@Test
	@Transactional
	public void testMenuHtmlInterface() {
		MenuServicePageTest menuDemo = new MenuServicePageTest(menuMapper,restTemplate);
		
		//请求菜单分页数据
//		menuDemo.testMenuDataByPageParam();
		
		//测试菜单新增、修改删除功能
		menuDemo.testMenuADDUPTDEL(null);
	}
	
	
	
}
