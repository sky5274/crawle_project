package com.sky.auth.config.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sky.auth.config.entity.UserDetailEntitiy;
import com.sky.auth.config.entity.UserEntity;

@Mapper
public interface UserMapper {
	public int insert(UserEntity user);
	public int insertSelective(UserEntity user);
	public int updateByPrimaryKey(UserEntity user);
	public int updateByPrimaryKeySelective(UserEntity user);
	
	public UserDetailEntitiy queryDetailByPhoneOrMailOrCode(String str);
	
	public UserEntity queryByPhone(String phone);
	
	public UserEntity queryByNameAndPasswd(@Param("code")String str,@Param("password")String password);
	
	public UserEntity queryByMail(String mail);
	
	public UserDetailEntitiy queryById(int id);
	public UserDetailEntitiy queryByCode(String code);
	
	/**
	 * 	给用户增加角色
	 * @param id
	 * @param codes
	 * @return
	 * @author 王帆
	 * @date 2019年1月25日 下午3:51:48
	 */
	public int addUserRoles(@Param("userId")int id,@Param("rolecodes") List<String> codes,@Param("createId")int createId);
	
	/**
	 * 	删除用户的权限
	 * @param id
	 * @param codes
	 * @return
	 * @author 王帆
	 * @date 2019年1月25日 下午3:53:04
	 */
	public int delUserRoles(@Param("userId")int id,@Param("rolecodes") List<String> codes);
}
