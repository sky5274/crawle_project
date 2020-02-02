package com.sky.pub.util;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.UUID;
import org.springframework.util.StringUtils;

public class ComPubUtil {
	private static String ip;
	
	public static String getUuid() {
		return UUID.randomUUID().toString().replaceAll("-","");
	}
	
	/**
	 * 获取本地ip
	 * @return
	 * @author wangfan
	 * @date 2020年1月29日 下午8:08:06
	 */
	public static String getIp() {
		if(StringUtils.isEmpty(ip)) {
			// 获取ip地址
			try {
				Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
				InetAddress ip = null;
				while (allNetInterfaces.hasMoreElements()) {
					NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
					if (netInterface.isLoopback() || netInterface.isVirtual() || !netInterface.isUp()) {
						continue;
					} else {
						Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
						while (addresses.hasMoreElements()) {
							ip = addresses.nextElement();
							if (ip != null && ip instanceof Inet4Address) {
								return ip.getHostAddress();
							}
						}
					}
				}
			} catch (Exception e) {
			}
		}
		return ip;
	}
}
