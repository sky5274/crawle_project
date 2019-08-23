package com.sky.table.contant;

public enum DriverEnmu {
	mysql("com.mysql.jdbc.Driver"),orcal("oracle.jdbc.OracleDriver");
	String driver;
	DriverEnmu(String driver){
		this.driver=driver;
	}
	public String driver() {
		return driver;
	}
}
