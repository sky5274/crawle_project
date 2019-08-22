package com.sky.auth.config.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.sky.auth.config.entity.UserDetailEntitiy;
import com.sky.pub.BaseTableEntity;

public class UserUtil {
	private static String DEF_USER_NAME="USER_INFO";
	
	/**
	 * 全局保存用户信息
	 * @param userinfo
	 * @author 王帆
	 * @date 2019年4月8日 下午3:47:43
	 */
	public static void saveUser(UserDetailEntitiy userinfo) {
		HttpServletRequest req = getHttpRequest();
		req.getSession().setAttribute(DEF_USER_NAME, userinfo);
	}
	
	public static UserDetailEntitiy getUser() {
		HttpServletRequest req = getHttpRequest();
		return (UserDetailEntitiy)req.getSession().getAttribute(DEF_USER_NAME);
	}
	
	public static HttpServletRequest getHttpRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}
	
	/**
	 * 设置数据的创建id与创建人名称
	 * @param data
	 * @return
	 * @author 王帆
	 * @date 2019年4月9日 上午10:19:34
	 */
	public static <T extends BaseTableEntity> T setUserInfo(T data) {
		UserDetailEntitiy user = getUser();
		if(user!=null) {
			data.setCreateid(user.getId());
			data.setCreateName(user.getUsername());
		}
		return data;
	}
}
