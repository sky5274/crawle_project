package com.sky.data.contant;

import com.sky.pub.contant.PubFileContant;

public class BatchDataContant {
	private static String file_prefix="/batch_data/";
	
	public static String getFilePrefix() {
		return PubFileContant.getJarPath()+file_prefix;
	}
}
