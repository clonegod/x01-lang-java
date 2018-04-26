package com.mybatis.sample1.test;

import java.util.Arrays;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mybatis.sample1.model.Gender;
import com.mybatis.sample1.model.User;
import com.mybatis.sample1.util.Sample1ConnectionFactory;

public class DynamicSqlTest {
	
	static SqlSessionFactory sessionFactory;
	
	@BeforeClass
	public static void setUp() {
		sessionFactory = Sample1ConnectionFactory.getSessionFactory();
	}
	
	/**
	 * 动态sql-if
	 */
	@Test
	public void testSelectUserIf() {
		SqlSession session = null;
		try {
			session = sessionFactory.openSession();
			
			User user = new User();
			user.setFirstName("alice");
			List<User> userList = session.selectList("selectUserIf", user);
			userList.stream().forEach(System.out::println);
			
			session.commit();
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			session.close();
		}
	}
	
	/**
	 * 动态sql-choose
	 */
	@Test
	public void testSelectUserChoose() {
		SqlSession session = null;
		try {
			session = sessionFactory.openSession();
			
			User user = new User();
			user.setFirstName("alice");
			user.setGender(Gender.女);
			List<User> userList = session.selectList("selectUserChoose", user);
			userList.stream().forEach(System.out::println);
			
			session.commit();
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			session.close();
		}
	}
	
	/**
	 * 动态sql-where
	 */
	@Test
	public void testSelectByWhere() {
		SqlSession session = null;
		try {
			session = sessionFactory.openSession();
			
			User user = new User();
			user.setFirstName("alice");
			user.setGender(Gender.女);
			List<User> userList = session.selectList("selectUserWhere", user);
			userList.stream().forEach(System.out::println);
			
			session.commit();
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			session.close();
		}
	}
	
	/**
	 * 动态sql-set
	 */
	@Test
	public void testUpdateBySet() {
		SqlSession session = null;
		try {
			session = sessionFactory.openSession();
			
			User user = new User();
			user.setFirstName("Bill");
			user.setLastName("Clinton");
			user.setGender(Gender.男);
			List<User> userList = session.selectList("updateUserBySet", user);
			userList.stream().forEach(System.out::println);
			
			session.commit();
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			session.close();
		}
	}
	
	
	/**
	 * 动态sql-foreach
	 */
	@Test
	public void testSelectUserByForeach() {
		SqlSession session = null;
		try {
			session = sessionFactory.openSession();
			
			List<Integer> idList = Arrays.asList(1, 2, 3, 4, 5);
			List<User> userList = session.selectList("selectUserByForeach", idList);
			userList.stream().forEach(System.out::println);
			
			session.commit();
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			session.close();
		}
	}
	
	
	
}
