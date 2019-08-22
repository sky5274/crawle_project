package com.sky.task.dao;

import java.util.List;

import com.sky.task.entity.TaskGroupEntity;

public interface TaskGroupEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TaskGroupEntity record);

    int insertSelective(TaskGroupEntity record);

    TaskGroupEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TaskGroupEntity record);

    int updateByPrimaryKey(TaskGroupEntity record);

	 List<TaskGroupEntity> queryAll() ;
}