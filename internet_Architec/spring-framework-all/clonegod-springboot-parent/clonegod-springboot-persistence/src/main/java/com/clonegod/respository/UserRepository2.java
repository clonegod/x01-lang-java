package com.clonegod.respository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;

import javax.sql.DataSource;
import javax.swing.plaf.basic.DefaultMenuLayout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.clonegod.api.User;

@Repository("userRepository2")
public class UserRepository2 {

	private final DataSource dataSource; // 多数据源的情况下，默认注入@Primary数据源
	
	private final DataSource masterDatasource;

	private final DataSource slaveDatasource;
	
	private final JdbcTemplate jdbcTemplate;
	
	private final PlatformTransactionManager platformTransactionManager;

	@Autowired
	public UserRepository2(
			DataSource dataSource,
			@Qualifier("masterDataSource") DataSource masterDatasource,
			@Qualifier("slaveDataSource") DataSource slaveDatasource,
			JdbcTemplate jdbcTemplate,
			PlatformTransactionManager platformTransactionManager) {
		this.dataSource = dataSource;
		this.masterDatasource = masterDatasource;
		this.slaveDatasource = slaveDatasource;
		this.jdbcTemplate = jdbcTemplate;
		this.platformTransactionManager = platformTransactionManager;
	}

	public Boolean save(User user) {
		if(user.getName().endsWith("0")) {
			return saveByJdbc(user);
		}
		
		if(user.getName().endsWith("1")) {
			return saveByJdbcTemplate(user);
		}
		
		return saveByTransactionManager(user);
	}
	
	/**
	 * JDBC 手动控制事务的提交与回滚
	 * 
	 */
	private Boolean saveByJdbc(User user) {
		Boolean success = false;
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			
			connection.setAutoCommit(false); // 关闭事务自动提交
			
			PreparedStatement ps = connection.prepareStatement("INSERT INTO users (name) VALUES (?)");
			ps.setString(1, user.getName());
			success = ps.executeUpdate() > 0; // sql已经提交执行
			connection.commit(); // 提交事务 
			ps.close();
		} catch(Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			JdbcUtils.closeConnection(connection);
		}
		
		return success;
	}
	
	/**
	 * 使用 @Transactional 声明式事务 - spring AOP 代理实现事务控制
	 * 
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	private Boolean saveByJdbcTemplate(User user) {
		boolean success = false;
		
		success = jdbcTemplate.execute("INSERT INTO users (name) VALUES (?)", new PreparedStatementCallback<Boolean>() {
			@Override
			public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				ps.setString(1, user.getName());
				return ps.executeUpdate() > 0;
			}
		});
		
		return success;
	}
	
	/**
	 * 通过TransactionManager 控制事务的提交与回滚
	 * 
	 */
	private Boolean saveByTransactionManager(User user) {
		boolean success = false;
		
		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
		TransactionStatus transactionStatus = platformTransactionManager.getTransaction(transactionDefinition);
		
		try {
			success = jdbcTemplate.execute("INSERT INTO users (name) VALUES (?)", new PreparedStatementCallback<Boolean>() {
				@Override
				public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
					ps.setString(1, user.getName());
					return ps.executeUpdate() > 0;
				}
			});
			platformTransactionManager.commit(transactionStatus);
		} catch (Exception e) {
			e.printStackTrace();
			platformTransactionManager.rollback(transactionStatus);
		}
		
		return success;
		
	}
	
	
	// ValuesView - 对返回的集合不支持add/addAll操作
	public Collection<User> findAll() {
		return jdbcTemplate.query("SELECT * FROM users", new RowMapper<User>() {
			@Override
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				return User.builder().id(rs.getLong(1)).name(rs.getString(2)).build();
			}
		});
	}

	public User findOne(Long id) {
		return jdbcTemplate.queryForObject("SELECT * FROM users WHERE id = ?", new RowMapper<User>() {
			@Override
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				return User.builder().id(rs.getLong(1)).name(rs.getString(2)).build();
			}
		}, id);
	}

}
