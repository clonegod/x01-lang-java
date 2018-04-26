package clonegod.mybatis.v2.result;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.reflection.factory.DefaultObjectFactory;

import clonegod.mybatis.v2.config.CGConfiguration;

public class CGResultSetHandler {

	final CGConfiguration configuration;

	public CGResultSetHandler(CGConfiguration configuration) {
		this.configuration = configuration;
	}

	/**
	 * 将ResultSet填充到Bean中
	 * 
	 * @param resultSet
	 * @param type
	 * @return
	 */
	public <T> T handle(ResultSet rs, Class<T> type) throws Exception {
		T resultObj = new DefaultObjectFactory().create(type);
		try {
			if (rs.next()) {
				for (Field field : type.getDeclaredFields()) {
					setValue(resultObj, field, rs);
				}
			} 
		} finally {
			rs.close();
		}
		return resultObj;
	}

	private void setValue(Object resultObj, Field field, ResultSet rs) throws NoSuchMethodException, SQLException, Exception {
		Method setMethod = null;
		try {
			setMethod = resultObj.getClass().getDeclaredMethod(
									"set"+upperCapital(field.getName()), 
									field.getType());
		} catch (NoSuchMethodException e) {
			//e.printStackTrace();
			return;
		}
		setMethod.invoke(resultObj, getResultByFieldName(field, rs));
	}
	
	/**
	 * 根据field的name从resultSet中获取结果
	 * 
	 */
	private Object getResultByFieldName(Field field, ResultSet rs) throws SQLException {
		//TODO type handles
        Class<?> type = field.getType();
        if(Integer.class == type){
            return rs.getInt(field.getName());
        }
        if(String.class == type){
            return rs.getString(field.getName());
        }
        return rs.getString(field.getName());
	}

	private String upperCapital(String name) {
        String first = name.substring(0, 1);
        String tail = name.substring(1);
        return first.toUpperCase() + tail;
    }

}
