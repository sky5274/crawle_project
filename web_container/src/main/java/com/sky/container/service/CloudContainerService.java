package com.sky.container.service;

import com.sky.container.bean.ContainerBean;
import com.sky.container.entity.ContainerEntity;
import com.sky.pub.Page;
import com.sky.pub.PageRequest;
import com.sky.pub.common.exception.ResultException;

/**
 * 	云容器
 * @author 王帆
 * @date  2019年10月28日 下午3:14:52
 */
public interface CloudContainerService {
	
	/**
	 * 	分页查询容器数据
	 * @param page
	 * @return
	 * @author 王帆
	 * @date 2019年10月28日 下午3:33:14
	 */
	Page<ContainerBean> queryContainer(PageRequest<ContainerEntity> page);
	
	/**
	 * 	查询容器关联docker信息
	 * @param cid
	 * @return
	 * @author 王帆
	 * @date 2019年10月29日 下午3:39:07
	 */
	public ContainerBean queryContainerDocker(String cid);
	
	/**
	 * 	新增容器
	 * @param c
	 * @return
	 * @throws ResultException
	 * @author 王帆
	 * @date 2019年10月28日 下午3:24:17
	 */
	public ContainerBean addContainer(ContainerEntity c) throws ResultException;
	
	/**
	 * 	更新容器信息
	 * @param c
	 * @return
	 * @throws ResultException
	 * @author 王帆
	 * @date 2019年10月28日 下午3:24:32
	 */
	public ContainerBean updateContainer(ContainerEntity c)throws ResultException;
	
	/**
	 * 	启动容器
	 * @param c
	 * @return
	 * @throws ResultException
	 * @author 王帆
	 * @date 2019年10月28日 下午3:24:51
	 */
	public String startContainer(ContainerEntity c)throws ResultException;
	
	/**
	 * 	停止容器
	 * @param c
	 * @return
	 * @throws ResultException
	 * @author 王帆
	 * @date 2019年10月28日 下午3:25:10
	 */
	public String stopContainer(ContainerEntity c)throws ResultException;
}
