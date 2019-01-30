package com.sky.auth.config.dao;

import org.apache.ibatis.annotations.Mapper;

import com.sky.auth.config.entity.AuthorityEntity;


@Mapper
public interface AuthorityMapper {

	int insert(AuthorityEntity record);
	
	int insertSelective(AuthorityEntity record);
	
	int updateByPrimaryKeySelective(AuthorityEntity record);

	int updateByPrimaryKey(AuthorityEntity record);
}
