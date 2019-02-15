package com.sky.pub.util;

import java.util.UUID;

public class ComPubUtil {
	
	public static String getUuid() {
		return UUID.randomUUID().toString().replaceAll("-","");
	}
}
