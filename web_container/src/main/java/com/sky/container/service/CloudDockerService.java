package com.sky.container.service;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import com.sky.container.convert.ReadStreamConvert;

/**
 * 	云docker命令
 * @author 王帆
 * @date  2019年10月28日 下午5:13:02
 */
public interface CloudDockerService {
	 
	public String exec(String cmd ,String ...args);
	public String exec(String cmd );
	
	/**
	 * 	获取docker File path
	 * @return
	 * @author 王帆
	 * @date 2019年10月29日 上午10:16:36
	 */
	public String getDockerFilePath();
	
	/**
	 *  网络地址对应的文件绝对地址
	 * @param path
	 * @return
	 * @author 王帆
	 * @date 2019年10月29日 上午10:23:31
	 */
	public String getAbsolutePath(String path);
	
	
	/**
	 * 	将docker命令命令操作输入流，转入目标输出流中
	 * @param cmd
	 * @param out
	 * @author 王帆
	 * @date 2019年10月28日 下午5:24:59
	 */
	public void exec(String cmd ,OutputStream out);
	
	/**
	 * 	将docker命令的流数据转换成对象
	 * @param cmd
	 * @param convert
	 * @return
	 * @author 王帆
	 * @date 2019年10月28日 下午5:23:56
	 */
	public <T> List<T> exec(String cmd ,ReadStreamConvert<T> convert);
	
	public Map<String, String> readJarManifest(String path);
}
