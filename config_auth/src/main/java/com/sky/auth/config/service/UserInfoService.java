package com.sky.auth.config.service;

import java.util.List;

import com.sky.auth.config.entity.UserDetailEntitiy;
import com.sky.auth.config.entity.UserEntity;
import com.sky.auth.config.entity.req.UserRequestEntity;
import com.sky.pub.Page;
import com.sky.pub.PageRequest;
import com.sky.pub.common.exception.ResultException;

/**
 * 用户管理service
 * @author 王帆
 * @date  2019年4月9日 上午10:34:34
 */
public interface UserInfoService {
	
	/**
	 * 添加用户
	 * @param user
	 * @return
	 * @throws ResultException
	 * @author 王帆
	 * @date 2019年4月9日 上午10:40:44
	 */
	public boolean addUser(UserEntity user) throws ResultException;
	
	/**
	 * 跟新用户信息
	 * @param user
	 * @return
	 * @throws ResultException
	 * @author 王帆
	 * @date 2019年4月9日 上午10:40:56
	 */
	public boolean updateUser(UserEntity user) throws ResultException;
	
	/**
	 * 修改用户密码
	 * @param user
	 * @return
	 * @throws ResultException
	 * @author 王帆
	 * @date 2019年4月10日 上午10:26:42
	 */
	public boolean modifyUserPassword(UserRequestEntity user) throws ResultException;
	
	/**
	 * 删除用户
	 * <pre>1:先删除用户对应的权限，2在删除用户的数据</pre>
	 * @param user
	 * @return
	 * @throws ResultException
	 * @author 王帆
	 * @date 2019年4月9日 上午10:43:00
	 */
	public boolean deleteUser(UserEntity user) throws ResultException;
	
	/**
	 * 查询用户集合
	 * @param user
	 * @return
	 * @author 王帆
	 * @date 2019年4月9日 上午10:43:12
	 */
	public List<UserEntity> queryUserInfo(UserEntity user);
	
	/**
	 * 分页查询用户集合
	 * @param pageuser
	 * @return
	 * @author 王帆
	 * @date 2019年4月9日 上午10:43:12
	 */
	public Page<UserEntity> queryUserListByPage(PageRequest<UserRequestEntity> pageuser);
	
	/**
	 * 根据用户id查询用户的具体信息
	 * @param userId
	 * @return
	 * @author 王帆
	 * @date 2019年4月9日 上午10:43:29
	 */
	public UserDetailEntitiy queryUserDetailById(int userId);
}
