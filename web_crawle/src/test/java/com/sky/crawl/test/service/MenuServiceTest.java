package com.sky.crawl.test.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;
import com.alibaba.fastjson.JSON;
import com.sky.crawl.config.service.MenuService;
import com.sky.crawl.data.config.dao.entity.MenuEntity;
import com.sky.crawl.data.config.dao.entity.MenuNode;
import com.sky.pub.BasePageRequest;
import com.sky.pub.Page;


/**
 * menu service test
 * @author 王帆
 * @date  2018年12月27日 下午2:08:59
 */
@RunWith(SpringRunner.class)
public class MenuServiceTest {
	private static final Logger log = LoggerFactory.getLogger(MenuServiceTest.class);

	@Autowired(required=false)
	private MenuService menuSerive;
	
	@Test
	public void getAllMenuNode() {
		List<MenuNode> node = menuSerive.getMenuNode();
		Assert.isNull(node,"菜单节点获取失败");
		log.debug("菜单节点信息：{}",JSON.toJSONString(node));
	}
	
	@Test
	public void getAllMenuList() {
		List<MenuEntity> node = menuSerive.getMenuList();
		Assert.isNull(node,"菜单集合失败获取失败");
		log.debug("菜单节点信息：{}",JSON.toJSONString(node));
	}
	
	@Test
	public void getMenuByPage() {
		int pagesize=1;
		Page<MenuEntity> page = getMenuDataByPageParam(1,pagesize);
		int total=page.getTotal();
		for(int i=2;i<total/pagesize;i++) {
			getMenuDataByPageParam(i,pagesize);
		}
	}
	
	/**
	 * 根据参数获取菜单分页数据
	 * @param pagenum
	 * @param size
	 * @return
	 * @author 王帆
	 * @date 2018年12月27日 下午2:29:03
	 */
	public Page<MenuEntity> getMenuDataByPageParam(int pagenum,int size) {
		log.debug("test load menu by page:[page:{},pagesize:{}]",pagenum,size);
		BasePageRequest page=new BasePageRequest(pagenum,size);
		Page<MenuEntity> pagenode = menuSerive.getMenuPage(page);
		Assert.isNull(pagenode,"菜单分页数据获取失败");
		log.info("menu in page:{}",JSON.toJSONString(pagenode));
		return pagenode;
	}
	
	@Test
	public void test() throws ParseException {
		String time="2018-12-28 15:36:47";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = dateFormat.parse(time);
		System.err.println(dateFormat.format(date));
		System.err.println(date.getTime());
	}
}
