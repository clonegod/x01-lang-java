package com.clonegod.jdbc.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.clonegod.jdbc.model.Order;
@Repository
public class OrderDao {
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	private static final String INSERT_ORDER = "INSERT INTO t_order(express_type, user_id, add_time) VALUES (?,?,?)";
	
	public int[] batchInsert(final List<Order> orders) {
		int result[] =
			jdbcTemplate.batchUpdate(INSERT_ORDER, new BatchPreparedStatementSetter(){
	
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					Order o = orders.get(i);
					ps.setShort(1, o.getExpressType());
					ps.setInt(2, o.getUserId());
					ps.setInt(3, Integer.valueOf(new SimpleDateFormat("MMddHHmmss").format(new Date()).substring(4)));
				}
	
				@Override
				public int getBatchSize() {
					return orders.size();
				}
				
			});
		return result;
	}
	
	
}
