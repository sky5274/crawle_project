package com.sky.container.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sky.container.bean.ContainerBean;
import com.sky.container.entity.ContainerEntity;
import com.sky.pub.PageRequest;

public interface ContainerEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ContainerEntity record);

    int insertSelective(ContainerEntity record);

    ContainerEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ContainerEntity record);

    int updateByPrimaryKey(ContainerEntity record);

    /**
     * 	统计容器数据
     * @param data
     * @return
     * @author 王帆
     * @date 2019年10月29日 上午9:51:02
     */
	int count(@Param("data")ContainerEntity data);

	/**
	 * 	分页查询容器数据
	 * @param data
	 * @param page
	 * @return
	 * @author 王帆
	 * @date 2019年10月29日 上午9:52:03
	 */
	List<ContainerBean> queryPage(@Param("data")ContainerEntity data, @Param("page")PageRequest<?> page);

	/**
	 * 	根据容器id查询容器信息
	 * @param cid   容器id
	 * @return
	 * @author 王帆
	 * @date 2019年10月29日 下午3:43:20
	 */
	ContainerBean queryByContainerId(String cid);
}