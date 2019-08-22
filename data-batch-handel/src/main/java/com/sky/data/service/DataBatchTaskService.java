package com.sky.data.service;

import java.util.List;
import java.util.Set;

import com.sky.pub.Page;
import com.sky.pub.common.exception.ResultException;
import com.sky.data.entity.DataBatchTaskEntity;
import com.sky.data.entity.BatchTaskGroupEntity;
import com.sky.data.entity.req.DataBatchReqData;;

/**
 * 		批处理调度任务服务
 * @author 王帆
 * @date  2019年2月9日 下午1:27:50
 */
public interface DataBatchTaskService {
	
	/**
	 * 	添加数据批处理调度任务配置信息
	 * @param task
	 * @return
	 * @author 王帆
	 * @throws ResultException 
	 * @date 2019年2月9日 下午2:47:52
	 */
	public DataBatchTaskEntity addDataTask(DataBatchTaskEntity task) throws ResultException;
	
	/**
	 * 	修改数据批处理调度任务信息
	 * @param task
	 * @return
	 * @author 王帆
	 * @date 2019年2月9日 下午2:48:26
	 */
	public boolean modifyDataTask(DataBatchTaskEntity task) throws ResultException;
	/**
	 *   	作废数据批处理调度任务
	 * @param task
	 * @return
	 * @author 王帆
	 * @date 2019年2月9日 下午2:48:48
	 */
	public boolean cancelDataTask(DataBatchTaskEntity task);
	
	public BatchTaskGroupEntity addTaskGroup(BatchTaskGroupEntity group) throws ResultException;
	public boolean modifyTaskGroup(BatchTaskGroupEntity group) throws ResultException;
	public boolean delTaskGroup(BatchTaskGroupEntity group);
	
	/**
	 * 	运行数据批处理任务调度
	 * @param task
	 * @return
	 * @author 王帆
	 * @throws ResultException 
	 * @date 2019年2月9日 下午2:46:48
	 */
	public boolean runDataTask(DataBatchTaskEntity task) throws ResultException;
	
	/**
	 * 	暂停数据批处理调度任务
	 * @param task
	 * @return
	 * @author 王帆
	 * @date 2019年2月9日 下午2:47:03
	 */
	public boolean pauseDataTask(DataBatchTaskEntity task);
	
	/**
	 * 	关闭数据批处理调度任务
	 * @param task
	 * @return
	 * @author 王帆
	 * @date 2019年2月9日 下午2:47:32
	 */
	public boolean closeDataTask(DataBatchTaskEntity task);
	
	/**
	 * 	查询所有分组
	 * @return
	 * @author 王帆
	 * @date 2019年2月9日 下午2:44:21
	 */
	public List<BatchTaskGroupEntity> queryGroup();
	
	/**
	 *	 分页查询调度任务
	 * @param taskPage
	 * @return
	 * @author 王帆
	 * @date 2019年2月9日 下午2:46:12
	 */
	public Page<DataBatchTaskEntity> queryPageByTask(DataBatchReqData taskPage);
	
	/**
	 * 	根据主键id查询调度任务
	 * @param id
	 * @return
	 * @author 王帆
	 * @date 2019年2月16日 上午9:44:27
	 */
	public DataBatchTaskEntity getDataTask(Integer id);
	
	/**
	 * 	根据任务id查询调度任务
	 * @param taskId
	 * 
	 * @return
	 * @author 王帆
	 * @date 2019年2月16日 上午9:45:03
	 */
	public DataBatchTaskEntity getDataTask(String taskId);
	
	/**
	 * 	自动执行系统定义的数据批处理调度任务
	 * 
	 * @author 王帆
	 * @date 2019年2月16日 上午9:49:50
	 */
	public void autoStartDataTask();
	
	/**
	 *	 自动更新数据批处理调度任务的停止状态
	 * 
	 * @author 王帆
	 * @date 2019年2月16日 上午10:01:42
	 */
	public void autoStopDataTask();

	/**
	 * 根据类名获取节点的ip+port
	 * @param className
	 * @return
	 * @author 王帆
	 * @date 2019年2月17日 下午2:03:31
	 */
	public Set<String> getNodeIps(String groupId,String className);
}
