package com.sky.task.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.sky.pub.PageRequest;
import com.sky.task.entity.JobTaskEntity;

public interface JobTaskEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(JobTaskEntity record);

    int insertSelective(JobTaskEntity record);

    JobTaskEntity selectByPrimaryKey(Integer id);
    
    JobTaskEntity selectByTaskId(String taskId);

    int updateByPrimaryKeySelective(JobTaskEntity record);

    int updateByPrimaryKey(JobTaskEntity record);

	List<JobTaskEntity> queryListByParam(@Param("page") PageRequest<JobTaskEntity> pageJob, @Param("bean")JobTaskEntity bean);

	int accout(@Param("bean") JobTaskEntity bean);
}