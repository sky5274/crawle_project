package com.sky.auth.config.service;


import java.util.List;
import java.util.Set;

import com.sky.auth.config.entity.AuthorityBean;
import com.sky.auth.config.entity.RoleEntity;
import com.sky.auth.config.entity.req.RoleRequestEntity;
import com.sky.pub.Page;
import com.sky.pub.PageRequest;
import com.sky.pub.common.exception.ResultException;

/**
 * 用户角色service
 * @author 王帆
 * @date  2019年4月8日 下午1:25:48
 */
public interface RoleService {
	
	/**
	 * 角色添加
	 * @param role
	 * @return
	 * @throws ResultException
	 * @author 王帆
	 * @date 2019年4月9日 上午10:10:17
	 */
	public boolean addRole(RoleEntity role) throws ResultException;
	
	/**
	 * 修改角色
	 * @param role
	 * @return
	 * @throws ResultException
	 * @author 王帆
	 * @date 2019年4月9日 上午10:10:33
	 */
	public boolean modifyRole(RoleEntity role)  throws ResultException;
	
	/**
	 * 删除角色
	 * @param role
	 * @return
	 * @throws ResultException
	 * @author 王帆
	 * @date 2019年4月9日 上午10:10:50
	 */
	public boolean delRole(RoleEntity role)  throws ResultException;
	
	/**
	 * 分页查询角色
	 * @param page
	 * @return
	 * @author 王帆
	 * @date 2019年4月8日 下午1:36:32
	 */
	public Page<RoleEntity> queryRoleByPage(PageRequest<RoleRequestEntity> page);
	
	/**
	 * 根据角色编码查询角色
	 * @param code
	 * @return
	 * @author 王帆
	 * @date 2019年4月10日 上午9:33:04
	 */
	public RoleEntity queryRoleByCode(String code);
	
	/**
	 * 修改用户的角色权限
	 * @param rolecode
	 * @param authorityCodes
	 * @return
	 * @author 王帆
	 * @date 2019年4月8日 下午1:34:54
	 */
	public boolean changeRoleAuthorities(String rolecode,List<String> authorityCodes)  throws ResultException;

	/**
	 * 根据角色编码查询关联的权限编码
	 * @param code
	 * @return
	 * @author 王帆
	 * @date 2019年5月1日 下午10:07:34
	 */
	public Set<String> queryAuthsByRole(String code);
	
	/**
	 * 根据角色查询权限信息
	 * @param code
	 * @return
	 * @author 王帆
	 * @date 2019年5月4日 下午5:17:56
	 */
	public Set<AuthorityBean> queryAuthListByRole(String code);
}
