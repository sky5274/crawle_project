package com.sky.cm.utils;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.http.HttpMethod;

import com.alibaba.fastjson.JSON;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.FormBody.Builder;

public class HttpUtil {
	public static OkHttpClient.Builder createOkHttpClientBuilder(long readTimeout){
		OkHttpClient.Builder builder = new OkHttpClient.Builder()
				.followRedirects(false)
				.followSslRedirects(false)
				.connectTimeout(readTimeout, TimeUnit.MILLISECONDS)
				.readTimeout(readTimeout, TimeUnit.MILLISECONDS);
		return builder;
	}
	
	public static Response getResponse(long readTimeout,String url,HttpMethod method,Map<String, Object> params) throws IOException {
		Request request = createRequestBuilder(url, method, params).build();
		return createOkHttpClientBuilder(readTimeout).build().newCall(request).execute();
	}
	
	public static Request.Builder createRequestBuilder(String url,HttpMethod method,Map<String, Object> params){
		Request.Builder builder = new Request.Builder()
				.url(url);
		if(method==null) {
			method=HttpMethod.GET;
		}
		switch (method) {
		case POST:
			Builder r_builder = new FormBody.Builder();
			for(String key:params.keySet()) {
				r_builder.add(key, params.get(key).toString());
			}
			builder.post(r_builder.build());
			break;
		case PUT:
			RequestBody body = RequestBody.create( MediaType.parse("application/json; charset=utf-8"), JSON.toJSONString(params));
			builder.post(body);
			break;
		default:
			boolean flag=!url.contains("?");
			for(String key:params.keySet()) {
				if(flag) {
					flag=false;
					url+="?";
				}else {
					url+="&";
				}
				url+=key+"="+params.get(key).toString();
			}
			builder.url(url);
			break;
		}
		return builder;
	}
}
