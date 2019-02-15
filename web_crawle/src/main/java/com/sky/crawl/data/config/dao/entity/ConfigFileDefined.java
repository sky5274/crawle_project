package com.sky.crawl.data.config.dao.entity;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import com.sky.pub.BaseTableEntity;

/**
 * 配置：文件读取参数
 * @author 王帆
 * @date  2019年1月18日 下午5:19:09
 */
public class ConfigFileDefined extends BaseTableEntity {
	public static Map<Integer, String> statusMap=new HashMap<>();
	static {
		statusMap.put(0, "新建");
		statusMap.put(1, "开始");
		statusMap.put(2, "进行中");
		statusMap.put(3, "暂停");
		statusMap.put(4, "终止");
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String code;
	private String path;
	/**0:新建，1：开始，2：进行中，3：暂停，4：终止*/
	private int status;
	private String statusName;
	private int length;
	private int index;
	private String context;
	public ConfigFileDefined() {}
	public ConfigFileDefined(CrawlerConfigEntity config) {
		if(config.getCode()==null) {
			code=UUID.randomUUID().toString().replace("-","");
		}else {
			code=config.getCode();
		}
		this.path=getClass().getResource("/").getFile()+"/log/"+new SimpleDateFormat("yyyy/MM").format(new Date())+"/"+this.code+".txt";
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
		this.index=this.index+context.length();
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getStatusName() {
		return statusMap.get(status);
	}
	
	public File initPathFile() throws IOException {
		File file=new File(path);
		if(!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		file.createNewFile();
		return file;
	}
}
