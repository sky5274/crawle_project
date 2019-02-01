package com.sky.auth.config.starger;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import com.sky.auth.config.dao.UserMapper;
import com.sky.auth.config.entity.UserDetailEntitiy;
import com.sky.auth.config.entity.UserEntity;
import com.sky.auth.config.provider.AbstractPreparableIntegrationAuthenticator;
import com.sky.auth.config.provider.IntegrationAuthentication;

/**
 * 用户名与密码验证
 * @author 王帆
 * @date  2019年1月26日 下午3:43:54
 */
@Component
@Primary
public class UsernamePasswordAuthenticator extends AbstractPreparableIntegrationAuthenticator {
	private Log log=LogFactory.getLog(getClass());
    @Autowired
    private UserMapper mapper;
    @Resource
	private BCryptPasswordEncoder passwordEncoder ;

    @Override
    public UserEntity authenticate(IntegrationAuthentication entity) {
        String name = entity.getAuthParameter("username");
        String pwd = entity.getAuthParameter("password");
        log.info("user name-password Authenticat:"+name+":"+pwd);
        if(name == null || pwd == null){
            throw new OAuth2Exception("用户名或密码不能为空");
        }
        UserDetailEntitiy user = mapper.queryDetailByPhoneOrMailOrCode(name);
       if(user==null) {
    	   throw new OAuth2Exception("用户名不正确");
       }else if(!passwordEncoder.matches(pwd, user.getPassword())) {
    	   throw new OAuth2Exception("用户密码不正确");
       }
        return user; 
    }

    @Override
    public boolean support(IntegrationAuthentication entity) {
        return StringUtils.isEmpty(entity.getAuthType());
    }
}
