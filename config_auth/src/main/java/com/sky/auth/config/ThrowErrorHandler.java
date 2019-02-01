package com.sky.auth.config;

import java.io.IOException;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

public class ThrowErrorHandler implements ResponseErrorHandler {
	 
    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        // 返回false表示不管response的status是多少都返回没有错
        // 这里可以自己定义那些status code你认为是可以抛Error
        return false;
    }
 
    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        // 这里面可以实现你自己遇到了Error进行合理的处理
    }
 
}