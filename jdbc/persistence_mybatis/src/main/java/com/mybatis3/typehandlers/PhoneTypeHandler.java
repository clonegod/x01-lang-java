package com.mybatis3.typehandlers;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import com.mybatis3.domain.PhoneNumber;

@MappedTypes(PhoneNumber.class)
public class PhoneTypeHandler extends BaseTypeHandler<PhoneNumber> {
	
	public PhoneTypeHandler() {}

	@Override
	public PhoneNumber getNullableResult(ResultSet rs, String columnName) throws SQLException {
		return new PhoneNumber(rs.getString(columnName));
	}
	
	@Override
	public PhoneNumber getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		return new PhoneNumber(rs.getString(columnIndex));
	}

	@Override
	public PhoneNumber getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		return new PhoneNumber(cs.getString(columnIndex));
	}

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, 
			PhoneNumber phoneNumber, JdbcType jdbcType) throws SQLException {
		ps.setString(i, phoneNumber.getAsString());
	}

}
