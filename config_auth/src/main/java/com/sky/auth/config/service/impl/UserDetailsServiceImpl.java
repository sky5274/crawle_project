package com.sky.auth.config.service.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.sky.auth.config.dao.UserMapper;
import com.sky.auth.config.entity.AuthorityEntity;
import com.sky.auth.config.entity.UserDetailEntitiy;
import com.sky.auth.config.entity.UserEntity;
import com.sky.auth.config.provider.IntegrationAuthentication;
import com.sky.auth.config.provider.IntegrationAuthenticationContext;
import com.sky.auth.config.provider.IntegrationAuthenticator;
import com.sky.auth.config.service.UserService;
import com.sky.pub.ResultCode;
import com.sky.pub.common.exception.ResultException;

@Service
public class UserDetailsServiceImpl implements UserDetailsService,UserService{
	private Log log=LogFactory.getLog(getClass());
	@Resource
	private UserMapper userMapper;
	@Resource
	private BCryptPasswordEncoder passwordEncoder ;

    private List<IntegrationAuthenticator> authenticators;

    @Autowired(required = false)
    public void setIntegrationAuthenticators(List<IntegrationAuthenticator> authenticators) {
        this.authenticators = authenticators;
    }

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("auth username:"+username);
		IntegrationAuthentication entity = IntegrationAuthenticationContext.get();
        if (entity == null){
            entity = new IntegrationAuthentication();
        }
        UserEntity user = this.authenticate(entity);
        if (user == null){
        	return userMapper.queryDetailByPhoneOrMailOrCode(username);
        }
        UserDetailEntitiy userd = userMapper.queryById(user.getId());
        Set<AuthorityEntity> auths = userd.getAuthorities();
        if(auths==null) {
        	auths=new HashSet<>();
        }
        auths.addAll((Collection<? extends AuthorityEntity>) AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER"));
        userd.setAuthorities(auths);
		return userd;
	}
	
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
	public UserEntity regist(UserEntity user) {
		user.setCode("user_com_"+ new Random(10000).nextInt());
		user.setPassword(passwordEncoder.encode(user.getPassword()).trim());
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
		if(passwordEncoder.matches(user.getPassword(), userd.getPassword())) {
			userd.setPassword(passwordEncoder.encode(user.getPassword()));
			return userd;
		}
		return null; 
	}

}
