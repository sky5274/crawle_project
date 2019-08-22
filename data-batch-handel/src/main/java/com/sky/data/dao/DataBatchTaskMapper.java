package com.sky.data.dao;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.sky.data.bean.DataBatchTaskExtEntity;
import com.sky.data.entity.DataBatchTaskEntity;
import com.sky.data.entity.req.DataBatchReqData;

public interface DataBatchTaskMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DataBatchTaskEntity record);

    int insertSelective(DataBatchTaskEntity record);

    DataBatchTaskEntity selectByPrimaryKey(Integer id);
    
    DataBatchTaskEntity selectByTaskId(String taskId);

    int updateByPrimaryKeySelective(DataBatchTaskEntity record);
    /**
     * 	根据任务更新调度为停止状态
     * @param list
     * @return
     * @author 王帆
     * @date 2019年2月16日 上午10:13:07
     */
    int updateTaskToClosed(List<DataBatchTaskEntity> list);

    int updateByPrimaryKey(DataBatchTaskEntity record);

	List<DataBatchTaskEntity> queryListByParam(DataBatchReqData pagedata);

	int accout(DataBatchReqData pagedata);

	/**
	 * 根据状态更新任务job对应状态
	 * @param status
	 * @param group_task_set(group-task)
	 * @return
	 * @author 王帆
	 * @date 2019年2月18日 下午5:22:09
	 */
	int updateJobTasksStatus(@Param("status")Integer status, @Param("list")Set<String> group_task_set);

	/**
	 * 根据任务id获取任务拓展信息
	 * @param taskId
	 * @return
	 * @author 王帆
	 * @date 2019年7月27日 上午9:50:38
	 */
	DataBatchTaskExtEntity queryExtByTask(@Param("taskId") String taskId);
	
	/**
	 * 	插入任务拓展信息
	 * @param dataTaskExt
	 * @return
	 * @author 王帆
	 * @date 2019年8月10日 下午4:56:33
	 */
	int insertTaskExt(DataBatchTaskExtEntity dataTaskExt);
	
	/**
	 * 更新任务拓展信息
	 * @param dataTaskExt
	 * @return
	 * @author 王帆
	 * @date 2019年8月10日 下午4:57:14
	 */
	int updateTaskExt(DataBatchTaskExtEntity dataTaskExt);
}