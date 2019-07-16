package com.sky.flow.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sky.flow.bean.TaskFlowBean;
import com.sky.flow.pub.PageRequest;

/**
 * 任务流程mapper
 * @author 王帆
 * @date  2019年5月18日 上午11:34:02
 */
@Mapper
public interface TaskFlowBeanMapper {
    int deleteByPrimaryKey(String id);

    int insert(TaskFlowBean record);

    int insertSelective(TaskFlowBean record);

    TaskFlowBean selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TaskFlowBean record);

    int updateByPrimaryKey(TaskFlowBean record);
    
    /**
     *	 查询用户 编码是否在当前任务的环节中
     * @param taskId
     * @param usercode
     * @return
     * @author 王帆
     * @date 2019年7月16日 上午9:20:37
     */
    boolean  queryUserIsInTask(@Param("taskId")String taskId,@Param("usercode")String usercode);
    
    boolean  queryUserIsTaskNode(@Param("taskNodeId")String taskNodeId,@Param("usercode")String usercode);
    
    /**
     * 	分页查询流程任务
     * @param taskpage
     * @param task
     * @return
     * @author 王帆
     * @date 2019年6月1日 下午3:42:32
     */
	List<TaskFlowBean> selectByParam(@Param("page")PageRequest<?> taskpage, @Param("task")TaskFlowBean task);

	/**
	 * 统计流程任务
	 * @param task
	 * @return
	 * @author 王帆
	 * @date 2019年6月1日 下午3:42:48
	 */
	int accoutByParam(@Param("task")TaskFlowBean task);
}