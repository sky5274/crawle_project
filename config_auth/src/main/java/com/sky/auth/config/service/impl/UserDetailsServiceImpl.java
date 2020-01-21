package com.sky.auth.config.service.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import com.sky.auth.config.dao.UserMapper;
import com.sky.auth.config.entity.AuthorityEntity;
import com.sky.auth.config.entity.UserDetailEntitiy;
import com.sky.auth.config.entity.UserEntity;
import com.sky.auth.config.provider.IntegrationAuthentication;
import com.sky.auth.config.provider.IntegrationAuthenticationContext;
import com.sky.auth.config.provider.IntegrationAuthenticator;
import com.sky.auth.config.service.UserLoginService;
import com.sky.pub.ResultCode;
import com.sky.pub.common.exception.ResultException;

@Service
@Transactional(rollbackFor=Exception.class)
public class UserDetailsServiceImpl implements UserDetailsService, UserLoginService{
	private Log log=LogFactory.getLog(getClass());

	private static Random random= new Random(10000);
	@Resource
	private UserMapper userMapper;
	@Resource
	private BCryptPasswordEncoder passwordEncoder ;
	
	private List<IntegrationAuthenticator> authenticators;

	@Autowired
	public void setIntegrationAuthenticators(List<IntegrationAuthenticator> authenticators) {
		this.authenticators = authenticators;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("auth username:"+username);
		IntegrationAuthentication entity = IntegrationAuthenticationContext.get();
		UserEntity user=null;
		if (entity != null){
			user= this.authenticate(entity);
		}
		if (user == null){
			user= userMapper.queryDetailByPhoneOrMailOrCode(username);
		}
		UserDetailEntitiy userd = userMapper.queryById(user.getId());
		
		//初始化用户权限
		initUserAuthoruty(userd);
		return userd;
	}
	
	/**
	 * 初始化用户权限
	 * @param userd
	 * @author 王帆
	 * @date 2019年4月8日 下午1:17:33
	 */
	private void initUserAuthoruty(UserDetailEntitiy userd) {
		Set<GrantedAuthority> auths = new HashSet<>();
		List<String> roles = userd.getRoles();
		if(roles==null || roles.isEmpty()) {
			roles=new LinkedList<>();
		}
		roles.addAll(Arrays.asList("ANONYMOUS","USER","ADMIN"));
		for(String role:roles) {
			auths.add(new SimpleGrantedAuthority("ROLE_"+role));
		}
		for(AuthorityEntity per:userd.getPermissions()) {
			auths.add(new SimpleGrantedAuthority(per.getAuthcode()));
		}
		userd.setAuthorities(auths);
	}

	/**
	 * 判断用户授权末世
	 * @param entity
	 * @return
	 * @author 王帆
	 * @date 2019年4月8日 下午1:24:21
	 */
	private UserEntity authenticate(IntegrationAuthentication entity) {
		if (this.authenticators != null) {
			for (IntegrationAuthenticator authenticator : authenticators) {
				if (authenticator.support(entity)) {
					return authenticator.authenticate(entity);
				}
			}
		}
		return null;
	}



	@Override
	public UserEntity regist(UserEntity user) throws ResultException {
		user.setCode("user_com_"+ random.nextInt());
		user.setPassword(passwordEncoder.encode(user.getPassword()).trim());
		List<UserEntity> users = userMapper.queryUserByPhoneOrMailOrCode(user);
		if(users !=null && !users.isEmpty()) {
			throw new ResultException(ResultCode.VALID,"用户名："+user.getUsername()+"已注册");
		}
		int size=userMapper.insertSelective(user);
		return size>0?user:null;
	}

	
	
	@Override
	public UserDetailEntitiy login(UserEntity user) throws ResultException {
		if(StringUtils.isEmpty(user.getUsername())) {
			throw new ResultException(ResultCode.PARAM_VALID,"缺少登陆名");
		}
		if(StringUtils.isEmpty(user.getPassword())) {
			throw new ResultException(ResultCode.PARAM_VALID,"缺少登陆密码");
		}
		UserDetailEntitiy userd = userMapper.queryDetailByPhoneOrMailOrCode(user.getUsername());
		if(userd==null) {
			throw new ResultException(ResultCode.PARAM_VALID,"未找到用户："+user.getUsername());
		}

		UserDetailEntitiy userInfo = userMapper.queryById(userd.getId());
		if(passwordEncoder.matches(user.getPassword(), userd.getPassword())) {
			userInfo.setPassword(passwordEncoder.encode(user.getPassword()));
		}
		initUserAuthoruty(userd);
		return userInfo; 
	}

}
