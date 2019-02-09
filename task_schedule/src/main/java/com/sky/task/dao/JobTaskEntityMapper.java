package com.sky.task.dao;

import com.sky.task.entity.JobTaskEntity;

public interface JobTaskEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(JobTaskEntity record);

    int insertSelective(JobTaskEntity record);

    JobTaskEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(JobTaskEntity record);

    int updateByPrimaryKey(JobTaskEntity record);
}