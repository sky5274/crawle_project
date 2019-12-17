package com.sky.crawl.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.sky.crawl.data.config.dao.MenuEntityMapper;
import com.sky.crawl.data.config.dao.entity.MenuEntity;
import com.sky.crawl.util.RestTemplateUtil;
import com.sky.pub.BasePageRequest;
import com.sky.pub.Page;
import com.sky.pub.Result;

/**
 * 	菜单服务测试用例
 * @author 王帆
 * @date  2018年12月27日 下午5:13:36
 */
@SuppressWarnings("unchecked")
public class MenuServicePageTest extends RestTemplateUtil{
	private MenuEntityMapper menuMapper;
	private TestRestTemplate restTemplate;
	
	public MenuServicePageTest(MenuEntityMapper menuMapper, TestRestTemplate restTemplate){
		this.menuMapper=menuMapper;
		this.restTemplate=restTemplate;
	}
	
	/**
	 *	 请求菜单分页数据
	 * 
	 * @author 王帆
	 * @date 2018年12月27日 下午4:16:06
	 */
	public void testMenuDataByPageParam() {
		Result<Page<MenuEntity>> resultpage = testGetMenuNodeBYPageParam(restTemplate,"/menu/page",new BasePageRequest(1,2),Result.class);
		assertNotNull(resultpage);
		assertTrue("请求菜单分页数据失败",resultpage.isSuccess());
		log.info("menu page info:{}",JSON.toJSONString(resultpage));
	}

	/**
	 * 	测试菜单的添加更新功能删除
	 * 
	 * @author 王帆
	 * @date 2018年12月27日 下午4:18:29
	 */
	public void testMenuADDUPTDEL(String url) {
		String[][] params=new String[][]{{null,"",""},{null,null,""},{"","",""},{"sys_menu_test","",""},{"sys_menu_test","测试菜单1",""}};
		for(String[] param:params) {
			testMenuAddInterface(param[0],param[1],url==null?param[2]:url);
		}
	}
	
	/**
	 * 	添加菜单并预测数据是否准确
	 * @param code
	 * @param name
	 * @param url
	 * @author 王帆
	 * @date 2018年12月27日 下午4:58:16
	 */
	public void testMenuAddInterface(String code, String name, String url) {
		Boolean predict=!StringUtils.isEmpty(code) && !StringUtils.isEmpty(name);
		Result<MenuEntity> result = testGetMenuNodeBYPageParam(restTemplate,"/menu/add",getMenuEntity(code, name, url),Result.class);
		log.info("menu add action, predict:{} reponse data:{}",predict,JSON.toJSONString(result));
		assertEquals(String.format("menu add is not expcet,the predict: %s, result status: %s ", predict+"",result.isSuccess()+""),predict, result.isSuccess());
		if(predict) {
			assertNotNull("菜单添加后未返回数据",result.getData());
			MenuEntity menu = JSON.parseObject(JSON.toJSONString(result.getData()), MenuEntity.class);
			testMenuUPTInterface(menu);
			waitTime();
			testMenuDelInterface(menu);
		}
	}
	public void testMenuUPTInterface(MenuEntity menu) {
		String path="/menu/update";
		menu.setUrl("/test/update");
		testMenuInterfaceWithFlag(menu,path,false);
		waitTime();
		
		testMenuInterfaceWithFlag(menu,path,true);
	}
	
	/**
	 * 更新与删除方法  通用测试
	 * @param menu
	 * @param path
	 * @param flag
	 * @author 王帆
	 * @date 2018年12月28日 下午12:28:57
	 */
	public void testMenuInterfaceWithFlag(MenuEntity menu,String path,Boolean flag) {
		assertNotNull("更新的菜单不能为空", menu);
		if(!flag) {
			menu.setVersion(menu.getVersion()-1);
			menu.setLongTs(System.currentTimeMillis());
		}
		Result<Boolean> result = testGetMenuNodeBYPageParam(restTemplate,path,menu,Result.class);
		log.info("path: {}, action, predict:{} reponse data:{}",path,flag,JSON.toJSONString(result));
		assertTrue(String.format("path:%s is aciton fialed,result success: %s",path,result.isSuccess()+""), result.isSuccess());
		assertTrue(String.format("path: %s is predict %s,result status: %s",path,flag+"",result.isSuccess()+""), flag==result.getData());
	}
	
	public MenuEntity getNowMenu(MenuEntity menu) {
		MenuEntity temp = menuMapper.selectByPrimaryKey(menu.getId());
		menu.setVersion(temp.getVersion());
		menu.setTs(temp.getTs());
		return menu;
	}
	
	public void testMenuDelInterface(MenuEntity menu) {
		String path="/menu/del";
		testMenuInterfaceWithFlag(menu,path,false);
		waitTime();
		menu=getNowMenu(menu);
		testMenuInterfaceWithFlag(getNowMenu(menu),path,true);
	}
	
	/**
	 * 	根据id查询菜单数据
	 * @param id
	 * @return
	 * @author 王帆
	 * @date 2018年12月27日 下午5:06:26
	 */
	public MenuEntity getNewMenuData(int id) {
		return menuMapper.selectByPrimaryKey(id);
	}
	
	/**
	 * 	获取菜单对象数据
	 * @param url
	 * @return
	 * @author 王帆
	 * @date 2018年12月27日 下午4:31:55
	 */
	public MenuEntity getMenuEntity(String url) {
		return getMenuEntity("sys_menu_test","测试菜单",url);
	}
	public MenuEntity getMenuEntity(String code,String name,String url) {
		MenuEntity menu=new MenuEntity();
		menu.setCode(code);
		menu.setName(name);
		menu.setUrl("");
		return menu;
	}
	
}
