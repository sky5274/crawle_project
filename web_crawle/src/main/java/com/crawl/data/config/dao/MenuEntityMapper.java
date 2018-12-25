package com.crawl.data.config.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.crawl.data.config.dao.entity.MenuEntity;
import com.sky.pub.BasePageRequest;

@Mapper
public interface MenuEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MenuEntity record);

    int insertSelective(MenuEntity record);

    MenuEntity selectByPrimaryKey(Integer id);
    List<MenuEntity> queryAll();
    List<MenuEntity> queryPage(BasePageRequest page);
    int accountQuery();

    int updateByPrimaryKeySelective(MenuEntity record);

    int updateByPrimaryKey(MenuEntity record);
}