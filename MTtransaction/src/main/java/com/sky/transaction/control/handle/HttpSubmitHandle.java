package com.sky.transaction.control.handle;

import java.io.IOException;

import org.springframework.http.HttpMethod;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sky.transaction.bean.ConnectTransationNodeData;
import com.sky.transaction.util.HttpClientUtil;

import okhttp3.Response;

/**
 *	http 事务平台提交handle 
 * @author 王帆
 * @date  2019年9月26日 上午10:04:29
 */
public class HttpSubmitHandle implements ConnectSubmitHandle{
	protected String platfromUrl;
	private String submitUrl="/project/transaction/submit";
	protected Long timeOut;
	
	
	public HttpSubmitHandle(String platfromUrl, Long timeOut) {
		this.platfromUrl=platfromUrl;
		this.timeOut=timeOut;
	}
	
	/**
	 * 执行post http 请求
	 * @param url
	 * @param req
	 * @return
	 * @author 王帆
	 * @date 2019年9月26日 下午2:46:23
	 */
	private String doPostHttp(String url,Object req) {
		try {
			Response res = HttpClientUtil.getResponse(timeOut, platfromUrl+url, HttpMethod.PUT, JSON.parseObject(JSON.toJSONString(req)));
			return res.body().string();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	public String excutePostHttp(String url,Object req) {
		JSONObject result = JSON.parseObject(doPostHttp(url,req),JSONObject.class);
		if(!result.getBoolean("success")) {
			throw new RuntimeException(String.format("事务全局提交异常：{code:%s,message:%s}",result.getString("code"), result.getString("message"))) ;
		}
		return result.getString("data");
	}
	public String getPostResponse(String url,Object req) {
		JSONObject result = JSON.parseObject(doPostHttp(url,req),JSONObject.class);
		return result.getString("data");
	}

	@Override
	public ConnectTransationNodeData submitTransactionNodeEvent(ConnectTransationNodeData req) {
		excutePostHttp(submitUrl,req);
		return req; 
	}

}
