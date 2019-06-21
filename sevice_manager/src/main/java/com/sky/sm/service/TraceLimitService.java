package com.sky.sm.service;

import com.sky.pub.Page;
import com.sky.pub.PageRequest;
import com.sky.pub.common.exception.ResultException;
import com.sky.sm.bean.TraceLimitBean;
import com.sky.sm.bean.req.TraceLimitReqBean;

/**
 * 链路限流服务interface
 * @author 王帆
 * @date  2019年5月8日 下午5:28:01
 */
public interface TraceLimitService {

	/**
	 *  根据限流信息查询系统配置最相近的限流配置
	 * @param limit
	 * @return
	 * @author 王帆
	 * @date 2019年5月8日 下午5:29:27
	 */
	public TraceLimitBean queryMaxLike(TraceLimitReqBean limit);
	
	/**
	 * 添加限流配置
	 * @param limit
	 * @return
	 * @author 王帆
	 * @throws ResultException 
	 * @date 2019年5月9日 下午1:10:06
	 */
	public int addLimitBean(TraceLimitBean limit) throws ResultException;
	
	/**
	 * 更新限流配置
	 * @param limit
	 * @return
	 * @author 王帆
	 * @date 2019年5月9日 下午1:10:56
	 */
	public int updateLimitBean(TraceLimitBean limit) throws ResultException;
	
	/**
	 * 删除 限流配置
	 * @param limit
	 * @return
	 * @author 王帆
	 * @date 2019年5月9日 下午1:10:28
	 */
	public int deleteLimitBean(TraceLimitBean limit) throws ResultException;
	
	/**
	 *  分页查询限流配置 
	 * @param pageLimit
	 * @return
	 * @author 王帆
	 * @date 2019年5月9日 下午1:11:19
	 */
	public Page<TraceLimitBean> queryLimitPage(PageRequest<TraceLimitReqBean> pageLimit);

	/**
	 * 根据id（，分割）删除限流配置
	 * @param id
	 * @return
	 * @author 王帆
	 * @date 2019年5月9日 下午3:48:26
	 */
	public int deleteLimits(String id);

	/**
	 * 根据id查询限流配置
	 * @param id
	 * @return
	 * @author 王帆
	 * @date 2019年5月9日 下午3:48:58
	 */
	public TraceLimitBean queryById(int id);

}
