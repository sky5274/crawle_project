package com.sky.pub.contant;

import java.io.InputStream;

public class PubFileContant {
	public static String urlRegex=" /^((ht|f)tps?):\\/\\/([\\w\\-]+(\\.[\\w\\-]+)*\\/)*[\\w\\-]+(\\.[\\w\\-]+)*\\/?(\\?([\\w\\-\\.,@?^=%&:\\/~\\+#]*)+)?/";
	
	/**获取资源文件的实际地址*/
	public static String getFilePath(String path) {
		return PubFileContant.class.getResource(path).getFile();
	}
	public static String getJarPath() {
		String path = PubFileContant.class.getResource("/").getFile();
		if(path.contains("!/")) {
			int startIndex=0;
			if(path.startsWith("file:/")) {
				startIndex=6;
			}
			int lastIndex = path.substring(0, path.indexOf("!/")).lastIndexOf("/");
			path=path.substring(startIndex,lastIndex);
		}
		return path;
	}
	/**获取资源文件的数据流*/
	public static InputStream getFilePathStream(String path) {
		return PubFileContant.class.getResourceAsStream(path);
	}
}
