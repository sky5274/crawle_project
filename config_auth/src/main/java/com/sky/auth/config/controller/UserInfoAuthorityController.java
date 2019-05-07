package com.sky.auth.config.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sky.auth.config.entity.RoleEntity;
import com.sky.auth.config.entity.UserDetailEntitiy;
import com.sky.auth.config.entity.UserEntity;
import com.sky.auth.config.entity.req.UserRequestEntity;
import com.sky.auth.config.service.UserAuthorityService;
import com.sky.auth.config.service.UserInfoService;
import com.sky.pub.Page;
import com.sky.pub.PageRequest;
import com.sky.pub.Result;
import com.sky.pub.ResultUtil;
import com.sky.pub.common.exception.ResultException;

/**
 * 用户信息以及用户角色控制器
 * @author 王帆
 * @date  2019年4月10日 上午9:56:12
 */
@RestController
@RequestMapping("/user")
public class UserInfoAuthorityController {
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private UserAuthorityService userAuthorityService;
	
	/**
	 * 根据参数查询用户信息
	 * @param user
	 * @return
	 * @author 王帆
	 * @date 2019年4月10日 上午10:29:56
	 */
	@PostMapping("/query/list")
	public Result<List<UserEntity>> queryUserList(@RequestBody UserRequestEntity user){
		return ResultUtil.getOk(userInfoService.queryUserInfo(user));
	}
	
	/**
	 * 分页查询用户信息
	 * @param pageuser
	 * @return
	 * @author 王帆
	 * @date 2019年5月4日 下午5:41:42
	 */
	@PostMapping("/query/page")
	public Result<Page<UserEntity>> queryUserListByPage(@RequestBody PageRequest<UserRequestEntity> pageuser){
		return ResultUtil.getOk(userInfoService.queryUserListByPage(pageuser));
	}
	
	/**
	 * 根据用户id查询用户信息
	 * @param userId
	 * @return
	 * @author 王帆
	 * @date 2019年4月10日 上午10:32:15
	 */
	@RequestMapping("info")
	public Result<UserDetailEntitiy> queryUserDetail(int  userId){
		return ResultUtil.getOk(userInfoService.queryUserDetailById(userId)); 
	}
	
	/**
	 * 添加用户
	 * @param user
	 * @return
	 * @throws ResultException
	 * @author 王帆
	 * @date 2019年4月10日 上午10:46:35
	 */
	@PreAuthorize("hasAuthority('USER_OPERATION') or hasRole('ADMIN')")
	@PostMapping("/add")
	public Result<Boolean> addUser(@RequestBody UserEntity user) throws ResultException {
		return ResultUtil.getOk(userInfoService.addUser(user));
	}
	
	/**
	 * 修改用户信息（不包含密码）
	 * @param user
	 * @return
	 * @throws ResultException
	 * @author 王帆
	 * @date 2019年4月10日 上午10:48:54
	 */
	@PreAuthorize("hasAuthority('USER_OPERATION') or hasRole('ADMIN')")
	@PostMapping("modify")
	public Result<Boolean> modifygUser(@RequestBody UserEntity user) throws ResultException {
		return ResultUtil.getOk(userInfoService.updateUser(user));
	}
	
	/**
	 * 删除用户
	 * @param user
	 * @return
	 * @throws ResultException
	 * @author 王帆
	 * @date 2019年4月10日 上午10:49:57
	 */
	@PreAuthorize("hasAuthority('USER_OPERATION') or hasRole('ADMIN')")
	@PostMapping("delete")
	public Result<Boolean> deleteUser(@RequestBody UserEntity user) throws ResultException {
		return ResultUtil.getOk(userInfoService.deleteUser(user));
	}
	
	/**
	 * 用户修改密码
	 * @param user
	 * @return
	 * @throws ResultException
	 * @author 王帆
	 * @date 2019年4月10日 上午10:50:28
	 */
	@PostMapping("/modify/password")
	public Result<Boolean> modifyPassword(@RequestBody UserRequestEntity  user) throws ResultException {
		return ResultUtil.getOk(userInfoService.modifyUserPassword(user));
	}
	
	/**
	 * 添加用户角色
	 * @param user
	 * @return
	 * @throws ResultException
	 * @author 王帆
	 * @date 2019年4月10日 上午11:41:04
	 */
	@PreAuthorize("hasAuthority('USER_OPERATION') or hasRole('ADMIN')")
	@PostMapping("/role/add")
	public Result<Boolean> addRoleInUser(@RequestBody UserRequestEntity user) throws ResultException {
		return ResultUtil.getOk(userAuthorityService.addUserRoles(user.getId(), user.getRoleCodes()));
	}
	
	/**
	 * 改变用户的
	 * @param user
	 * @return
	 * @throws ResultException
	 * @author 王帆
	 * @date 2019年5月5日 上午9:32:11
	 */
	@PreAuthorize("hasAuthority('USER_OPERATION') or hasRole('ADMIN')")
	@PostMapping("/role/change")
	public Result<Boolean> changeRoleInUser(@RequestBody UserRequestEntity user) throws ResultException {
		return ResultUtil.getOk(userAuthorityService.changeUserRoles(user.getId(), user.getRoleCodes()));
	}
	
	/**
	 * 删除用户的角色
	 * @param user
	 * @return
	 * @throws ResultException
	 * @author 王帆
	 * @date 2019年4月10日 上午11:41:23
	 */
	@PreAuthorize("hasAuthority('USER_OPERATION') or hasRole('ADMIN')")
	@PostMapping("/role/delete")
	public Result<Boolean> deleteRolesFromUser(@RequestBody UserRequestEntity user) throws ResultException{
		return ResultUtil.getOk(userAuthorityService.deleteUserRoles(user.getId(), user.getRoleCodes()));
	}
	
	/**
	 * 根据用户id查询角色数据
	 * @param userId
	 * @return
	 * @author 王帆
	 * @throws ResultException 
	 * @date 2019年4月10日 上午11:44:10
	 */
	@RequestMapping("/role/query")
	public Result<List<RoleEntity>> queryRolesByUserId(int userId) throws ResultException {
		return ResultUtil.getOk(userAuthorityService.queryRolesByUser(userId));
	}
}
