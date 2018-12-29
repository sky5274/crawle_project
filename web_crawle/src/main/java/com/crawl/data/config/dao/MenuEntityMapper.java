package com.crawl.data.config.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.crawl.data.config.dao.entity.MenuEntity;
import com.sky.pub.BasePageRequest;

@Mapper
public interface MenuEntityMapper {
    int deleteByPrimaryKey(MenuEntity record);

    int insert(MenuEntity record);

    int insertSelective(MenuEntity record);
    
    /**
     * 根据对象查询菜单数据
     * @param record
     * @return
     * @author 王帆
     * @date 2018年12月28日 下午1:00:09
     */
    List<MenuEntity> queryByEntity(MenuEntity record);

    MenuEntity selectByPrimaryKey(Integer id);
    
    List<MenuEntity> queryAll();
    /**
     * 分页查询菜单数据
     * @param page
     * @return
     * @author 王帆
     * @date 2018年12月28日 下午12:36:02
     */
    List<MenuEntity> queryPage(BasePageRequest page);
    
    /**
     * 统计菜单数据
     * @return
     * @author 王帆
     * @date 2018年12月28日 下午12:36:23
     */
    int accountQuery();

    int updateByPrimaryKeySelective(MenuEntity record);

    int updateByPrimaryKey(MenuEntity record);
}