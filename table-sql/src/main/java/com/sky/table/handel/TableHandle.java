package com.sky.table.handel;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.mysql.cj.util.StringUtils;
import com.sky.table.bean.DataSource;
import com.sky.table.bean.ParamBean;
import com.sky.table.util.SpringExpressionUtil;

/**
 * 	表处理
 * @author 王帆
 * @date  2019年7月19日 上午9:39:51
 */
public class TableHandle {
	private static String functionRegix="#(\\{[\\S]+\\})";
	Log log=LogFactory.getLog(getClass());
	public static Pattern functionPattern = Pattern.compile(functionRegix);
	
	public Connection getDataConnect(DataSource datasource) {
		if(datasource.getDriver()!=null) {
			try {
				Class.forName(datasource.getDriver().driver());
				return DriverManager.getConnection(datasource.getUrl(), datasource.getUserName(), datasource.getPassword());
			} catch (ClassNotFoundException |SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	private int createTable(Connection conn, String table, Collection<ParamBean> set) {
		if(set==null || set.isEmpty()) {
			return 0;
		}
		String sql=getCreateTableSql(table,set);
		//执行新增表
		return excuteUpate(conn,sql,null);
	}
	private int addTableColumns(Connection conn, String table, Collection<ParamBean> set) {
		if(set==null || set.isEmpty()) {
			return 0;
		}
		String sql=getAddColumSql(table, set);
		//执行新增列
		return excuteUpate(conn,sql,null);
	}
	
	
	public String getCreateTableSql(String table, Collection<ParamBean> set) {
		StringBuilder str=new StringBuilder();
		str.append("CREATE TABLE").append("`").append(table).append("`(");
		boolean hasId=false;
		for(ParamBean param:set) {
			if("id".equals(param.getValue())) {
				hasId=true;
			}
			str.append("`").append(param.getLable()).append("` ").append(getColumnType(param.getType())).append(" ").append("DEFAULT NULL,");
		}
		if(!hasId) {
			str.append("`id` int(11) NOT NULL AUTO_INCREMENT,");
		}
		str.append("PRIMARY KEY (`id`)");
		str.append(") ENGINE=InnoDB DEFAULT CHARSET=utf8;");
		return str.toString();
	}
	public String getAddColumSql(String table, Collection<ParamBean> set) {
		StringBuilder str=new StringBuilder();
		if(set!=null && !set.isEmpty()) {
			for(ParamBean s:set) {
				str.append("alter table "+table+" add column ").append(s.getLable()).append(" "+getColumnType(s.getType())).append(";");
			}
		}
		return str.toString();
	}

	private void closed(Connection conn) {
		if(conn!=null) {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public int createTable(DataSource source, String table, Set<ParamBean> set) {
		Connection conn = getDataConnect(source);
		int size = createTable(conn, table, set);
		return size;
	}
	
	private String getColumnType(String paramType) {
		String type="varchar(255)";
		switch (paramType) {
		case "boolean":
			type="byte(2)";
		case "int":
		case "long":
			type="int(11)";
			break;
		case "float":
		case "double":
			type="decimal(50,2)";
			break;
		case "date":
			type="timestamp";
			break;

		default:
			break;
		}
		return type;
	}
	
	public int alertColumn() {
		return 0;
	}
	
	protected Map<String, Object> getData(List<String> colunmNames, ResultSet resultset) throws SQLException{
		Map<String, Object> result=new HashMap<>();
		for(String column:colunmNames) {
			result.put(column, resultset.getString(column));
		}
		return result;
	}
	protected <T> T getData(List<String> colunmNames, ResultSet resultset,Class<T> clazz) throws SQLException{
		T result;
		try {
			result = clazz.newInstance();
			Method[] methods = clazz.getDeclaredMethods();
			for(String column:colunmNames) {
				for(Method m:methods) {
					if(m.getName().toUpperCase().equals("SET"+column.toUpperCase())) {
						m.invoke(result, resultset.getObject(column));
					}
				}
			}
			return result;
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private void addParams(PreparedStatement pstm, List<ParamBean> params) throws SQLException {
		if(params!=null && !params.isEmpty()) {
			log.info("ooo>>  excute param: "+StringUtils.joinWithSerialComma(params));
			for(int i=0;i<params.size();i++) {
				ParamBean param = params.get(i);
				if(param!=null) {
					if(param.getType()==null) {
						pstm.setString(i+1, param.getValue().toString());
					}else {
						switch (param.getType()) {
						case "int":
							pstm.setInt(i+1, (Integer)param.getValue());
							break;
						case "boolean":
							pstm.setBoolean(i+1, (Boolean)param.getValue());
							break;
						case "float":
							pstm.setFloat(i+1, (Float)param.getValue());
							break;
						case "double":
							pstm.setDouble(i+1, (Double)param.getValue());
							break;
						case "date":
							pstm.setDate(i+1, param.getValue()==null?null:new Date(((java.util.Date)param.getValue()).getTime()));
							break;

						default:
							pstm.setString(i+1, param.getValue()==null?null:param.getValue().toString());
							break;
						}
					}
				}
				
			}
		}
	}
	
	protected <T> T getExcuteResult(DataSource datasource,String sql,List<ParamBean> params,SqlExcuteHandel<?> handel) throws SQLException {
		Connection conn = getDataConnect(datasource);
		if(conn!=null) {
			try {
				return getExcuteResult(conn,sql,params,handel);
			} catch (SQLException  e) {
				throw e;
			}finally {
				closed(conn);
			}
		}
		return null;
	}
	
	protected int excuteUpate(Connection conn, String sql,List<ParamBean> params) {
		Statement stat=null;
		try {
			log.info("ooo>> excute sql: "+sql);
			int size=0;
			if(sql.contains("?")) {
				PreparedStatement pstm = conn.prepareStatement(sql);
				addParams(pstm,params);
				size=pstm.executeUpdate();
				stat=pstm;
			}else {
				stat= conn.createStatement();
				stat.executeUpdate(sql);
				size=stat.getUpdateCount();
			}
			
			log.info("excute result<<== "+size);
			return size;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(stat!=null) {
					stat.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return 0;
	}
	
	@SuppressWarnings("unchecked")
	protected <T> T getExcuteResult(Connection conn,String sql,List<ParamBean> params,SqlExcuteHandel<?> handel) throws SQLException {
		if(conn!=null) {
			PreparedStatement pstm=null;
			try {
				log.info("ooo>> excute sql: "+sql);
				pstm = conn.prepareStatement(sql);
				//添加参数
				addParams(pstm,params);
				ResultSet resultset = pstm.executeQuery();
				return (T) handel.excuteResult(resultset);
			} catch (SQLException  e) {
				throw e;
			}finally {
				try {
					if(pstm!=null) {
						pstm.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	/**
	 * 获取表的列名
	 * @param datasource
	 * @param table
	 * @return
	 * @author 王帆
	 * @date 2019年8月22日 下午4:08:58
	 */
	public List<String> getTableColumus(DataSource datasource,String table){
		Connection conn = getDataConnect(datasource);
		return getTableColumus(conn,table);
	}
	public List<String> getTableColumus(Connection conn,String table){
		if(conn!=null) {
			Statement pstm=null;
			try {
				String sql = "select * from "+table;;
				pstm = conn.createStatement();
				ResultSet resultset = pstm.executeQuery(sql);
				ResultSetMetaData tableMeteData = resultset.getMetaData();
				int colunmsize = tableMeteData.getColumnCount();
				List<String> colunmNames=new LinkedList<>();
				for( int i=1;i<=colunmsize;i++) {
					colunmNames.add(tableMeteData.getColumnName(i));
				}
				resultset.close();
				return colunmNames;
			} catch (SQLException e) {
			}finally {
				try {
					if(pstm!=null) {
						pstm.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			}
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	protected <T> List<T> excuteAndGetResult(DataSource datasource,String sql,List<ParamBean> params,Class<T> clazz) throws Exception {
		return getExcuteResult(datasource,sql,params ,(resultset) -> {
					if(resultset!=null) {
						try {
							ResultSetMetaData tableMeteData = resultset.getMetaData();
							int colunmsize = tableMeteData.getColumnCount();
							List<String> colunmNames=new LinkedList<>();
							for( int i=1;i<=colunmsize;i++) {
								colunmNames.add(tableMeteData.getColumnLabel(i));
							}
							List<T> results=new LinkedList<>();
							while(resultset.next()) {
								if(clazz ==null || clazz.newInstance() instanceof Map) {
									results.add((T)getData(colunmNames,resultset));
								}else {
									results.add(getData(colunmNames,resultset,clazz));
								}
							}
							resultset.close();
							return results;
						} catch (SQLException | InstantiationException | IllegalAccessException e) {
							e.printStackTrace();
						}
					}
					return null;
			});
	}
	
	public List<Map<String, Object>> excute(DataSource datasource,String sql) throws Exception {
		return excuteAndGetResult(datasource, sql, null, null);
	}
	public List<Map<String, Object>> excute(DataSource datasource,String sql,Object param) throws Exception {
		return excuteQuery(datasource, preHandel(sql), getParamData(sql,param),null);
	}
	public <T> List<T> excute(DataSource datasource,String sql,Object param,Class<T> clazz) throws Exception {
		return excuteQuery(datasource, preHandel(sql), getParamData(sql,param),clazz);
	}
	
	/**
	 * 	根据sql封装对应的提交数据
	 * @param sql
	 * @param param
	 * @return
	 * @author 王帆
	 * @date 2019年8月22日 下午5:23:18
	 */
	private List<ParamBean> getParamData(String sql,Object param) {
		Matcher matches = functionPattern.matcher(sql);
		List<ParamBean> params=new LinkedList<>();
		if(matches.find()) {
			for(int i=0;i<matches.groupCount();i++) {
				String field=matches.group(i);
				Object val = SpringExpressionUtil.parse(field,param);
				ParamBean p=new ParamBean();
				p.setLable(field);
				p.setValue(val);
				params.add(p);
			}
		}
		return params;
	}

	/**
	 * 	预处理 sql
	 * @param sql
	 * @return
	 * @author 王帆
	 * @date 2019年8月22日 下午5:22:47
	 */
	private String preHandel(String sql) {
		if(sql !=null) {
			return sql.replaceAll(functionRegix, "?");
		}
		return null;
	}

	protected <T> List<T> excuteQuery(DataSource datasource,String sql,List<ParamBean> params,Class<T> clazz) throws Exception {
		return excuteAndGetResult(datasource, sql, params, clazz);
	}
	
	/**
	 * 	向表新增数据；
	 * 1.看表是否存在，不存在建表，存在比较列，补充不存在的列
	 * 2：新增数据
	 * @param source  数据源
	 * @param table  表名
	 * @param datas	新增数据
	 * @return
	 * @author 王帆
	 * @date 2019年7月19日 下午2:28:28
	 */
	public int addData(DataSource source, String table, List<?> datas) {
		ColumDataMap columnData=getDataListTypeMap(datas);
		Connection conn = getDataConnect(source);
		List<String> columns = getTableColumus(conn, table);
		int up_size=0;
		if(columnData.getColumNames()!=null) {
			//无表时添加表结构
			if(columns==null || columns.isEmpty()) {
				//建表
				createTable(conn,table,columnData.getColumNames());
			}else {
				//新增列名
				List<ParamBean> excludeColums = getExcludeColums(columnData.getColumNames(),columns);
				if(excludeColums.isEmpty()) {
					addTableColumns(conn, table, excludeColums);
				}
			}
			
			up_size=insertData(source,table,columnData);
		}
		closed(conn);
		return up_size;
	}
	
	/**
	 * 	执行数据插入
	 * @param source
	 * @param table
	 * @param columnData
	 * @author 王帆
	 * @return 
	 * @date 2019年8月22日 下午4:31:43
	 */
	@SuppressWarnings("rawtypes")
	private int insertData(DataSource source, String table, ColumDataMap columnData) {
		StringBuilder sql=new StringBuilder();
		List<String> columNames=new LinkedList<>();
		List<String> dataPreNames=new LinkedList<>();
		List<ParamBean> dataValues=new LinkedList<>();
		for(ParamBean col:columnData.getColumNames()) {
			columNames.add("`"+col.getLable()+"`");
			dataPreNames.add("?");
		}
		sql.append("insert into ").append(table).append("(").append(String.join(",",columNames)).append(")");
		int index=0;
		for(Map obj:columnData.getColumndatas()) {
			if(obj==null || obj.isEmpty()) {
				continue;
			}
			for(ParamBean col:columnData.getColumNames()) {
				try {
					ParamBean p=col.clone();
					p.setValue(obj.get(col.getLable()));
					dataValues.add(p);
				} catch (CloneNotSupportedException e) {
					dataValues.add(col);
				}
			}
			sql.append(index==0?" values":",").append("(").append(String.join(",",dataPreNames)).append(")");
			index++;
		}
		return excuteUpate(getDataConnect(source),sql.toString(),dataValues);
		
	}

	/**
	 *	 获取数据库中不含有的对应的列属性
	 * @param dataNeedColumns
	 * @param tabcolumns
	 * @return
	 * @author 王帆
	 * @date 2019年8月22日 下午3:28:10
	 */
	protected List<ParamBean> getExcludeColums(Set<ParamBean> dataNeedColumns, List<String> tabcolumns) {
		List<ParamBean> excludeColums=new LinkedList<>();
		if(dataNeedColumns!=null && !dataNeedColumns.isEmpty()) {
			for(ParamBean col:dataNeedColumns) {
				if(!tabcolumns.contains(col.getLable())) {
					excludeColums.add(col);
				}
			}
		}
		return excludeColums;
	}
	
	/**
	 * 获取新增参数的参数类型与所有列
	 * @param datas
	 * @author 王帆
	 * @return 
	 * @date 2019年7月19日 下午3:33:50
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ColumDataMap getDataListTypeMap(List<?> datas) {
		ColumDataMap columnData=new ColumDataMap();
		List<Map> list=new LinkedList<>();
		Set<ParamBean> colums=new HashSet<>();
		for(Object data:datas) {
			Map<String,Object> obj=null;
			if(data instanceof  Map) {
				Map d = (Map)data;
				obj=new HashMap<>();
				Set<String> keys = d.keySet();
				for(String key:keys) {
					obj.put(key.toUpperCase(), d.get(key));
				}
			}else {
				obj=getDataMap(data);
			}
			list.add(obj);
			for(String key:obj.keySet()) {
				ParamBean column=new ParamBean();
				column.setLable(key);
				column.setParamType(obj.get(key));
				colums.add(column);
			}
		}
		columnData.setColumNames(colums);
		columnData.setColumndatas(list);
		return columnData;
	}

	/**
	 *	 将数据转成map
	 * @param data
	 * @return
	 * @author 王帆
	 * @date 2019年8月22日 下午4:21:52
	 */
	private Map<String, Object> getDataMap(Object data) {
		if(data!=null) {
			Class<? extends Object> clazz = data.getClass();
			Method[] method = clazz.getDeclaredMethods();
			Map<String, Object> dataFeildmap=new HashMap<>();
			try {
			for(Method m:method) {
				if(m.getName().startsWith("get")) {
					dataFeildmap.put(m.getName().substring(2, m.getName().length()).toUpperCase(), m.invoke(data));
				}
			}
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
			return dataFeildmap;
		}
		return null;
	}
	
	@SuppressWarnings({"unused","rawtypes"})
	private class ColumDataMap{
		private List<Map> columndatas;
		private Set<ParamBean> columNames;
		public List<Map> getColumndatas() {
			return columndatas;
		}
		public void setColumndatas(List<Map> columndatas) {
			this.columndatas = columndatas;
		}
		public Set<ParamBean> getColumNames() {
			return columNames;
		}
		public void setColumNames(Set<ParamBean> columNames) {
			this.columNames = columNames;
		}
		@Override
		public String toString() {
			return "{datas:"+columndatas+",names:"+columNames+"}";
		}
	}
}
