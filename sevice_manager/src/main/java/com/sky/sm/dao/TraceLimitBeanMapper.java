package com.sky.sm.dao;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sky.pub.PageRequest;
import com.sky.sm.bean.TraceLimitBean;
import com.sky.sm.bean.req.TraceLimitReqBean;

@Mapper
public interface TraceLimitBeanMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TraceLimitBean record);

    int insertSelective(TraceLimitBean record);

    TraceLimitBean selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TraceLimitBean record);

    int updateByPrimaryKey(TraceLimitBean record);

    /**
     * 	查询限流配置
     * @param page
     * @param data
     * @return
     * @author 王帆
     * @date 2019年5月9日 下午1:57:48
     */
	List<TraceLimitBean> queryLimitBeans(@Param("page")PageRequest<?> page, @Param("limit")TraceLimitReqBean data);

	/**
	 * 	统计限流配置
	 * @param data
	 * @return
	 * @author 王帆
	 * @date 2019年5月9日 下午1:58:36
	 */
	int accoutLimitBean(@Param("limit")TraceLimitReqBean data);

	/**
	 * 根据id集合删除限流数据
	 * @param ids
	 * @return
	 * @author 王帆
	 * @date 2019年5月9日 下午3:51:44
	 */
	int deleteLimit(List<String> ids);
   
}