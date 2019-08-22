package com.sky.task.controller;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.sky.pub.Page;
import com.sky.pub.Result;
import com.sky.pub.ResultAssert;
import com.sky.pub.ResultCode;
import com.sky.pub.ResultUtil;
import com.sky.pub.common.exception.ResultException;
import com.sky.pub.controller.BaseExcepionController;
import com.sky.task.entity.JobTaskEntity;
import com.sky.task.entity.TaskGroupEntity;
import com.sky.task.entity.req.JobTaskReqData;
import com.sky.task.service.JobTaskService;

/**
 * 	任务管理器
 * @author 王帆
 * @date  2019年2月2日 下午7:58:53
 */
@RestController
@RequestMapping("/job")
public class JobTaskController extends BaseExcepionController{
	@Resource
	private JobTaskService jobTaskService;
	
	private void validResult(BindingResult validResult) throws ResultException{
		if(validResult.hasErrors()) {
			List<String> msgs=new LinkedList<>();
			for(ObjectError field:validResult.getAllErrors()) {
				msgs.add(field.getDefaultMessage());
			}
			throw new ResultException(ResultCode.VALID, "定时任务校验："+String.join(";", msgs));
		}
	}
	
	@RequestMapping("/page/cron")
	public ModelAndView taskQuartzCron() {
		return new ModelAndView("task/cron");
	}
	@RequestMapping("/page/list")
	public ModelAndView taskListPage() {
		ModelAndView view=new ModelAndView("task/list");
		view.addObject("groups", jobTaskService.queryGroup());
		return view;
	}
	@RequestMapping("/page/cart")
	public ModelAndView taskCartPage(Integer id) {
		ModelAndView view=new ModelAndView("task/cart");
		view.addObject("job", jobTaskService.getJobTask(id));
		return view;
	}
	@RequestMapping("/page/modify")
	public ModelAndView taskModifyPage(HttpServletRequest req) {
		ModelAndView view=new ModelAndView("task/modify");
		String id=req.getParameter("id");
		if(!StringUtils.isEmpty(id)) {
			view.addObject("job", jobTaskService.getJobTask(Integer.valueOf(id)));
		}
		view.addObject("groups", jobTaskService.queryGroup());
		return view;
	}
	
	@RequestMapping(value="add",method=RequestMethod.POST)
	public Result<JobTaskEntity> addTaskJob(@RequestBody @Valid JobTaskEntity job,BindingResult validResult) throws ResultException{
		validResult(validResult);
		return ResultUtil.getOk(jobTaskService.addJobTask(job));
	}
	
	@RequestMapping(value="modify",method=RequestMethod.POST)
	public Result<Boolean> modifyTaskJob(@RequestBody @Valid JobTaskEntity job,BindingResult validResult) throws ResultException{
		validResult(validResult);
		ResultAssert.isBlank(job.getTaskId(), "调度任务编码不允许为空");
		return ResultUtil.getOk(jobTaskService.modifyJobTask(job));
	}
	
	@RequestMapping(value="cancel",method=RequestMethod.POST)
	public Result<Boolean> cancelTaskJob(@RequestBody JobTaskEntity job){
		return ResultUtil.getOk( jobTaskService.cancelJobTask(job));
	}
	
	@RequestMapping(value="/query/task")
	public Result<JobTaskEntity> queryTaskJob(int id){
		return ResultUtil.getOk( jobTaskService.getJobTask(id));
	}
	
	@RequestMapping(value="/query/page")
	public Result<Page<JobTaskEntity>> queryPageTaskJob(JobTaskReqData jobPage){
		return ResultUtil.getOk( jobTaskService.queryPageByTask(jobPage));
	}
	
	@RequestMapping(value="/query/node")
	public Result<Set<String>> queryJobNodeUrl(String groupId,String className){
		return ResultUtil.getOk( jobTaskService.getNodeIps(groupId,className));
	}
	
	@RequestMapping(value="/group/add",method=RequestMethod.POST)
	public Result<TaskGroupEntity> addTaskGroup(@RequestBody TaskGroupEntity group) throws ResultException {
		return ResultUtil.getOk(jobTaskService.addTaskGroup(group));
	}
	
	@RequestMapping(value="/group/mod",method=RequestMethod.POST)
	public Result<Boolean> modifyTaskGroup(@RequestBody TaskGroupEntity group) throws ResultException {
		return ResultUtil.getOk(jobTaskService.modifyTaskGroup(group));
	}
	
	@RequestMapping(value="/group/del",method=RequestMethod.POST)
	public Result<Boolean> delTaskGroup(@RequestBody TaskGroupEntity group) {
		return ResultUtil.getOk(jobTaskService.delTaskGroup(group));
	}
	
	@RequestMapping("/group/list")
	public Result<List<TaskGroupEntity>> queryTaskGroupList() {
		return ResultUtil.getOk(jobTaskService.queryGroup());
	}
	
	@RequestMapping(value="/task/run",method=RequestMethod.POST)
	public Result<Boolean> runJobTask(@RequestBody @Valid JobTaskEntity job,BindingResult validResult) throws ResultException {
		validResult(validResult);
		ResultAssert.isBlank(job.getTaskId(), "调度任务编码不允许为空");
		return ResultUtil.getOk(jobTaskService.runJobTask(job));
	}
	@RequestMapping(value="/task/pause",method=RequestMethod.POST)
	public Result<Boolean> pauseJobTask(@RequestBody JobTaskEntity job) throws ResultException {
		ResultAssert.isBlank(job.getTaskId(), "调度任务编码不允许为空");
		return ResultUtil.getOk(jobTaskService.pauseJobTask(job));
	}
	@RequestMapping(value="/task/close",method=RequestMethod.POST)
	public Result<Boolean> closeJobTask(@RequestBody JobTaskEntity job) throws ResultException {
		ResultAssert.isBlank(job.getTaskId(), "调度任务编码不允许为空");
		return ResultUtil.getOk(jobTaskService.closeJobTask(job));
	}
}
