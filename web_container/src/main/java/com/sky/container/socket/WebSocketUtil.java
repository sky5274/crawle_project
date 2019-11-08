package com.sky.container.socket;

import java.util.HashMap;
import java.util.Map;
import javax.websocket.Session;
import org.springframework.util.StringUtils;

public class WebSocketUtil {

	/**
	 * 查询socket链接url参数
	 * @param session
	 * @return
	 * @author 王帆
	 * @date 2019年10月26日 下午5:21:38
	 */
	public static Map<String, String> getQueryMap(Session session){
		Map<String, String> queryMap=new HashMap<String, String>();
		String queryString = session.getQueryString();
		if(!StringUtils.isEmpty(queryString)) {
			String[] queryParams = queryString.split("&");
			if(queryParams.length>0) {
				for(String params:queryParams) {
					if(!StringUtils.isEmpty(params)) {
						String[] paramEntity = params.split("=");
						if(paramEntity.length==2) {
							queryMap.put(paramEntity[0], paramEntity[1]);
						}
					}
				}
			}
		}
		return queryMap;
	}
}
