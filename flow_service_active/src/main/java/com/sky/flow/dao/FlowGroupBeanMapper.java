package com.sky.flow.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sky.flow.bean.FlowGroupBean;

/**
 * 流程分组mapper
 * @author 王帆
 * @date  2019年5月18日 上午11:30:33
 */
@Mapper
public interface FlowGroupBeanMapper {
    int insert(FlowGroupBean record);

    int insertSelective(FlowGroupBean record);

	int updateSelective(FlowGroupBean group);

	/**
	 * 根据code集合查询分组
	 * @param codes
	 * @return
	 * @author 王帆
	 * @date 2019年5月18日 下午8:43:43
	 */
	List<FlowGroupBean> selectByCodes(List<String> codes);

	/**
	 * 根据id查询分组信息
	 * @param groupId
	 * @return
	 * @author 王帆
	 * @date 2019年5月18日 下午8:44:09
	 */
	FlowGroupBean selectById(String groupId);
}