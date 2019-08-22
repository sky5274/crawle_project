package com.sky.flow.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sky.flow.bean.TaskFlowNodeBean;

/**
 * 任务流程节点信息mapper
 * @author 王帆
 * @date  2019年5月18日 上午11:34:28
 */
@Mapper
public interface TaskFlowNodeBeanMapper {
    int deleteByPrimaryKey(String id);

    int insert(TaskFlowNodeBean record);

    int insertSelective(TaskFlowNodeBean record);

    TaskFlowNodeBean selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TaskFlowNodeBean record);

    int updateByPrimaryKey(TaskFlowNodeBean record);
    
    /**
     * 	根据流程任务id查询任务节点
     * @param taskId
     * @return
     * @author 王帆
     * @date 2019年6月1日 下午3:43:22
     */
	List<TaskFlowNodeBean> selectByTask(String taskId);
	
	
	/**
	 * 根据任务id与任务节点id查询任务节点数据
	 * @param taskId
	 * @param nodeId
	 * @return
	 * @author 王帆
	 * @date 2019年6月10日 下午2:23:51
	 */
	TaskFlowNodeBean selectByTaskAndId(@Param("taskId")String taskId,@Param("nodeId")String nodeId);

	/**
	 * 根据流程节点条件查询节点
	 * @param node
	 * @return
	 * @author 王帆
	 * @date 2019年6月1日 下午3:43:46
	 */
	List<TaskFlowNodeBean> selectByparam(TaskFlowNodeBean node);

	/**
	 * 查询流程任务的最新的任务节点
	 * @param taskId
	 * @return
	 * @author 王帆
	 * @date 2019年6月1日 下午3:44:08
	 */
	TaskFlowNodeBean selectNowNodeByTask(@Param("taskId")String taskId,@Param("index")Integer index);
}