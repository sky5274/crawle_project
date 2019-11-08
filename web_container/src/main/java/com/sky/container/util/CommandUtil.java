package com.sky.container.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.sky.container.socket.stream.StreamReadEvent;

/**
 * java cmd runtime 工具类
 * @author 王帆
 * @date  2019年10月24日 下午5:21:42
 */
public class CommandUtil {
	private static String charset="UTF-8";
	private static Log log=LogFactory.getLog(CommandUtil.class);
	
	public static Process run(String[] cmds) throws IOException {
		log.debug("runtime run cmd: "+JSON.toJSONString(cmds));
		return Runtime.getRuntime().exec(cmds); 
	}
	public static Process run(String cmds,String[] args) throws IOException {
		log.debug(String.format("runtime run cmd: %s %s", cmds,args));
		return Runtime.getRuntime().exec(cmds,args); 
	}
	public static String exec(String cmds,String[] args) {
		try {  
			Process p = run(cmds,args);  
			return readProcessMsg(p);
		} catch (IOException e) {  
			return e.getMessage();  
		}  
	}
	public static String exec(String[] cmds) {
		try {  
			Process p = run(cmds);  
			return readProcessMsg(p);
		} catch (IOException e) {  
			return e.getMessage();
		} 
	}
	public static String exec(String cmds) {
		return exec(cmds,null);
	}
	
	/**
	 * 	命令读流操作
	 * @param cmds
	 * @param out
	 * @throws IOException
	 * @author 王帆
	 * @date 2019年10月29日 上午9:06:57
	 */
	public static void exe(String cmds,OutputStream out) throws IOException {
		try {  
			Process p = run(cmds,null);  
			InputStream in = p.getInputStream();
			readStream(in, out);
		} catch (IOException e) {  
			e.printStackTrace();
			out.write(e.getMessage().getBytes());
		}finally {
			out.flush();
			out.close();
		} 
	}
	
	/**
	 * 读取cmd命令进程返回结果
	 * @param process
	 * @return
	 * @throws IOException
	 * @author 王帆
	 * @date 2019年10月24日 下午5:34:09
	 */
	public static String readProcessMsg(Process pro) throws IOException {
		InputStream err = pro.getErrorStream();  
		InputStream in = pro.getInputStream();  
		String charsetStr=charset;
		try {
			if(System.getProperty("os.name").toLowerCase().startsWith("win")) {
				charsetStr="GBK";
			}
		} catch (Exception e) {
		}
		
		String str = readStream(in, charsetStr);  
		String errStr = readStream(err, charsetStr);  
		if(!StringUtils.isEmpty(errStr)){  
			str=str+errStr;  
		}  
		return str;
	}

	/**
	 * 读流操作
	 * @param in
	 * @param charset
	 * @return
	 * @throws IOException
	 * @author 王帆
	 * @date 2019年10月24日 下午5:33:34
	 */
	public static String readStream(InputStream in, String charset) throws IOException {  
		StringBuffer str=new StringBuffer();
		InputStreamReader insr = new InputStreamReader(in,charset);  
		BufferedReader buffer = new BufferedReader(insr);  
		try {  
			String s=null;
			while ((s=buffer.readLine())!= null){  
				str.append(s).append("\r\n");
			}  
		} catch (IOException e) {  
			throw e;  
		} finally {  
			buffer.close();  
			insr.close();  
		}  
		return str.toString();  
	} 
	public static void readStream(InputStream in, OutputStream out) throws IOException {  
		InputStreamReader insr = new InputStreamReader(in);  
		BufferedReader buffer = new BufferedReader(insr);  
		try {  
			String s=null;
			while ((s=buffer.readLine())!= null){  
				out.write(s.getBytes());
				out.write("\n".getBytes());
				out.flush();
			}
		} catch (IOException e) {  
			throw e;  
		} finally {
			out.close();
			buffer.close();  
			insr.close();  
		}  
	} 
	public static void readStream(InputStream in, StreamReadEvent readEvent) throws IOException {  
		InputStreamReader insr = new InputStreamReader(in);  
		BufferedReader buffer = new BufferedReader(insr);  
		try {  
			String s=null;
			while ((s=buffer.readLine())!= null){  
				readEvent.invoke(s);
			}
		} catch (IOException e) {  
			throw e;  
		} finally {
			buffer.close();  
			insr.close();  
		}  
	} 
}
