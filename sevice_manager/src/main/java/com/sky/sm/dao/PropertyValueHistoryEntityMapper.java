package com.sky.sm.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.sky.sm.bean.PropertyValueHistoryEntity;

/**
 * 	配置属性历史数据mapper
 * @author 王帆
 * @date  2019年3月7日 下午5:02:27
 */
@Mapper
public interface PropertyValueHistoryEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PropertyValueHistoryEntity record);

    int insertSelective(PropertyValueHistoryEntity record);

    PropertyValueHistoryEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PropertyValueHistoryEntity record);

    int updateByPrimaryKey(PropertyValueHistoryEntity record);
    
    /**
     * 	根据pid查询配置属性历史数据
     * @param pid
     * @return
     * @author 王帆
     * @date 2019年3月7日 下午5:01:54
     */
	List<PropertyValueHistoryEntity> queryByPid(Integer pid);
}