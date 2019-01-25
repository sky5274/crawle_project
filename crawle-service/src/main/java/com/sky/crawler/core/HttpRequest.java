package com.sky.crawler.core;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import cn.edu.hfut.dmic.webcollector.conf.CommonConfigured;
import okhttp3.FormBody;
import okhttp3.FormBody.Builder;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class HttpRequest extends CommonConfigured implements JsonRequester{

	protected OkHttpClient client;
	protected HashSet<Integer> successCodeSet;
	protected HashSet<Integer> createSuccessCodeSet(){
		HashSet<Integer> result = new HashSet<Integer>();
		result.add(200);
		result.add(301);
		result.add(302);
		result.add(404);
		return result;
	}


	public OkHttpClient.Builder createOkHttpClientBuilder(){
		OkHttpClient.Builder builder = new OkHttpClient.Builder()
				.followRedirects(false)
				.followSslRedirects(false)
				.connectTimeout(getConf().getConnectTimeout(), TimeUnit.MILLISECONDS)
				.readTimeout(getConf().getReadTimeout(), TimeUnit.MILLISECONDS);
		return builder;

	}

	public Request.Builder createRequestBuilder(CrawlerUrlDatum crawlDatum){
		Request.Builder builder = new Request.Builder()
				.header("User-Agent",getConf().getDefaultUserAgent())
				.url(crawlDatum.url());
		if(!crawlDatum.getParam().isEmpty()) {
			switch (crawlDatum.getMethod()) {
			case POST:
				Map<String, Object> param = crawlDatum.getParam();
				Builder r_builder = new FormBody.Builder();
				for(String key:param.keySet()) {
					r_builder.add(key, param.get(key).toString());
				}
				builder.post(r_builder.build());
				break;
			case POST_ROW:
				RequestBody body = RequestBody.create( MediaType.parse("application/json; charset=utf-8"), JSON.toJSONString(crawlDatum.getParam()));
				builder.post(body);
				break;

			default:
				Map<String, Object> param1 = crawlDatum.getParam();
				String url=crawlDatum.url();
				boolean flag=!url.contains("?");
				for(String key:param1.keySet()) {
					if(flag) {
						flag=true;
						url+="?";
					}else {
						url+="&";
					}
					url+=key+"="+param1.get(key).toString();
				}
				builder.url(url);
				break;
			}
		}
		String defaultCookie = getConf().getDefaultCookie();
		if(defaultCookie != null){
			builder.header("Cookie", defaultCookie);
		}
		return builder;
	}

	public HttpRequest() {
		successCodeSet = createSuccessCodeSet();
		client = createOkHttpClientBuilder().build();
	}


	@Override
	public JSONObject getResponse(String url) throws Exception {
		return getResponse(new CrawlerUrlDatum(url));
	}

	@Override
	public JSONObject getResponse(CrawlerUrlDatum datum) throws Exception {
		Request  request = createRequestBuilder(datum).build();
		Response response = client.newCall(request).execute();

		ResponseBody responseBody = response.body();
		try {
			int code = response.code();
			//设置重定向地址
			datum.code(code);
			datum.location(response.header("Location"));

			if (!successCodeSet.contains(code)) {
				//	            throw new IOException(String.format("Server returned HTTP response code: %d for URL: %s (CrawlDatum: %s)", code,crawlDatum.url(), crawlDatum.key()));
				//	            throw new IOException(String.format("Server returned HTTP response code: %d for %s", code, crawlDatum.briefInfo()));
				throw new IOException(String.format("Server returned HTTP response code: %d", code));

			}
			if (responseBody != null) {
				return JSON.parseObject(responseBody.string());
			}
			return null;
		}finally {
			if(responseBody != null){
				responseBody.close();
			}
		}
	}

}
