package com.sky.task.dao;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.sky.task.entity.JobTaskEntity;
import com.sky.task.entity.req.JobTaskReqData;

public interface JobTaskEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(JobTaskEntity record);

    int insertSelective(JobTaskEntity record);

    JobTaskEntity selectByPrimaryKey(Integer id);
    
    JobTaskEntity selectByTaskId(String taskId);

    int updateByPrimaryKeySelective(JobTaskEntity record);
    /**
     * 	根据任务更新调度为停止状态
     * @param list
     * @return
     * @author 王帆
     * @date 2019年2月16日 上午10:13:07
     */
    int updateTaskToClosed(List<JobTaskEntity> list);

    int updateByPrimaryKey(JobTaskEntity record);

	List<JobTaskEntity> queryListByParam(JobTaskReqData pageJob);

	int accout(JobTaskReqData pageJob);

	/**
	 * 根据状态更新任务job对应状态
	 * @param status
	 * @param group_task_set(group-task)
	 * @return
	 * @author 王帆
	 * @date 2019年2月18日 下午5:22:09
	 */
	int updateJobTasksStatus(@Param("status")Integer status, @Param("list")Set<String> group_task_set);
}