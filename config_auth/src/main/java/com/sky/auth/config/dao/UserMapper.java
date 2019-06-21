package com.sky.auth.config.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sky.auth.config.entity.RoleEntity;
import com.sky.auth.config.entity.UserDetailEntitiy;
import com.sky.auth.config.entity.UserEntity;
import com.sky.auth.config.entity.req.UserRequestEntity;
import com.sky.pub.Page;
import com.sky.pub.PageRequest;

@Mapper
public interface UserMapper {
	public int insert(UserEntity user);
	public int insertSelective(UserEntity user);
	public int updateByPrimaryKey(UserEntity user);
	public int updateByPrimaryKeySelective(UserEntity user);
	
	/**
	 * 删除用户信息
	 * @param user
	 * @return
	 * @author 王帆
	 * @date 2019年4月9日 下午4:48:14
	 */
	public int deleteUser(UserEntity user);
	
	/**
	 * 根据用户名\电话\邮箱  查询用户
	 * @param str
	 * @return
	 * @author 王帆
	 * @date 2019年4月9日 下午4:47:37
	 */
	public UserDetailEntitiy queryDetailByPhoneOrMailOrCode(String str);
	
	/**
	 * 根据电话查询用户
	 * @param phone
	 * @return
	 * @author 王帆
	 * @date 2019年4月9日 下午4:47:19
	 */
	public UserEntity queryByPhone(String phone);
	
	/**
	 * 根据用户名与密码查询用户
	 * @param str
	 * @param password
	 * @return
	 * @author 王帆
	 * @date 2019年4月9日 下午4:46:49
	 */
	public UserEntity queryByNameAndPasswd(@Param("code")String str,@Param("password")String password);
	/**
	 * 根据邮箱查询用户
	 * @param mail
	 * @return
	 * @author 王帆
	 * @date 2019年4月9日 下午4:46:31
	 */
	public UserEntity queryByMail(String mail);
	
	public UserDetailEntitiy queryById(int id);
	public UserDetailEntitiy queryByCode(String code);
	
	/**
	 * 根据用户的姓名、手机号、邮箱查询用户信息
	 * @param use
	 * @return
	 * @author 王帆
	 * @date 2019年3月24日 上午10:38:58
	 */
	public List<UserEntity> queryUserByPhoneOrMailOrCode(UserEntity use);
	
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
	
	/**
	 * 根据用户的id查询权限信息
	 * @param userid
	 * @return
	 * @author 王帆
	 * @date 2019年4月9日 上午10:57:55
	 */
	public List<RoleEntity> queryRoleInfoByUser(String userid);
	
	/**
	 * 根据用户的参数查询用户集合信息
	 * @param user
	 * @return
	 * @author 王帆
	 * @date 2019年4月9日 下午5:05:18
	 */
	public List<UserEntity> queryUserListByParams(UserEntity user);
	
	/**
	 * 分页查用户信息 
	 * @param page
	 * @param user
	 * @return
	 * @author 王帆
	 * @date 2019年5月4日 下午5:30:43
	 */
	public List<UserEntity> queryUserListByPageParams(PageRequest page,UserRequestEntity user);
	
	/**
	 * 根据调解统计用户数量
	 * @param user
	 * @return
	 * @author 王帆
	 * @date 2019年5月4日 下午5:31:11
	 */
	public int countUserListByParams(UserRequestEntity user);
}
