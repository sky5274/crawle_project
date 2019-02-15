package com.sky.crawl.config.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sky.crawl.data.config.dao.entity.MenuEntity;
import com.sky.crawl.data.config.dao.entity.MenuNode;
import com.sky.pub.BasePageRequest;
import com.sky.pub.Page;
import com.sky.pub.common.exception.ResultException;

@Service
public interface MenuService {
	
	public List<MenuEntity> getMenuList();
	public MenuEntity saveMenu(MenuEntity menu) throws ResultException;
	public Boolean updateMenu(MenuEntity menu) throws ResultException;
	public Boolean delMneu(MenuEntity menu) throws ResultException;
	/**
	 * 分页查询菜单
	 * @param page
	 * @return
	 * @author 王帆
	 * @date 2018年12月23日 下午10:16:38
	 */
	public Page<MenuEntity> getMenuPage(BasePageRequest page);
	
	/**
	 * 查询 菜单节点
	 * @return
	 * @author 王帆
	 * @date 2018年12月24日 下午3:09:56
	 */
	public List<MenuNode> getMenuNode();
}
