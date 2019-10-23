package com.sky.transaction.util;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.springframework.http.HttpMethod;
import com.alibaba.fastjson.JSON;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.FormBody.Builder;

public class HttpClientUtil {

	public static class ResponseCall implements Callback{

		@Override
		public void onFailure(Call call, IOException e) {

		}

		@Override
		public void onResponse(Call call, Response response) throws IOException {

		}

	}


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
	public static void AsyncResponse(long readTimeout,String url,HttpMethod method,Map<String, Object> params,ResponseCall call) throws IOException {
		Request request = createRequestBuilder(url, method, params).build();
		if(call==null) {
			call=new ResponseCall();
		}
		createOkHttpClientBuilder(readTimeout).build().newCall(request).enqueue(call);
	}

	protected static Request.Builder createRequestBuilder(String url,HttpMethod method,Map<String, Object> params){
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
		builder.addHeader("Content-Type", "application/json");
		return builder;
	}
}
