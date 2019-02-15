package com.sky.crawl.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.UriUtils;

import com.alibaba.fastjson.JSON;

public class RestTemplateUtil {
	public Logger log=LoggerFactory.getLogger(getClass());
	protected long wait=1000;
	private static List<String> extraAttr=Arrays.asList("serialVersionUID","$jacocoData");
	
	public void waitTime() {
		waitTime(wait);
	}
	public void waitTime(long wait) {
		try {
			Thread.sleep(wait);
		} catch (InterruptedException e) {
		}
	}
	
	/**
	 * 	简单的get请求封装
	 * @param path
	 * @param param
	 * @param responseType
	 * @return
	 * @author 王帆
	 * @date 2018年12月27日 下午4:16:21
	 */
	public <K, T> T testGetMenuNodeBYPageParam(TestRestTemplate restTemplate,String path,K param,Class<T> responseType) {
		try {
			path=parsePathParam(path,param);
			log.info("load test path:{}",path);
			ResponseEntity<T> response = restTemplate.getForEntity(new URI(path),  responseType);
			if(response.getStatusCodeValue()!=200) {
				log.info(JSON.toJSONString(response.getBody()));
			}
			assertEquals("request url: "+path+" fialed and http status: "+response.getStatusCodeValue(),response.getStatusCodeValue(), 200);
			return response.getBody();
		} catch (IllegalArgumentException|IllegalAccessException|RestClientException | URISyntaxException e) {
			log.warn("rest template request url:{} warn messge:{}",path,e.getMessage(),e);
		}
		return null;
	}

	/**
	 * 无参页面测试
	 * @param page
	 * @author 王帆
	 * @date 2018年12月27日 下午1:53:52
	 */
	public void getPagewithoutParam(TestRestTemplate restTemplate,String page) {
		try {
			ResponseEntity<String> respose = restTemplate.getForEntity(new URI(page), String.class);
			assertNotEquals(respose.getStatusCodeValue(), 2000);
			log.info("load page: {}",page);
			log.info("load page headers: {}",respose.getHeaders());
			log.info("page body: {}"+respose.getBody());
		} catch (RestClientException | URISyntaxException e) {
			log.warn("url init warn:"+e.getMessage(),e);
		}
	}

	public MultiValueMap<String, String> parseHeaderMap(Object param) throws IllegalArgumentException, IllegalAccessException {
		assertNotNull("param is null",param);
		MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<String, String>();
		
		Map<String ,Object> paramMAP = JSON.parseObject(JSON.toJSONString(param), Map.class);
		for(String key:paramMAP.keySet()) {
			Object value = paramMAP.get(key);
			if(value!=null) {
				multiValueMap.add(key, value.toString());
			}
		}
		log.debug("param is:{}" ,JSON.toJSON(multiValueMap.toSingleValueMap()));
		return multiValueMap;
	}
	
	public String parsePathParam(String path,Object param) throws IllegalArgumentException, IllegalAccessException {
		assertNotNull("param is null",param);
		if(param instanceof String) {
			return path+"?"+(String)param;
		}
		StringBuilder paramStr=null;
		Map<String ,Object> paramMAP = JSON.parseObject(JSON.toJSONString(param), Map.class);
		for(String key:paramMAP.keySet()) {
			if(extraAttr.contains(key) || key==null) {
				continue;
			}
			Object value=paramMAP.get(key);
			if(!StringUtils.isEmpty(value)) {
				if(StringUtils.isEmpty(paramStr)) {
					paramStr=new StringBuilder("?");
				}else {
					paramStr.append("&");
				}
				try {
					paramStr.append(key+"="+UriUtils.encode(value.toString(),"UTF-8"));
				} catch (UnsupportedEncodingException e) {
				}
			}
		}
		log.debug("param is:{}" ,paramStr);
		return path+(paramStr==null?"":paramStr.toString());
	}

	public String parseObjectPostBody(Object param) {
		return JSON.toJSONString(param);
	}
}
