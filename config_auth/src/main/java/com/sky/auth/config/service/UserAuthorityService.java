package com.sky.auth.config.service;

import java.util.List;

import com.sky.auth.config.entity.RoleEntity;
import com.sky.pub.common.exception.ResultException;

/**
 * 用户权限service
 * @author 王帆
 * @date  2019年4月9日 上午10:09:35
 */
public interface UserAuthorityService {
	
	/**
	 * 添加用户的角色
	 * @param userId  目标用户的id
	 * @param roleCodes   添加角色编码集合
	 * @return
	 * @author 王帆
	 * @date 2019年4月9日 上午10:25:28
	 */
	public boolean addUserRoles(Integer userId,List<String> roleCodes) throws ResultException;
	
	/**
	 * 删除用户的角色
	 * @param UserId  目标用户id
	 * @param roleCodes  要删除的角色编码
	 * @return
	 * @author 王帆
	 * @date 2019年4月9日 上午10:26:02
	 */
	public boolean deleteUserRoles(Integer UserId,List<String> roleCodes) throws ResultException;
	
	/**
	 * 改变客户的角色授权
	 * @param id
	 * @param roleCodes
	 * @return
	 * @author 王帆
	 * @throws ResultException 
	 * @date 2019年5月5日 上午9:31:04
	 */
	public boolean changeUserRoles(Integer id, List<String> roleCodes) throws ResultException;
	
	/**
	 * 根据用户信息查询
	 * @param user
	 * @return
	 * @author 王帆
	 * @throws ResultException 
	 * @date 2019年4月9日 上午10:32:13
	 */
	public List<RoleEntity> queryRolesByUser(Integer userid) throws ResultException;

	
}
