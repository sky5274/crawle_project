package com.sky.transaction.datasource.factory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.util.CollectionUtils;
import com.sky.transaction.datasource.bean.ConnectSourceBean;
import com.sky.transaction.datasource.bean.RedisConnectSourceBean;
import com.sky.transaction.datasource.bean.SqlConnectSourceBean;
import com.sky.transaction.datasource.event.ConnectSourceEvent;

/**
 * 链接资源工厂
 * @author 王帆
 * @date  2019年9月20日 上午11:00:51
 */
public class ConnectSourceFactory implements ConnectSourceEvent{
	
	
	/**
	 *	通过工厂统一设置数据源链接对象  
	 * @param source
	 * @return
	 * @author 王帆
	 * @throws SQLException 
	 * @date 2019年9月20日 上午10:45:31
	 */
	public static ConnectSourceBean<?> getConnectSource(Object source) throws SQLException {
		if(source instanceof Connection) {
			Connection conn = (Connection)source;
			return new SqlConnectSourceBean(conn);
		}else if(source instanceof RedisConnection) {
			return new RedisConnectSourceBean((RedisConnection)source);
		}
		return null;
	}

	private List<ConnectSourceBean<?>> connlist;
	public ConnectSourceFactory(ConnectSourceBean<?> ...conns) {
		connlist=new LinkedList<ConnectSourceBean<?>>(Arrays.asList(conns));
	}
	public ConnectSourceFactory(Collection<ConnectSourceBean<?>> conns) {
		connlist=new LinkedList<>(conns);
	}
	
	public void commit() throws Exception {
		if(!CollectionUtils.isEmpty(connlist)) {
			for(ConnectSourceBean<?> conn:connlist) {
				if(conn!=null) {
					conn.commit();
					conn.setStatus(1);
				}
			}
		}
	}

	public void rollBack() throws Exception {
		if(!CollectionUtils.isEmpty(connlist)) {
			for(ConnectSourceBean<?> conn:connlist) {
				if(conn!=null) {
					conn.rollBack();
					conn.setStatus(-1);
				}
			}
		}
	}
}
