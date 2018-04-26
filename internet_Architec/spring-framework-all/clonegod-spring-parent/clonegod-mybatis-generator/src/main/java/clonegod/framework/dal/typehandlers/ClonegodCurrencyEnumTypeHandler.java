package clonegod.framework.dal.typehandlers;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import clonegod.framework.dal.enums.CurrencyEnum;

/**
 * You can override the type handlers or create your own to deal with unsupported or non-standard types. 
 * To do so, implement the interface org.apache.ibatis.type.TypeHandler 
 * or extend the convenience class org.apache.ibatis.type.BaseTypeHandler and optionally map it to a JDBC type.
 * 
 */
@SuppressWarnings("all")
public class ClonegodCurrencyEnumTypeHandler<E extends Enum<E>> extends BaseTypeHandler<E> {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
		if (jdbcType == null) {
			if (parameter instanceof CurrencyEnum) {
				CurrencyEnum ce = (CurrencyEnum) parameter;
				ps.setString(i, ce.getAlias());
			} else {
				ps.setString(i, parameter.name());
			}
		} else {
			ps.setObject(i, parameter.name(), jdbcType.TYPE_CODE); // see r3589
		}
	}

	@Override
	public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
		String s = rs.getString(columnName);
		return (E) CurrencyEnum.ofType(s);
	}

	@Override
	public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		String s = rs.getString(columnIndex);
		return (E) CurrencyEnum.ofType(s);
	}

	@Override
	public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		String s = cs.getString(columnIndex);
		return (E) CurrencyEnum.ofType(s);
	}

}
