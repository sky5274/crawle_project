package com.sky.sm.dao;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import com.sky.sm.bean.ProjectInfoBean;

@Mapper
public interface ProjectInfoBeanMapper {
    int deleteProject(ProjectInfoBean record);

    int insert(ProjectInfoBean record);


    List<ProjectInfoBean> selectByProject(ProjectInfoBean record);

    int updateByPrimaryKeySelective(ProjectInfoBean record);

    int updateByPrimaryKey(ProjectInfoBean record);
    
   
}