package com.sky.auth.config.dao;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.sky.auth.config.entity.AuthorityBean;
import com.sky.auth.config.entity.AuthorityEntity;
import com.sky.pub.PageRequest;


/**
 * 权限mapper
 * @author 王帆
 * @date  2019年4月9日 下午2:35:03
 */
@Mapper
public interface AuthorityMapper {

	int insert(AuthorityEntity record);
	
	int insertSelective(AuthorityEntity record);
	
	int updateByPrimaryKeySelective(AuthorityEntity record);

	int updateByPrimaryKey(AuthorityEntity record);
	
	int deleteAuthority(Integer id);
	
	/**
	 * 根据权限编码删除角色关联的数据
	 * @param code
	 * @return
	 * @author 王帆
	 * @date 2019年4月9日 下午2:41:14
	 */
	int deleteAuthorityRelateRole(String code);
	
	/**
	 * 
	 * 根据权限跟新角色与权限关联的数据
	 * @param record
	 * @return
	 * @author 王帆
	 * @date 2019年4月9日 下午2:34:29
	 */
	int updateAuthorityRelateRole(AuthorityEntity record);
	
	/**
	 * 根据权限id查询权限信息
	 * @param id
	 * @return
	 * @author 王帆
	 * @date 2019年4月9日 下午1:08:22
	 */
	AuthorityEntity queryByPrimaryKey(Integer id);
	
	/**
	 * 根据权限编码查询权限信息
	 * @param code
	 * @return
	 * @author 王帆
	 * @date 2019年4月9日 下午1:08:03
	 */
	AuthorityEntity queryByCode(String code);

	/**
	 * 根据权限编码
	 * @param codes
	 * @return
	 * @author 王帆
	 * @date 2019年4月16日 上午9:35:21
	 */
	Set<AuthorityBean> queryByCodes(@Param("list") Collection<String> codes);
	
	/**
	 * 根据条件查询权限数据，可能有分页参数
	 * @param page
	 * @param auth
	 * @return
	 * @author 王帆
	 * @date 2019年4月19日 下午2:02:51
	 */
	List<AuthorityEntity> queryAuthsByPage(@Param("page")PageRequest page,@Param("auth")AuthorityEntity auth);
	
	@Select({"<script>"
			+ "select count(1) from config_auth "
			+ "<trim prefix='where ' suffixOverrides='and'> <if test='authcode !=null'>auth_code = #{authcode} </if> </trim>"
			+ "</script>"})
	int countAuthority(AuthorityEntity auth);
}
