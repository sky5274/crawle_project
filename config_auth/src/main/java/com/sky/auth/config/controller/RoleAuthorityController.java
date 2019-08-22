package com.sky.auth.config.controller;

import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sky.auth.config.entity.AuthorityBean;
import com.sky.auth.config.entity.AuthorityContainerBean;
import com.sky.auth.config.entity.AuthorityEntity;
import com.sky.auth.config.entity.RoleEntity;
import com.sky.auth.config.entity.req.RoleRequestEntity;
import com.sky.auth.config.service.AuthorityService;
import com.sky.auth.config.service.RoleService;
import com.sky.pub.Page;
import com.sky.pub.PageRequest;
import com.sky.pub.Result;
import com.sky.pub.ResultUtil;
import com.sky.pub.common.exception.ResultException;

/**
 * 角色+权限控制器
 * @author 王帆
 * @date  2019年4月8日 下午1:39:14
 */
@RestController
@RequestMapping("/permission")
public class RoleAuthorityController {

	@Autowired
	private RoleService roleService;
	@Autowired
	private AuthorityService authorityService;
	
	
	/**
	 * 新增角色
	 * @param role
	 * @return
	 * @throws ResultException
	 * @author 王帆
	 * @date 2019年4月10日 上午9:11:23
	 */
	@PreAuthorize("hasAuthority('AUTH_OPERATION') or hasRole('ADMIN')")
	@PostMapping("/role/add")
	public Result<Boolean> addRole(@RequestBody RoleEntity role) throws ResultException{
		return ResultUtil.getOk(roleService.addRole(role));
	}
	
	/**
	 * 修改角色
	 * @param role
	 * @return
	 * @throws ResultException
	 * @author 王帆
	 * @date 2019年4月10日 上午9:13:25
	 */
	@PreAuthorize("hasAnyRole('ADMIN')")
	@PostMapping("/role/modify")
	public Result<Boolean> modifyRole(@RequestBody RoleEntity role) throws ResultException {
		return ResultUtil.getOk(roleService.modifyRole(role));
	}
	
	/**
	 * 删除角色
	 * @param role
	 * @return
	 * @throws ResultException
	 * @author 王帆
	 * @date 2019年4月10日 上午9:17:08
	 */
	@PreAuthorize("hasAuthority('AUTH_OPERATION') or hasRole('ADMIN')")
	@PostMapping("/role/delete")
	public Result<Boolean> deleteyRole(@RequestBody RoleEntity role) throws ResultException {
		return ResultUtil.getOk(roleService.delRole(role));
	}
	
	/**
	 * 改变角色权限关联
	 * @param role
	 * @return
	 * @throws ResultException
	 * @author 王帆
	 * @date 2019年4月10日 上午9:36:45
	 */
	@PreAuthorize("hasAuthority('AUTH_OPERATION') or hasRole('ADMIN')")
	@PostMapping("/role/change/auth")
	public Result<Boolean> changRoleAndAuthorityRelative(@RequestBody RoleRequestEntity role) throws ResultException {
		return ResultUtil.getOk(roleService.changeRoleAuthorities(role.getCode(), role.getAuthorityCodes()));
	}
	
	/**
	 * 根据角色编码查询角色信息
	 * @param code
	 * @return
	 * @author 王帆
	 * @date 2019年4月10日 上午9:42:12
	 */
	@RequestMapping("/role/query/{code}")
	public Result<RoleEntity> queryRoleByCode(@PathVariable("code") String code){
		return ResultUtil.getOk(roleService.queryRoleByCode(code));
	}
	
	/**
	 * 根据角色编码查询权限编码
	 * @param rolecode
	 * @return
	 * @author 王帆
	 * @date 2019年4月10日 上午9:42:12
	 */
	@RequestMapping("/query/auths")
	public Result<Set<String>> queryAuthCodesByRle(String rolecode){
		return ResultUtil.getOk(roleService.queryAuthsByRole(rolecode));
	}
	
	/**
	 * 根据角色编码查询权限信息
	 * @param rolecode
	 * @return
	 * @author 王帆
	 * @date 2019年4月10日 上午9:42:12
	 */
	@RequestMapping("/query/authlist")
	public Result<Set<AuthorityBean>> queryAuthInfoByRle(String rolecode){
		return ResultUtil.getOk(roleService.queryAuthListByRole(rolecode));
	}
	
	/**
	 * 根据条件分页查询角色数据
	 * @param page
	 * @return
	 * @author 王帆
	 * @date 2019年4月10日 上午9:20:07
	 */
	@PostMapping("/role/page")
	public Result<Page<RoleEntity>> queryRoleByPage(@RequestBody PageRequest<RoleRequestEntity> page) {
		return ResultUtil.getOk(roleService.queryRoleByPage(page));
	}
	
	/**
	 * 添加权限
	 * @param auth
	 * @return
	 * @throws ResultException
	 * @author 王帆
	 * @date 2019年4月10日 上午9:53:46
	 */
	@PreAuthorize("hasAuthority('AUTH_OPERATION') or hasRole('ADMIN')")
	@PostMapping("/auth/add")
	public Result<Boolean> addAuthority(@RequestBody AuthorityEntity auth) throws ResultException {
		return ResultUtil.getOk(authorityService.addAuthority(auth));
	}
	
	/**
	 * 恢复作废的权限
	 * @param auth
	 * @return
	 * @throws ResultException
	 * @author 王帆
	 * @date 2019年4月10日 上午9:54:02
	 */
	@PreAuthorize("hasAuthority('AUTH_OPERATION') or hasRole('ADMIN')")
	@PostMapping("/auth/active")
	public Result<Boolean> activeAuthority(@RequestBody AuthorityEntity auth) throws ResultException {
		return ResultUtil.getOk(authorityService.activeAuthority(auth));
	}
	
	/**
	 * 作废权限
	 * @param auth
	 * @return
	 * @throws ResultException
	 * @author 王帆
	 * @date 2019年4月10日 上午9:54:14
	 */
	@PreAuthorize("hasAuthority('AUTH_OPERATION') or hasRole('ADMIN')")
	@PostMapping("/auth/disable")
	public Result<Boolean> disableAuthority(@RequestBody AuthorityEntity auth) throws ResultException {
		return ResultUtil.getOk(authorityService.disableAuthority(auth));
	}
	
	/**
	 * 删除作废的权限
	 * @param auth
	 * @return
	 * @throws ResultException
	 * @author 王帆
	 * @date 2019年4月10日 上午9:54:28
	 */
	@PreAuthorize("hasAuthority('AUTH_OPERATION') or hasRole('ADMIN')")
	@PostMapping("/auth/delete")
	public Result<Boolean> deleteAuthority(@RequestBody AuthorityEntity auth) throws ResultException {
		return ResultUtil.getOk(authorityService.deleteAuthority(auth));
	}
	
	/**
	 * 分页查询权限数据
	 * @param page
	 * @return
	 * @author 王帆
	 * @date 2019年4月19日 下午1:56:43
	 */
	@PostMapping("/auth/page")
	public Result<Page<AuthorityEntity>> queryAuthorityByPage(@RequestBody PageRequest<AuthorityEntity> page ) {
		return ResultUtil.getOk(authorityService.queryAuthorityByPage(page));
	}
	
	/**
	 * 根据条件查询权限集合
	 * @param auth
	 * @return
	 * @author 王帆
	 * @date 2019年4月19日 下午1:56:56
	 */
	@PostMapping("/auth/list")
	public Result<List<AuthorityEntity>> queryAuthorityList(@RequestBody AuthorityEntity auth) {
		return ResultUtil.getOk(authorityService.queryAuthorityList(auth));
	}
	
	
	/**
	 * 根据权限编码查询权限信息
	 * @param code
	 * @return
	 * @author 王帆
	 * @date 2019年4月10日 上午9:54:44
	 */
	@RequestMapping("/auth/query/{code}")
	public Result<AuthorityEntity> queryAuthorityByCode(@PathVariable("code") String code){
		return ResultUtil.getOk(authorityService.queryByCode(code));
	}
	
	/**
	 * 根据编码查询权限/角色信息
	 * @param codes
	 * @return
	 * @author 王帆
	 * @date 2019年4月17日 下午4:30:17
	 */
	@RequestMapping(value="/query/codes",method=RequestMethod.POST)
	public Result<AuthorityContainerBean> queryAuthorityByCodes(@RequestBody List<String> codes){
		return ResultUtil.getOk(authorityService.queryAuthorityByCodes(codes));
	}
	
	
}
