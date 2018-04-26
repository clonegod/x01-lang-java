package com.mybatis.sample1.test;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.mybatis.sample1.model.User;
import com.mybatis.sample1.util.Sample1ConnectionFactory;


// 设置测试类中所有方法的执行顺序：按方法名称的ASCII先后顺序依次执行
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SqlInjectTest {
	
	static SqlSessionFactory sessionFactory;
	
	@BeforeClass
	public static void setUp() {
		sessionFactory = Sample1ConnectionFactory.getSessionFactory();
	}
	
	/**
	 * mybatisc存在sql注入的情况：
	 * 	如果使用${}来引用变量值，则可能会发生sql注入。因为sql不是预编译的，而是运行时与参数一起拼接而成的sql，所以使用${}在一些情况下会发生sql注入。
	 * 
	 * 解决办法：
	 * 	使用#{}来引用变量，预编译sql不会存在sql注入问题。
	 */
	
	@Test
	public void testSqlInjection1() {
		SqlSession session = null;
		try {
			session = sessionFactory.openSession();
			
			User user = new User();
			user.setUsername("");
			user.setPassword("' or 1=1 -- "); // 注入
			List<User> userList = session.selectList("selectSqlInject", user);
			userList.stream().forEach(System.out::println);
			
			session.commit();
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			session.close();
		}
	}
	
	@Test
	public void testSqlInjection2() {
		SqlSession session = null;
		try {
			session = sessionFactory.openSession();
			
			User user = new User();
			user.setUsername("' or 1=1 -- ");
			List<User> userList = session.selectList("selectSqlInject_LIKE", user);
			userList.stream().forEach(System.out::println);
			
			session.commit();
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			session.close();
		}
	}
	
}
