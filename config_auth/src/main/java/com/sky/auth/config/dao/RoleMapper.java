package com.sky.auth.config.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sky.auth.config.entity.RoleEntity;
import com.sky.pub.BasePageRequest;


@Mapper
public interface RoleMapper {
	public int insert(RoleEntity record);
	
	public int insertSelective(RoleEntity record);
	
	public int updateByPrimaryKeySelective(RoleEntity record);

	public int updateByPrimaryKey(RoleEntity record);
	
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
	 * @return
	 * @author 王帆
	 * @date 2019年1月25日 下午2:56:41
	 */
	public List<RoleEntity> queryRoleByPage(BasePageRequest page);
	
	/**
	 * 	 根据角色编码查询角色包含权限
	 * @param code
	 * @return
	 * @author 王帆
	 * @date 2019年1月25日 下午2:58:22
	 */
	public RoleEntity queryRoleDetailsByRole(String code);
}
