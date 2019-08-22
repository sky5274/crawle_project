package com.sky.auth.config.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sky.auth.config.dao.UserMapper;
import com.sky.auth.config.entity.UserDetailEntitiy;
import com.sky.auth.config.entity.UserEntity;
import com.sky.auth.config.entity.req.UserRequestEntity;
import com.sky.auth.config.service.UserInfoService;
import com.sky.auth.config.service.UserLoginService;
import com.sky.auth.config.util.UserUtil;
import com.sky.pub.Page;
import com.sky.pub.PageRequest;
import com.sky.pub.ResultAssert;
import com.sky.pub.common.exception.ResultException;

@Service
@Transactional(rollbackFor=Exception.class)
public class UserInfoSerivceImpl implements UserInfoService{
	
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private UserLoginService userLoginService;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder ;
	
	
	
	@Override
	public boolean addUser(UserEntity user) throws ResultException {
		ResultAssert.isBlank(user.getUsername(), "请提交用户名称");
		user=UserUtil.setUserInfo(user);
		return userLoginService.regist(user)!=null;
	}

	@Override
	public boolean updateUser(UserEntity user) throws ResultException {
		ResultAssert.isBlank(user.getUsername(), "请提交用户名称");
		user=UserUtil.setUserInfo(user);
		user.setPassword(null);
		return userMapper.updateByPrimaryKeySelective(user)>0;
	}

	@Override
	public boolean deleteUser(UserEntity user) throws ResultException {
		userMapper.delUserRoles(user.getId(), null);
		ResultAssert.isTure(userMapper.deleteUser(user)<=0, "删除用户失败");
		return true;
	}

	@Override
	public List<UserEntity> queryUserInfo(UserEntity user) {
		return userMapper.queryUserListByParams(user);
	}

	@Override
	public UserDetailEntitiy queryUserDetailById(int userId) {
		return userMapper.queryById(userId);
	}

	@Override
	public boolean modifyUserPassword(UserRequestEntity user) throws ResultException {
		ResultAssert.isEmpty(user, "未提交请求数据");
		//nomatch==1时强制更新用户的密码，不进行  旧密码校验
		if(!(user.getNoMatch()!=null && user.getNoMatch()==1)) {
			UserDetailEntitiy userInfo = userMapper.queryById(user.getId());
			ResultAssert.isEmpty(userInfo, "根据id未找到用户的信息");
			ResultAssert.isFalse(passwordEncoder.matches(user.getPassword(),userInfo.getPassword()), "您输出的旧密码有误");
		}
		user.setPassword(passwordEncoder.encode(user.getNewPassword()));
		return userMapper.updateByPrimaryKeySelective(user)>0;
	}

	@Override
	public Page<UserEntity> queryUserListByPage(PageRequest<UserRequestEntity> pageuser) {
		pageuser.initPage();
		Page<UserEntity> page =new Page<>();
		page.setPageData(pageuser);
		page.setList(userMapper.queryUserListByPageParams(pageuser, pageuser.getData()));
		page.setTotal(userMapper.countUserListByParams(pageuser.getData()));
		return page;
	}

}
