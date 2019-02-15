package com.sky.crawl.util;

import java.io.IOException;
import java.io.PrintWriter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.sky.crawl.data.config.dao.ConfigLogFileMapper;
import com.sky.crawl.data.config.dao.entity.ConfigFileDefined;

/**
 * 	爬虫前端控制
 * @author 王帆
 * @date  2019年1月18日 下午2:41:05
 */
public class CrawlerHttpEventWriter extends PrintWriter{
	private Log log=LogFactory.getLog(getClass());
	private ConfigFileDefined fileDefined;
	private ConfigLogFileMapper configLogFileMapper;

	public CrawlerHttpEventWriter(ConfigFileDefined fileDefined) throws IOException{
		super(fileDefined.initPathFile());
		this.fileDefined=fileDefined;
	}
	public CrawlerHttpEventWriter(ConfigFileDefined fileDefined,ConfigLogFileMapper configLogFileMapper) throws IOException{
		super(fileDefined.initPathFile());
		this.fileDefined=fileDefined;
		this.configLogFileMapper=configLogFileMapper;
	}


	@Override
	public void write(String s, int off, int len) {
		super.write(s, off, len);
		super.flush();
		log.debug(s);
		if(this.fileDefined.getStatus()!=2 && configLogFileMapper !=null) {
			//进行中
			fileDefined.setStatus(2);
			configLogFileMapper.updateByPrimaryKey(fileDefined);
		}
	}

	@Override
	public void close() {
		super.close();
		//结束
		fileDefined.setStatus(4);
		configLogFileMapper.updateByPrimaryKey(fileDefined);
	}

	public ConfigFileDefined getFileDefined() {
		return fileDefined;
	}
	public void setFileDefined(ConfigFileDefined fileDefined) {
		this.fileDefined = fileDefined;
	}

	public ConfigLogFileMapper getConfigLogFileMapper() {
		return configLogFileMapper;
	}

	public void setConfigLogFileMapper(ConfigLogFileMapper configLogFileMapper) {
		this.configLogFileMapper = configLogFileMapper;
	}
}
