package com.clonegod.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.clonegod.jdbc.model.User;

@Repository
public class UserDao {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	/**
	 * 插入User，并返回记录的id
	 * 
	 * @param user
	 * @return
	 */
	public int insertUser(User user) {
        final String sql = "insert into t_user (name, age, create_time) values(?,?,?)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();
        
        jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
	               PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	               ps.setString(1, user.getName());
	               ps.setInt(2, user.getAge());
	               ps.setDate(3, new java.sql.Date(user.getCreateTime().getTime()));
				return ps;
			}
        }, keyHolder);
        
        return keyHolder.getKey().intValue();
		
	}
	
	/**
	 * 根据用户名查询User
	 * 
	 * @param userName
	 * @return
	 */
	public User findByUserName(String userName) {
		String sql = "SELECT * FROM t_user WHERE name = ?";
		RowMapper<User> rowMapper = new BeanPropertyRowMapper<>(User.class);
		User user = jdbcTemplate.queryForObject(sql, new Object[] {userName}, rowMapper);
		return user;
	}
	
}
