package com.sky.sm.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.sky.pub.Result;
import com.sky.pub.ResultUtil;
import com.sky.sm.bean.ProjectTransationNodeData;
import com.sky.sm.service.impl.StringJsonRedisServiceImpl;
import com.sky.sm.socketService.HttpTransactionSocketService;

/**
 * http 事务控制管理控件
 * @author 王帆
 * @date  2019年9月23日 下午2:57:30
 */
@RestController
@RequestMapping("/project/transaction")
public class HttpTransactionController {
	@Resource
	private StringJsonRedisServiceImpl stringRedisService;
	private int moreTime=1000*30;
	
	private String getKey(ProjectTransationNodeData data) {
		return HttpTransactionSocketService.getKey(data);
	}
	/**
	 * 事务节点模糊查询key
	 * */
	private String getLikeKey(ProjectTransationNodeData data) {
		return String.format(HttpTransactionSocketService.transactionKeyFormat, getDefString(data.getProject()),getDefString(data.getVersion()),getDefString(data.getGroupId()),getDefString(data.getNodeId()));
	}
	private String getDefString(String key) {
		if(StringUtils.isEmpty(key)) {
			return "*";
		}
		return key;
	}
	
	/**
	 * 更新事务节点数据
	 * @param data
	 * @return
	 * @author 王帆
	 * @date 2019年9月26日 下午2:26:47
	 */
	@RequestMapping(value="/submit",method=RequestMethod.POST)
	public Result<Boolean> submitNodeEvent(@RequestBody ProjectTransationNodeData data){
		String msg=JSON.toJSONString(data);
		if(data.getEventType()!=-2) {
			stringRedisService.set(getKey(data),msg ,Integer.valueOf((data.getTimeOut()+moreTime)+""));
			//通过socket 发送节点事务消息
			HttpTransactionSocketService.sendInfo(HttpTransactionSocketService.fromateGroup(data.getProject(), data.getVersion()), msg);
		}else {
			stringRedisService.delKeyLike("String_"+getKey(data));
		}
		
		
		return ResultUtil.getOk(true);
	}
	
	/**
	 * 根据信息查询事务节点信息
	 * @param data
	 * @return
	 * @author 王帆
	 * @date 2019年9月26日 下午2:26:24
	 */
	@RequestMapping(value="/query",method=RequestMethod.POST)
	public Result<List<ProjectTransationNodeData>> queryNodeEvent(@RequestBody ProjectTransationNodeData data){
		return ResultUtil.getOk(stringRedisService.getLike(getLikeKey(data),ProjectTransationNodeData.class));
	}
}
