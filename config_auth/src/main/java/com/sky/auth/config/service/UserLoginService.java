package com.sky.auth.config.service;

import org.springframework.stereotype.Service;

import com.sky.auth.config.entity.UserDetailEntitiy;
import com.sky.auth.config.entity.UserEntity;
import com.sky.pub.common.exception.ResultException;


@Service
public interface UserLoginService {
	
	/**
	 * 用户注册注册
	 * @param user
	 * @return
	 * @author 王帆
	 * @date 2019年1月26日 下午1:29:31
	 */
	public UserEntity regist(UserEntity user)throws ResultException;
	
	/**
	 * 登录
	 * @param user
	 * @return
	 * @throws ResultException
	 * @author 王帆
	 * @date 2019年4月9日 上午10:07:46
	 */
	public UserDetailEntitiy login(UserEntity user) throws ResultException;
}
