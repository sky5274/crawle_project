package com.crawl.data.config.dao.entity;


import java.util.List;

import com.sky.pub.BasePageRequest;


/**
 * 爬虫配置
 * @author 王帆
 * @date  2019年1月17日 上午9:32:47
 */
public class CrawlerConfigEntity extends BasePageRequest{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private String code;
	/**type:html>>爬去html;json>>爬去json*/
	private String type;
    private String name;
    /**爬虫配置*/
    private String crawlPath;
    
    /**迭代次数*/
    private int depth=5;
    private int topN=10;
    private int threads=10;
    private String remark;
    /**javascript 文件路径*/
    private String js;
    /**类名*/
    private String className;
    /**javascript  文件内容*/
    private String jsContent;
    
    private List<CrawlerConfigUrlEntity> urls;
    private List<CrawlerConfigFilterEntity> filters ;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getJs() {
        return js;
    }

    public void setJs(String js) {
        this.js = js == null ? null : js.trim();
    }

	public String getJsContent() {
		return jsContent;
	}

	public void setJsContent(String jsContent) {
		this.jsContent = jsContent;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<CrawlerConfigUrlEntity> getUrls() {
		return urls;
	}

	public void setUrls(List<CrawlerConfigUrlEntity> urls) {
		this.urls = urls;
	}

	public List<CrawlerConfigFilterEntity> getFilters() {
		return filters;
	}

	public void setFilters(List<CrawlerConfigFilterEntity> filters) {
		this.filters = filters;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getCrawlPath() {
		return crawlPath==null || "".equals(crawlPath)? "/crawler/"+code:crawlPath;
	}

	public void setCrawlPath(String crawlPath) {
		this.crawlPath = crawlPath;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public int getTopN() {
		return topN;
	}

	public void setTopN(int topN) {
		this.topN = topN;
	}

	public int getThreads() {
		return threads;
	}

	public void setThreads(int threads) {
		this.threads = threads;
	}

}