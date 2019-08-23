package com.sky.table.bean;

import com.sky.table.contant.DriverEnmu;

/**
 * 	数据源
 * @author 王帆
 * @date  2019年7月19日 上午9:22:25
 */
public class DataSource {
	private String url;
	private String userName;
	private String password;
	private DriverEnmu driver;
	
	public DataSource() {}
	public DataSource(String url,String userName,String password,String driver) {
		this(url,userName,password,DriverEnmu.valueOf(driver));
	}
	public DataSource(String url,String userName,String password,DriverEnmu driver) {
		this.url=url;
		this.userName=userName;
		this.password=password;
		this.driver=driver;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public DriverEnmu getDriver() {
		return driver;
	}
	public void setDriver(DriverEnmu driver) {
		this.driver = driver;
	}
	
}
