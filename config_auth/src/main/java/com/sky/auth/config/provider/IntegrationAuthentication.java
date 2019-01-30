package com.sky.auth.config.provider;

import java.util.Map;

/**
 * 集成认证实体
 * @author 王帆
 * @date  2019年1月26日 下午3:14:58
 */
public class IntegrationAuthentication {
    private String authType;//请求登录认证类型
    private Map<String,String[]> authParameters;//请求登录认证参数集合

    public String getAuthParameter(String paramter){
        String[] values = this.authParameters.get(paramter);
        if(values != null && values.length > 0){
            return values[0];
        }
        return null;
    }

	public String getAuthType() {
		return authType;
	}

	public void setAuthType(String authType) {
		this.authType = authType;
	}

	public Map<String, String[]> getAuthParameters() {
		return authParameters;
	}

	public void setAuthParameters(Map<String, String[]> authParameters) {
		this.authParameters = authParameters;
	}
    
}