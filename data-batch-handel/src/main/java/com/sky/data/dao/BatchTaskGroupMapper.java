package com.sky.data.dao;

import java.util.List;

import com.sky.data.entity.BatchTaskGroupEntity;

public interface BatchTaskGroupMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BatchTaskGroupEntity record);

    int insertSelective(BatchTaskGroupEntity record);

    BatchTaskGroupEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BatchTaskGroupEntity record);

    int updateByPrimaryKey(BatchTaskGroupEntity record);

	 List<BatchTaskGroupEntity> queryAll() ;
}