package com.sky.auth.config;


import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import com.sky.auth.config.provider.IntegrationAuthenticationFilter;
import com.sky.auth.config.service.impl.UserDetailsServiceImpl;


@Configuration
@AutoConfigureAfter(CommonAuthConfig.class)
@EnableAuthorizationServer
@Order(Ordered.LOWEST_PRECEDENCE)
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
	@Value("${own.config.client.id:dev}")
	private String clientId;
	@Value("${own.config.client.secret:dev}")
	private String clientSecret;
	@Value("${own.config.app.secret:app}")
	private String appSecret;
	@Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private UserDetailsServiceImpl userDetailsService;
    @Resource
    private IntegrationAuthenticationFilter integrationAuthenticationFilter;
    @Resource
    private RedisTokenStore tokeStore;
    @Resource
    private DefaultTokenServices defaultTokenServices;
    
    /**
     * 	 客户端配置（给谁发令牌）
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients 
        		.inMemory().withClient(clientId)
                .secret(clientSecret)
                //有效时间 2小时
                .accessTokenValiditySeconds(2*60*60)
                //密码授权模式和刷新令牌
                .authorizedGrantTypes("authorization_code","password", "refresh_token","client_credentials") // 该client允许的授权类型
                .scopes( "all","read","writer")// 允许的授权范围  
                .autoApprove(true)
                .and()
                .inMemory().withClient("app")
                .secret(appSecret)
                //有效时间 2小时
                .accessTokenValiditySeconds(2*60*60)
                //密码授权模式和刷新令牌
                .authorizedGrantTypes("password", "refresh_token","client_credentials") // 该client允许的授权类型
                .scopes( "all","app","read","writer")// 允许的授权范围 
                ; 
        		
    }
   
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
        		.tokenStore(tokeStore)
        		.tokenServices(defaultTokenServices)
                .userDetailsService(userDetailsService)
                .authenticationManager(authenticationManager);
    }

    /**
     * <p>注意，自定义TokenServices的时候，需要设置@Primary，否则报错，</p>
     * @return
     */
    

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()");
        security .checkTokenAccess("isAuthenticated()");
        security.allowFormAuthenticationForClients();
        security.addTokenEndpointAuthenticationFilter(integrationAuthenticationFilter);
    }
}
