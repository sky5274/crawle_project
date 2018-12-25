package com.crawl.config.service.impl;

import java.util.LinkedList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.crawl.config.service.MenuService;
import com.crawl.data.config.dao.MenuEntityMapper;
import com.crawl.data.config.dao.entity.MenuEntity;
import com.crawl.data.config.dao.entity.MenuNode;
import com.crawl.pub.util.Filter;
import com.crawl.pub.util.FilterReduce;
import com.sky.pub.BasePageRequest;
import com.sky.pub.Page;

@Service
public class MenuServiceImpl implements MenuService{
	@Resource
	private MenuEntityMapper menuMapper;
	
	@Override
	public List<MenuEntity> getMenuList() {
		return menuMapper.queryAll();
	}

	@Override
	public Boolean saveMenu(MenuEntity menu) {
		if(menu.getPid()==null) {
			menu.setPid(0);
		}
		return menuMapper.insertSelective(menu)>0;
	}

	@Override
	public Boolean updateMenu(MenuEntity menu) {
		return menuMapper.updateByPrimaryKeySelective(menu)>0;
	}

	@Override
	public Boolean delMneu(MenuEntity menu) {
		return menuMapper.deleteByPrimaryKey(menu.getId())>0;
	}

	@Override
	public Page<MenuEntity> getMenuPage(BasePageRequest page) {
		if(page!=null) {
			page.initPage();
			int total=menuMapper.accountQuery();
			return new Page<>(menuMapper.queryPage(page), total);
		}
		return null;
	}

	@Override
	public List<MenuNode> getMenuNode() {
		List<MenuEntity> list = getMenuList();
		List<MenuNode> nodes=new LinkedList<>();
		for(MenuEntity m:list) {
			MenuNode node = new MenuNode();
			BeanUtils.copyProperties(m, node);
			nodes.add(node);
		}
		Filter<MenuNode> filter=new Filter<MenuNode>() {

			@Override
			public Object getPid(MenuNode t) {
				return t.getPid();
			}

			@Override
			public Object getId(MenuNode t) {
				return t.getId();
			}

			@Override
			public void setChild(MenuNode t, List<MenuNode> child) {
				t.setChild(child);
			}
		};
		return FilterReduce.filter(nodes, filter);
	}

}
