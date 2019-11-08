package com.sky.container.dao;

import com.sky.container.entity.ContainerGroupEntity;

public interface ContainerGroupEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ContainerGroupEntity record);

    int insertSelective(ContainerGroupEntity record);

    ContainerGroupEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ContainerGroupEntity record);

    int updateByPrimaryKey(ContainerGroupEntity record);
}