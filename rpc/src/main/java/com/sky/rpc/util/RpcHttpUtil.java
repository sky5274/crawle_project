package com.sky.rpc.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.alibaba.fastjson.JSON;

public class RpcHttpUtil {

	public static String doPost(String requestUrl,Object param) {
		// 定义 BufferedReader输入流来读取URL的响应
		BufferedReader in = null;
		// 得到请求的输出流对象
		DataOutputStream out=null;
		try {
			URL url = new URL(requestUrl);
			// 打开和URL之间的连接
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			// 设置通用的请求属性
			connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
			connection.setRequestProperty("X-Requested-With", "XMLHttpRequest");
			connection.setRequestProperty("Connection", "Keep-Alive");
			connection.setUseCaches(false);
			connection.setDoOutput(true);
			connection.setDoInput(true);
			System.err.println(JSON.toJSONString(param));
			out = new DataOutputStream(connection.getOutputStream());
			out.write(JSON.toJSONString(param).getBytes("UTF-8"));
			out.flush();
			out.close();
			// 建立实际的连接
			connection.connect();
			in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
			StringBuilder result = new StringBuilder();
			String getLine;
			while ((getLine = in.readLine()) != null) {
				result.append(getLine);
			}

			return result.toString();
		} catch (IOException e) {
		}finally {
			if(in !=null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
			if(out !=null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}
		return null;
	}
}
