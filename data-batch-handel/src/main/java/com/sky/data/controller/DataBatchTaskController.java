package com.sky.data.controller;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.sky.data.entity.BatchTaskGroupEntity;
import com.sky.data.entity.DataBatchTaskEntity;
import com.sky.data.entity.req.DataBatchReqData;
import com.sky.data.service.DataBatchTaskService;
import com.sky.pub.Page;
import com.sky.pub.Result;
import com.sky.pub.ResultUtil;
import com.sky.pub.common.exception.ResultException;

@RestController
@RequestMapping("datatask")
public class DataBatchTaskController<TaskGroupEntity> {
	@Autowired
	private DataBatchTaskService dataBatchTaskService;
	
	@RequestMapping("/page/cron")
	public ModelAndView taskQuartzCron() {
		return new ModelAndView("task/cron");
	}
	@RequestMapping("/page/list")
	public ModelAndView taskListPage() {
		ModelAndView view=new ModelAndView("task/list");
		view.addObject("groups", dataBatchTaskService.queryGroup());
		return view;
	}
	@RequestMapping("/page/cart")
	public ModelAndView taskCartPage(Integer id) {
		ModelAndView view=new ModelAndView("task/cart");
		view.addObject("job", dataBatchTaskService.getDataTask(id));
		return view;
	}
	@RequestMapping("/page/modify")
	public ModelAndView taskModifyPage(HttpServletRequest req) {
		ModelAndView view=new ModelAndView("task/modify");
		String id=req.getParameter("id");
		if(!StringUtils.isEmpty(id)) {
			view.addObject("job", dataBatchTaskService.getDataTask(Integer.valueOf(id)));
		}
		view.addObject("groups", dataBatchTaskService.queryGroup());
		return view;
	}
	
	/**
	 * 分页查询批处理数据任务
	 * @param datatask
	 * @return
	 * @author 王帆
	 * @date 2019年8月10日 下午3:50:07
	 */
	@PostMapping("/query/page")
	public Result<Page<DataBatchTaskEntity>> queryPage(@RequestBody DataBatchReqData datatask) {
		return ResultUtil.getOk(dataBatchTaskService.queryPageByTask(datatask));
	}
	
	/**
	 * 根据任务id查询任务数据
	 * @param taskId
	 * @return
	 * @author 王帆
	 * @date 2019年8月10日 下午3:55:20
	 */
	@RequestMapping("/query/info")
	public Result<DataBatchTaskEntity> queryDataInfo(String taskId) {
		return ResultUtil.getOk(dataBatchTaskService.getDataTask(taskId));
	}
	
	/**
	 * 添加批处理调度任务
	 * @param datatask
	 * @return
	 * @throws ResultException
	 * @author 王帆
	 * @date 2019年8月10日 下午3:50:39
	 */
	@PostMapping("/add/data")
	public Result<DataBatchTaskEntity> addDataBatchTask(@RequestBody DataBatchTaskEntity datatask) throws ResultException {
		return ResultUtil.getOk(dataBatchTaskService.addDataTask(datatask));
	}
	
	/**
	 * 修改批处理调度任务
	 * @param dataTask
	 * @return
	 * @throws ResultException
	 * @author 王帆
	 * @date 2019年8月10日 下午3:52:44
	 */
	@PostMapping("/modify/data")
	public Result<Boolean> modifyDataBatchTask(@RequestBody DataBatchTaskEntity dataTask) throws ResultException {
		return ResultUtil.getOk(dataBatchTaskService.modifyDataTask(dataTask));
	}
	
	/**
	 * 	执行任务
	 * @param dataTask
	 * @return
	 * @throws ResultException
	 * @author 王帆
	 * @date 2019年8月10日 下午4:25:30
	 */
	@PostMapping("/run/task")
	public Result<Boolean> runDataBatchTask(@RequestBody DataBatchTaskEntity dataTask) throws ResultException {
		return ResultUtil.getOk(dataBatchTaskService.runDataTask(dataTask));
	}
	
	/**
	 * 	暂停批处理任务
	 * @param dataTask
	 * @return
	 * @throws ResultException
	 * @author 王帆
	 * @date 2019年8月10日 下午4:31:05
	 */
	@PostMapping("/pause/task")
	public Result<Boolean> pauseDataBatchTask(@RequestBody DataBatchTaskEntity dataTask) throws ResultException {
		return ResultUtil.getOk(dataBatchTaskService.pauseDataTask(dataTask));
	}
	
	/**
	 * 	停止批处理任务
	 * @param dataTask
	 * @return
	 * @throws ResultException
	 * @author 王帆
	 * @date 2019年8月10日 下午4:31:29
	 */
	@PostMapping("/stop/task")
	public Result<Boolean> stopDataBatchTask(@RequestBody DataBatchTaskEntity dataTask) throws ResultException {
		return ResultUtil.getOk(dataBatchTaskService.closeDataTask(dataTask));
	}
	
	/**
	 * 	查询任务节点
	 * @param groupId
	 * @param className
	 * @return
	 * @author 王帆
	 * @date 2019年8月10日 下午4:12:23
	 */
	@RequestMapping(value="/query/node")
	public Result<Set<String>> queryJobNodeUrl(String groupId,String className){
		return ResultUtil.getOk( dataBatchTaskService.getNodeIps(groupId,className));
	}
	
	/**
	 * 	添加任务组
	 * @param group
	 * @return
	 * @throws ResultException
	 * @author 王帆
	 * @date 2019年8月10日 下午4:13:16
	 */
	@RequestMapping(value="/group/add",method=RequestMethod.POST)
	public Result<BatchTaskGroupEntity> addTaskGroup(@RequestBody BatchTaskGroupEntity group) throws ResultException {
		return ResultUtil.getOk(dataBatchTaskService.addTaskGroup(group));
	}
	
	/**
	 *    修改任务组
	 * @param group
	 * @return
	 * @throws ResultException
	 * @author 王帆
	 * @date 2019年8月10日 下午4:13:56
	 */
	@RequestMapping(value="/group/mod",method=RequestMethod.POST)
	public Result<Boolean> modifyTaskGroup(@RequestBody BatchTaskGroupEntity group) throws ResultException {
		return ResultUtil.getOk(dataBatchTaskService.modifyTaskGroup(group));
	}
	
	/**
	 * 	删除任务组
	 * @param group
	 * @return
	 * @author 王帆
	 * @date 2019年8月10日 下午4:14:28
	 */
	@RequestMapping(value="/group/del",method=RequestMethod.POST)
	public Result<Boolean> delTaskGroup(@RequestBody BatchTaskGroupEntity group) {
		return ResultUtil.getOk(dataBatchTaskService.delTaskGroup(group));
	}
	
	/**
	 * 	查询分组所有数据
	 * @return
	 * @author 王帆
	 * @date 2019年8月10日 下午4:14:50
	 */
	@RequestMapping("/group/list")
	public Result<List<BatchTaskGroupEntity>> queryTaskGroupList() {
		return ResultUtil.getOk(dataBatchTaskService.queryGroup());
	}
}
