package com.sky.auth.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.sky.pub.ResultCode;
import com.sky.pub.ResultUtil;

@Component
public class DefAuthenticationFailHandler extends SimpleUrlAuthenticationFailureHandler {

    private Log logger = LogFactory.getLog(getClass());

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        	logger.info("登录失败");
            //设置状态码
            response.setStatus(200);
            response.setContentType("application/json;charset=UTF-8");
            //将 登录失败 信息打包成json格式返回
            response.getWriter().write(JSON.toJSONString(ResultUtil.getFailed(ResultCode.AUTH_FAILED,exception.getMessage())));
    }
}

