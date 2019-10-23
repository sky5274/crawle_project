package com.sky.transaction.datasource.bean;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * sql  connect source
 * @author 王帆
 * @date  2019年9月20日 上午10:43:05
 */
public class SqlConnectSourceBean extends ConnectSourceBean<Connection>{
	private boolean isTransaction=false;
	
	public SqlConnectSourceBean(Connection conn) {
		super(conn);
		init();
	}

	public void commit() throws Exception {
		isTransaction=false;
		if(getConnect() !=null && !getConnect().isClosed()) {
			getConnect().commit();
			getConnect().setAutoCommit(true);
			getConnect().close();
		}

	}

	public void rollBack() throws Exception {
		isTransaction=false;
		if(getConnect() !=null && !getConnect().isClosed()) {
			getConnect().rollback();
			getConnect().setAutoCommit(true);
			getConnect().close();
		}
	}

	@Override
	public void init() {
		Connection conn = getConnect();
		try {
			setTransaction(true);
			if(conn!=null && !conn.isClosed()) {
				conn.setAutoCommit(false);
				conn.setReadOnly(false);
				conn.setTransactionIsolation(4);	//TRANSACTION_REPEATABLE_READ
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean isTransaction() {
		return isTransaction;
	}

	public void setTransaction(boolean isTransaction) {
		this.isTransaction = isTransaction;
	}
	
	

}
