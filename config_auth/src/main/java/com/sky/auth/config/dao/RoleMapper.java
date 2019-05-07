package com.sky.auth.config.dao;

import java.util.List;
import java.util.Set;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.sky.auth.config.entity.AuthorityBean;
import com.sky.auth.config.entity.RoleEntity;
import com.sky.auth.config.entity.req.RoleRequestEntity;
import com.sky.pub.PageRequest;


@Mapper
public interface RoleMapper {
	public int insert(RoleEntity record);
	
	public int insertSelective(RoleEntity record);
	
	public int updateByPrimaryKeySelective(RoleEntity record);

	public int updateByPrimaryKey(RoleEntity record);
	
	/**
	 * 根据角色编码删除角色
	 * @param code
	 * @return
	 * @author 王帆
	 * @date 2019年4月8日 下午5:16:24
	 */
	public int deleteRole(String code);
	
	/**
	 * 	添加角色关联的权限编码
	 * @param code
	 * @param authcodes
	 * @return
	 * @author 王帆
	 * @date 2019年1月25日 下午2:53:40
	 */
	public int addRoleAuth(@Param("code")String code,@Param("auths")List<String> authcodes,@Param("createid")String createid);
	
	/**
	 * 	删除用户的角色权限关联
	 * @param code
	 * @param authcodes
	 * @return
	 * @author 王帆
	 * @date 2019年1月25日 下午3:06:40
	 */
	public int delRoleAuth(@Param("code")String code,@Param("auths")List<String> authcodes);
	
	/**
	 * 	分页查询权限
	 * @param page
	 * @param role
	 * @return
	 * @author 王帆
	 * @date 2019年1月25日 下午2:56:41
	 */
	public List<RoleEntity> queryRoleByPage(@Param("role") RoleRequestEntity role,@SuppressWarnings("rawtypes") @Param("page")PageRequest page );
	
	/**
	 * 统计角色数据
	 * @param role
	 * @return
	 * @author 王帆
	 * @date 2019年4月8日 下午4:46:10
	 */
	public int accountRole(@Param("role") RoleRequestEntity role);
	
	/**
	 * 	 根据角色编码查询角色包含权限
	 * @param code
	 * @return
	 * @author 王帆
	 * @date 2019年1月25日 下午2:58:22
	 */
	public RoleEntity queryRoleDetailsByRole(String code);

	/**
	 * 根据角色编码查询角色基本信息
	 * @param codes
	 * @return
	 * @author 王帆
	 * @date 2019年4月16日 上午9:34:52
	 */
	public Set<AuthorityBean> queryByRoleCodes(List<String> codes);
	
	/**
	 * 根据角色查询关联的权限
	 * @param code
	 * @return
	 * @author 王帆
	 * @date 2019年5月1日 下午10:04:40
	 */
	public Set<String> queryAuthsbyRole(String  code);
}
