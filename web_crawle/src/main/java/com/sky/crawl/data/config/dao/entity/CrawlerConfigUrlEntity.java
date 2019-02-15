package com.sky.crawl.data.config.dao.entity;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.alibaba.druid.util.StringUtils;
import com.sky.pub.BaseTableEntity;
import com.sky.pub.ResultCode;
import com.sky.pub.common.exception.ResultException;

/**
 * 爬虫配置种子路径
 * @author 王帆
 * @date  2019年1月17日 上午9:58:33
 */
public class CrawlerConfigUrlEntity extends BaseTableEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String configCode;
	
    private String url;

    private String name;
    
    private String type;

    private String condtion;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getCondtion() {
        return condtion;
    }

    public void setCondtion(String condtion) {
        this.condtion = condtion == null ? null : condtion.trim();
    }

	public String getConfigCode() {
		return configCode;
	}

	public void setConfigCode(String configCode) {
		this.configCode = configCode;
	}
	
	public List<String> bulidUrl() throws ResultException{
		if(!StringUtils.isEmpty(url)) {
			if(url.startsWith("http://") || url.startsWith("https://") || url.startsWith("://") ) {
				return Arrays.asList(url.split(","));
			}else {
				throw new ResultException(ResultCode.PARAM_VALID,url+"格式错误，请以'http' or 'https'开头");
			}
		}else {
			return new LinkedList<>();
		}
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}