package com.sky.crawl.config.service.impl;

import java.util.LinkedList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.sky.crawl.config.service.MenuService;
import com.sky.crawl.data.config.dao.MenuEntityMapper;
import com.sky.crawl.data.config.dao.entity.MenuEntity;
import com.sky.crawl.data.config.dao.entity.MenuNode;
import com.sky.pub.util.Filter;
import com.sky.pub.util.FilterReduce;
import com.sky.pub.BasePageRequest;
import com.sky.pub.Page;
import com.sky.pub.ResultAssert;
import com.sky.pub.common.exception.ResultException;

@Service
public class MenuServiceImpl implements MenuService{
	@Resource
	private MenuEntityMapper menuMapper;
	
	@Override
	public List<MenuEntity> getMenuList() {
		return menuMapper.queryAll();
	}

	@Override
	public MenuEntity saveMenu(MenuEntity menu) throws ResultException {
		if(menu.getPid()==null) {
			menu.setPid(0);
		}
		if(StringUtils.isEmpty(menu.getUrl())) {
			menu.setUrl("#");
		}
		List<String> err=new LinkedList<>();
		if(StringUtils.isEmpty(menu.getCode())) err.add("菜单编码");
		if(StringUtils.isEmpty(menu.getName())) err.add("菜单名称");
		ResultAssert.isTure(!err.isEmpty(), "请提交"+org.apache.tomcat.util.buf.StringUtils.join(err));
		
		MenuEntity temp=new MenuEntity();
		temp.setCode(menu.getCode());
		temp.setName(menu.getName());
		//验证menu  编码与名称是否重复
		ResultAssert.isTure(!menuMapper.queryByEntity(menu).isEmpty(),String.format("菜单编码：%s,菜单名称：%s 已存在，请修改后再提交",menu.getCode(),menu.getName()));
		//验证menu  添加操作是否成功
		ResultAssert.isTure(menuMapper.insertSelective(menu)<=0, "菜单添加失败");
		return menuMapper.queryByEntity(menu).get(0);
	}

	@Override
	public Boolean updateMenu(MenuEntity menu) throws ResultException{
		return menuMapper.updateByPrimaryKeySelective(menu)>0;
	}

	@Override
	public Boolean delMneu(MenuEntity menu) throws ResultException {
		return menuMapper.deleteByPrimaryKey(menu)>0;
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
