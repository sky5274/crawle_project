package com.sky.sm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sky.sm.bean.TraceRecordBean;
import com.sky.sm.bean.TraceRecordEntity;

@Mapper
public interface TraceRecordEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TraceRecordEntity record);
    
    int insertBatch(List<TraceRecordEntity> records);

    int insertSelective(TraceRecordEntity record);

    TraceRecordEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TraceRecordEntity record);

    int updateByPrimaryKey(TraceRecordEntity record);
    
    /**
     * 	根据链路信息查询链路分组记录
     * @param record
     * @return
     * @author 王帆
     * @date 2019年3月16日 上午10:34:06
     */
    TraceRecordBean queryTraceGroup(TraceRecordEntity record);
    
    /**
     * 	根据链路信息查询关联的链路数据
     * @param record
     * @return
     * @author 王帆
     * @date 2019年3月16日 上午10:35:44
     */
    List<TraceRecordEntity> queryTraceList(TraceRecordEntity record);
}