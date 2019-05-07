package com.sky.auth.config.service;

import java.util.List;

import com.sky.auth.config.entity.AuthorityContainerBean;
import com.sky.auth.config.entity.AuthorityEntity;
import com.sky.pub.Page;
import com.sky.pub.PageRequest;
import com.sky.pub.common.exception.ResultException;

/**
 * 权限操作service
 * @author 王帆
 * @date  2019年4月9日 上午9:14:47
 */
public interface AuthorityService {
	
	/**
	 * 新增权限
	 * @param auth
	 * @return
	 * @author 王帆
	 * @date 2019年4月9日 上午9:16:12
	 */
	public boolean addAuthority(AuthorityEntity auth) throws ResultException;
	
	/**
	 * 重置作废的权限为有效
	 * <pre>
	 *  1:权限状态为作废
	 *  2：恢复角色与权限关联数据 
	 *  2：恢复权限有效 
	 * </pre>
	 * @param auth
	 * @return
	 * @author 王帆
	 * @date 2019年4月9日 上午10:02:38
	 */
	public boolean activeAuthority(AuthorityEntity auth) throws ResultException;
	
	/**
	 * 作废权限
	 * <pre>
	 *  1:作废权限本身
	 *  2：作废角色与权限关联数据 
	 * </pre>
	 * @param auth
	 * @return
	 * @author 王帆
	 * @date 2019年4月9日 上午10:02:38
	 */
	public boolean disableAuthority(AuthorityEntity auth) throws ResultException;
	
	/**
	 * 删除权限
	 * <pre>
	 * 	1:权限必须是已作废的
	 *  2：删除关联的权限
	 *  3：删除本身的权限
	 * </pre>
	 * @param auth
	 * @return
	 * @author 王帆
	 * @date 2019年4月9日 上午10:02:53
	 */
	public boolean deleteAuthority(AuthorityEntity auth) throws ResultException;
	
	/**
	 * 根据权限id查询权限
	 * @param id
	 * @return
	 * @author 王帆
	 * @date 2019年4月9日 下午1:12:21
	 */
	public AuthorityEntity queryByPrimaryId(Integer id);
	
	/**
	 * 根据权限编码查询权限
	 * @param code
	 * @return
	 * @author 王帆
	 * @date 2019年4月9日 下午1:12:42
	 */
	public AuthorityEntity queryByCode(String code);
	
	/**
	 * 根据权限编码查询权限与角色数据
	 * @param codes
	 * @return
	 * @author 王帆
	 * @date 2019年4月16日 上午9:12:56
	 */
	public AuthorityContainerBean queryAuthorityByCodes(List<String> codes);

	/**
	 * 分页查询权限数据
	 * @param page
	 * @return
	 * @author 王帆
	 * @date 2019年4月19日 下午1:58:58
	 */
	public Page<AuthorityEntity> queryAuthorityByPage(PageRequest<AuthorityEntity> page);

	/**
	 * 根据条件查询权限数据集合
	 * @param auth
	 * @return
	 * @author 王帆
	 * @date 2019年4月19日 下午1:59:18
	 */
	public List<AuthorityEntity> queryAuthorityList(AuthorityEntity auth);
}
