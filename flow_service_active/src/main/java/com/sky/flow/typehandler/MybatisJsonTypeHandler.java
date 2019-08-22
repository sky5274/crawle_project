package com.sky.flow.typehandler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mysql.cj.jdbc.CallableStatement;

@MappedTypes(JSONObject.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
public class MybatisJsonTypeHandler<T extends Object> extends BaseTypeHandler<T> {
 
    private Class<T> clazz;
 
    public MybatisJsonTypeHandler(Class<T> clazz) {
        if (clazz == null) throw new IllegalArgumentException("Type argument cannot be null");
        this.clazz = clazz;
    }
 
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, this.toJson(parameter));
    }
 
    @Override
    public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return this.toObject(rs.getString(columnName), clazz);
    }
 
    @Override
    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return this.toObject(rs.getString(columnIndex), clazz);
    }
 
    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return this.toObject(cs.getString(columnIndex), clazz);
    }
 
    private String toJson(T object) {
        try {
            return JSON.toJSONString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
 
    @SuppressWarnings("unchecked")
	private T toObject(String content, Class<?> clazz) {
        if (content != null && !content.isEmpty()) {
            try {
                return (T) JSON.parseObject(content, clazz);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            return null;
        }
    }

	@Override
	public T getNullableResult(java.sql.CallableStatement cs, int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
 
}
