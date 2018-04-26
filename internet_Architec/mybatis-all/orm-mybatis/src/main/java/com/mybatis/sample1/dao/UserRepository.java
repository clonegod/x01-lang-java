package com.mybatis.sample1.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.mybatis.sample1.mappers.UserMapper;
import com.mybatis.sample1.model.User;
import com.mybatis.sample1.util.Sample1ConnectionFactory;

/*
 * This would usually be an interface, and implementation would be injected via DI
 * to those objects that need access to database.
 * It is done this way for simplicity.
 */
public class UserRepository {
	/*
	 * We need only one SqlSessionFactory per application.
	 */
	private SqlSessionFactory sessionFactory;
	
	public UserRepository() {
		sessionFactory = Sample1ConnectionFactory.getSessionFactory();	
	}
	
	public User getById(int id) {
		System.out.println("Getting by id: " + id);
		SqlSession session = sessionFactory.openSession(); 
		try { 
		  UserMapper mapper = session.getMapper(UserMapper.class);
		  User user = mapper.getUserById(id);
		  return user;
		} finally { 
		  session.close();
		}
	}
	
	public void insert(User user) {
		SqlSession session = sessionFactory.openSession(); 
		try { 
		  UserMapper mapper = session.getMapper(UserMapper.class);
		  mapper.insertUser(user);
		  session.commit();
		} finally { 
		  session.close();
		}
	}
	
	/**
	 * 直接通过mapper中sql的id关联需要执行的sql
	 * @param user
	 */
	public void updateBySqlId(User user) {
		SqlSession session = sessionFactory.openSession(); 
		try { 
			session.update("updateUser", user);
			session.commit();
		} finally { 
			session.close();
		}
	}
	
	public void updateByMapperInterface(User user) {
		SqlSession session = sessionFactory.openSession(); 
		try { 
			UserMapper mapper = session.getMapper(UserMapper.class);
			mapper.updateUser(user); // 通过Mapper接口来绑定sql。接口的方法名需要与xml中sql节点的id相同。这种方式的好处是：避免了书写sql的id字符串。
			session.commit();
		} finally { 
			session.close();
		}
	}
	
	public void deleteByAnnotation(int userId) {
		SqlSession session = sessionFactory.openSession(); 
		try { 
			UserMapper mapper = session.getMapper(UserMapper.class);
			mapper.deleteUser(userId); // 通过在接口方法上使用注解来绑定sql
			session.commit();
		} finally { 
			session.close();
		}
	}
}
