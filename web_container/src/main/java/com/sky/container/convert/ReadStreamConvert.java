package com.sky.container.convert;

import java.io.InputStream;
import java.util.List;

/**
 * 	读流转换对象
 * @author 王帆
 * @date  2019年10月28日 下午5:20:35
 */
public interface ReadStreamConvert<T> {
	
	/**
	 * 	将流数据转换成对象
	 * @param in
	 * @return
	 * @author 王帆
	 * @date 2019年10月28日 下午5:22:08
	 */
	public List<T> convert(InputStream in);
}
