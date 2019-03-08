package com.sky.sm.service;

import java.util.List;
import com.sky.pub.Page;
import com.sky.pub.common.exception.ResultException;
import com.sky.sm.bean.ProjectPropertyBean;
import com.sky.sm.bean.PropertyValueEntity;
import com.sky.sm.bean.PropertyValueHistoryEntity;
import com.sky.sm.bean.req.PropertyValueReqEntity;

public interface PropertyValueService {
	
	/**
	 * 	分页查询属性配置信息
	 * @param property
	 * @return
	 * @author 王帆
	 * @date 2019年3月7日 下午2:40:19
	 */
	Page<PropertyValueEntity> queryPageOfProperty(PropertyValueReqEntity property);
	
	/**
	 * 	新增属性配置
	 * @param property
	 * @return
	 * @throws ResourceException
	 * @author 王帆
	 * @date 2019年3月7日 下午2:41:41
	 */
	Boolean addProperty(PropertyValueEntity property) throws ResultException;
	/**
	 * 	更新属性配置
	 * @param property
	 * @return
	 * @throws ResourceException
	 * @author 王帆
	 * @date 2019年3月7日 下午2:42:06
	 */
	Boolean updateProperty(PropertyValueEntity property) throws ResultException;
	
	/**
	 * 	根据id查询属性配置信息
	 * @param id
	 * @return
	 * @author 王帆
	 * @date 2019年3月7日 下午2:43:38
	 */
	PropertyValueEntity queryById(Integer id);
	
	/**
	 * 	根据id查询属性配置的历史信息
	 * @param pid
	 * @return
	 * @author 王帆
	 * @date 2019年3月7日 下午2:43:59
	 */
	List<PropertyValueHistoryEntity> queryByPId(Integer pid);

	/**
	 * 根据项目条件查询配置属性的值
	 * @param property
	 * @return
	 * @author 王帆
	 * @date 2019年3月8日 下午2:22:24
	 */
	String getPropertyValue(PropertyValueEntity property);
	
	/**
	 * 根据项目条件查询对应的项目属性数据
	 * @param property
	 * @return
	 * @author 王帆
	 * @date 2019年3月8日 下午2:22:53
	 */
	List<ProjectPropertyBean> getProperties(PropertyValueEntity property);
}
