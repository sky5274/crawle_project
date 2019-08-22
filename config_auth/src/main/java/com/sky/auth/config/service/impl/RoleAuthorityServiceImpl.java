package com.sky.auth.config.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import com.sky.auth.config.dao.AuthorityMapper;
import com.sky.auth.config.dao.RoleMapper;
import com.sky.auth.config.dao.UserMapper;
import com.sky.auth.config.entity.AuthorityBean;
import com.sky.auth.config.entity.AuthorityContainerBean;
import com.sky.auth.config.entity.AuthorityEntity;
import com.sky.auth.config.entity.RoleEntity;
import com.sky.auth.config.entity.UserDetailEntitiy;
import com.sky.auth.config.entity.req.RoleRequestEntity;
import com.sky.auth.config.service.AuthorityService;
import com.sky.auth.config.service.RoleService;
import com.sky.auth.config.service.UserAuthorityService;
import com.sky.auth.config.util.UserUtil;
import com.sky.pub.Page;
import com.sky.pub.PageRequest;
import com.sky.pub.ResultAssert;
import com.sky.pub.common.exception.ResultException;
import com.sky.pub.util.ListUtils;

@Service
@Transactional(rollbackFor=Exception.class)
public class RoleAuthorityServiceImpl implements RoleService,AuthorityService,UserAuthorityService{
	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private AuthorityMapper authorityMapper;

	/**
	 * 通用校验角色数据，以及更新操作人信息
	 * @param role
	 * @throws ResultException
	 * @author 王帆
	 * @date 2019年4月8日 下午3:57:16
	 */
	private void commonValidAndUpdateUser(RoleEntity role) throws ResultException {
		UserDetailEntitiy user = UserUtil.getUser();
		ResultAssert.isEmpty(user, "用户信息缺失");
		ResultAssert.isEmpty(role, "角色为空");
		ResultAssert.isBlank(role.getCode(), "角色编码为空");
		ResultAssert.isTure(role.getCode().startsWith("ROLE_"), "角色编码不能以ROLE_开头");
		ResultAssert.isBlank(role.getName(), "角色名称为空");
		role.setCreateid(user.getId());
		role.setCreateName(user.getUsername());
	}
	
	/**
	 * 查询角色编码是否存在
	 * @param code
	 * @return
	 * @author 王帆
	 * @date 2019年4月8日 下午5:13:52
	 */
	private boolean hasRoleCode(String code) {
		RoleRequestEntity role=new RoleRequestEntity();
		role.setCode(code);
		return roleMapper.accountRole(role)>0;
	}
	
	@Override
	public boolean addRole(RoleEntity role) throws ResultException {
		commonValidAndUpdateUser(role);
		ResultAssert.isTure(hasRoleCode(role.getCode()), "角色编："+role.getCode()+" 已存在！");
		//插入角色数据
		int size=roleMapper.insertSelective(role);
		//删除角色编码对应的权限
		delRoleAuth(role.getCode());
		if(size>0 && !ListUtils.isEmpty(role.getAuths())) {
			//添加角色对应的权限
			List<String> auths=new LinkedList<>();
			for(AuthorityEntity auth:role.getAuths()) {
				auths.add(auth.getAuthcode());
			}
			size=roleMapper.addRoleAuth(role.getCode(), auths, role.getCreateid()+"");
		}
		//断言判断：size<=0 时，提示：角色添加失败
		ResultAssert.isFalse(size>0, "角色添加失败");
		return true;
	}

	@Override
	public boolean modifyRole(RoleEntity role) throws ResultException {
		commonValidAndUpdateUser(role);
		//跟新角色信息
		int size = roleMapper.updateByPrimaryKeySelective(role);
		size=1;
		if(size>0 && !ListUtils.isEmpty(role.getAuths())) {
			//重置角色对应的权限名
			delRoleAuth(role.getCode());
			List<String> auths=new LinkedList<>();
			for(AuthorityEntity auth:role.getAuths()) {
				auths.add(auth.getAuthcode());
			}
			size=roleMapper.addRoleAuth(role.getCode(), auths, role.getCreateid()+"");
		}
		//断言判断：size<=0 时，提示：角色变更失败
		ResultAssert.isFalse(size>0, "角色变更失败");
		return true;
	}

	/**
	 * 删除角色编码对应的所有权限关联
	 * @param code
	 * @author 王帆
	 * @date 2019年4月8日 下午4:35:58
	 */
	private void delRoleAuth(String code) {
		roleMapper.delRoleAuth(code, null);
	}
	
	@Override
	public boolean delRole(RoleEntity role) throws ResultException {
		ResultAssert.isFalse(hasRoleCode(role.getCode()), "角色编："+role.getCode()+" 不存在，无法删除！");
		delRoleAuth(role.getCode());
		int size = roleMapper.deleteRole(role.getCode());
		ResultAssert.isTure(size<=0, "删除角色失败");
		return true;
	}

	@Override
	public RoleEntity queryRoleByCode(String code) {
		return roleMapper.queryRoleDetailsByRole(code);
	}
	@Override
	public Page<RoleEntity> queryRoleByPage(PageRequest<RoleRequestEntity> page) {
		page.initPage();
		Page<RoleEntity> pageData=new Page<>();
		pageData.setPageData(page);
		pageData.setList(roleMapper.queryRoleByPage(page.getData(), page));
		pageData.setTotal(roleMapper.accountRole(page.getData()));
		return pageData;
	}
	
	@Override
	public Set<String> queryAuthsByRole(String code) {
		return roleMapper.queryAuthsbyRole(code);
	}

	@Override
	public boolean changeRoleAuthorities(String rolecode, List<String> authorityCodes) throws ResultException {
		ResultAssert.isBlank(rolecode, "角色编码为空");
		ResultAssert.isTure(authorityCodes==null || authorityCodes.isEmpty(), "权限编码为空");
		UserDetailEntitiy user = UserUtil.getUser();
		roleMapper.delRoleAuth(rolecode, null);
		return roleMapper.addRoleAuth(rolecode, authorityCodes,user.getId()+"")>0;
	}

	/**
	 * 通用校验：权限字段是否为空
	 * @param auth
	 * @throws ResultException
	 * @author 王帆
	 * @date 2019年4月9日 上午10:50:26
	 */
	private void commonValidAuthority(AuthorityEntity auth) throws ResultException{
		ResultAssert.isBlank(auth.getAuthcode(), "权限编码为空");
		ResultAssert.isTure(auth.getAuthcode().startsWith("ROLE_"), "权限编码不能以ROLE_开头");
		ResultAssert.isBlank(auth.getAuthname(), "权限编码为空");
	}
	
	@Override
	public boolean addAuthority(AuthorityEntity auth) throws ResultException {
		commonValidAuthority(auth);
		auth=UserUtil.setUserInfo(auth);
		return authorityMapper.insertSelective(auth)>0;
	}
	
	
	/**
	 * 获取最新的的权限数据，并校验是否能查询到数据
	 * @param auth
	 * @return
	 * @throws ResultException
	 * @author 王帆
	 * @date 2019年4月9日 下午1:42:52
	 */
	private AuthorityEntity getNewAuthority(AuthorityEntity auth) throws ResultException {
		ResultAssert.isBlank(auth.getAuthcode(), "权限编码为空");
		AuthorityEntity newauth = queryByCode(auth.getAuthcode());
		ResultAssert.isEmpty(newauth, "权限编码:"+auth.getAuthcode()+" 无对应的权限数据");
		return newauth;
	}
	
	/**
	 * 当修改权限数据时校验：数据是否变更、权限状态
	 * @param auth
	 * @throws ResultException
	 * @author 王帆
	 * @date 2019年4月9日 下午2:12:50
	 */
	private void comValidAuthorityWhenModify(AuthorityEntity auth,Character status,String tips) throws ResultException {
		AuthorityEntity newauth = getNewAuthority(auth);
		ResultAssert.isTure(newauth.getVersion()!=auth.getVersion(),"权限数据已变更，请重新刷新数据"); 
		if(!StringUtils.isEmpty(status)) {
			ResultAssert.isTure(!status.equals(newauth.getVailFlag()),tips);
		}
	}
	
	@Override
	public boolean disableAuthority(AuthorityEntity auth) throws ResultException{
		//修改权限时，必要校验
		comValidAuthorityWhenModify(auth,'Y',"非有效权限无需作废");
		//设置操作人信息，修改信息
		auth=UserUtil.setUserInfo(auth);
		auth.setVailFlag('N');
		//变更权限数据操作
		return updateAuthority(auth);
	}
	
	@Override
	public boolean activeAuthority(AuthorityEntity auth) throws ResultException {
		comValidAuthorityWhenModify(auth,'N',"只有作废的权限才需重新生效");
		auth=UserUtil.setUserInfo(auth);
		auth.setVailFlag('Y');
		return updateAuthority(auth);
	}
	
	/**
	 * 更新权限数据、角色与权限关联数据
	 * @param auth
	 * @return
	 * @author 王帆
	 * @throws ResultException 
	 * @date 2019年4月9日 下午2:31:45
	 */
	private boolean updateAuthority(AuthorityEntity auth) throws ResultException {
		ResultAssert.isFalse(authorityMapper.updateByPrimaryKeySelective(auth)>0,"权限更新失败");
		authorityMapper.updateAuthorityRelateRole(auth);
		return true;
	}

	@Override
	public boolean deleteAuthority(AuthorityEntity auth) throws ResultException{
		comValidAuthorityWhenModify(auth,'N',"有效权限无法删除，请先作废");
		authorityMapper.deleteAuthorityRelateRole(auth.getAuthcode());
		ResultAssert.isFalse(authorityMapper.deleteAuthority(auth.getId())>0,"权限更新失败");
		return true;
	}
	
	@Override
	public AuthorityEntity queryByPrimaryId(Integer id) {
		return authorityMapper.queryByPrimaryKey(id);
	}

	@Override
	public AuthorityEntity queryByCode(String code) {
		return authorityMapper.queryByCode(code);
	}

	@Override
	public boolean addUserRoles(Integer userId, List<String> roleCodes) throws ResultException {
		ResultAssert.isEmpty(userId,"请提交用户id");
		ResultAssert.isFalse(roleCodes !=null && !roleCodes.isEmpty(),"请提交角色编码");
		UserDetailEntitiy user = UserUtil.getUser();
		return userMapper.addUserRoles(userId, roleCodes, user.getId())>0;
	}

	@Override
	public boolean deleteUserRoles(Integer userId, List<String> roleCodes) throws ResultException {
		ResultAssert.isEmpty(userId,"请提交用户id");
		return userMapper.delUserRoles(userId, roleCodes)>0;
	}
	
	@Override
	public boolean changeUserRoles(Integer userId, List<String> roleCodes) throws ResultException {
		ResultAssert.isEmpty(userId,"请提交用户id");
		boolean flag = userMapper.delUserRoles(userId, roleCodes)>0;
		if(!ListUtils.isEmpty(roleCodes)) {
			UserDetailEntitiy user = UserUtil.getUser();
			flag=userMapper.addUserRoles(userId, roleCodes, user.getId())>0;
		}
		return flag;
	}

	@Override
	public List<RoleEntity> queryRolesByUser(Integer userid) throws ResultException {
		ResultAssert.isEmpty(userid,"请提交用户id");
		return userMapper.queryRoleInfoByUser(userid+"");
	}

	@Override
	public AuthorityContainerBean queryAuthorityByCodes(List<String> codes) {
		AuthorityContainerBean container=new AuthorityContainerBean();
		container.setAuths(authorityMapper.queryByCodes(codes));
		container.setRoles(roleMapper.queryByRoleCodes(codes));
		return container;
	}

	@Override
	public Page<AuthorityEntity> queryAuthorityByPage(PageRequest<AuthorityEntity> page) {
		Page<AuthorityEntity> pageData=new Page<>();
		page.initPage();
		pageData.setPageData(page);
		pageData.setList(authorityMapper.queryAuthsByPage(page, page.getData()));
		pageData.setTotal(authorityMapper.countAuthority(page.getData()));
		return pageData;
	}

	@Override
	public List<AuthorityEntity> queryAuthorityList(AuthorityEntity auth) {
		return authorityMapper.queryAuthsByPage(null,auth);
	}

	@Override
	public Set<AuthorityBean> queryAuthListByRole(String code) {
		Set<String> authcodes = roleMapper.queryAuthsbyRole(code);
		return ListUtils.isEmpty(authcodes)? null: authorityMapper.queryByCodes(authcodes);
	}

}
