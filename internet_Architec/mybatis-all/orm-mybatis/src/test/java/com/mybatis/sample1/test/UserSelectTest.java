package com.mybatis.sample1.test;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mybatis.sample1.model.Gender;
import com.mybatis.sample1.model.User;
import com.mybatis.sample1.util.Sample1ConnectionFactory;

/**
 * 说明：
 * 下面的测试为了简单起见，直接通过sql的id字符串来引用执行，没有在接口中声明对应方法。
 * 如果要使用接口的方法，则在接口中声明与id字符串相同的方法名即可。注意方法的返回值类型要与对应的sql执行结果一致。
 * 
 * @author Administrator
 *
 */
public class UserSelectTest {
	
	static SqlSessionFactory sessionFactory;
	
	@BeforeClass
	public static void setUp() {
		sessionFactory = Sample1ConnectionFactory.getSessionFactory();
	}
	
	/**
	 * 查询单条记录。使用selectOne
	 * ParameterType -> POJO
	 * ResultType -> HashMap
	 */
	@Test
	public void testSelectUser_ParameterType_POJO_ResultType_HashMap() {
		SqlSession session = null;
		try {
			session = sessionFactory.openSession();
			User user = new User("alice", "123456");
			HashMap<String, String> result = session.selectOne("selectUser_paramType_POJO", user); // selectOne返回单一结果
			System.out.println(result);
			session.commit();
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			session.close();
		}
	}
	
	/**
	 * 查询单条记录。使用selectOne
	 * ParameterType -> HashMap
	 * ResultType -> POJO
	 */
	@Test
	public void testSelectUser_ParameterType_HashMap_ResultType_POJO() {
		SqlSession session = null;
		try {
			session = sessionFactory.openSession();
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("username", "alice");
			map.put("password", "123456");
			User user = session.selectOne("selectUser_paramType_HashMap", map); // selectOne返回单一结果
			System.out.println(user);
			session.commit();
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			session.close();
		}
	}
	
	/**
	 * 查询多条记录。使用selectList
	 * 	使用ResultType来处理结果映射. 对字段名不一致的属性，将无法注入值。
	 */	
	@Test
	public void testSelectUserList_ResultType() {
		SqlSession session = null;
		try {
			session = sessionFactory.openSession();
			List<User> users = session.selectList("selectUserList_resultType"); // selectList返回集合
			users.stream().forEach(System.out::println);
			session.commit();
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			session.close();
		}
	}

	/**
	 * 查询多条记录。使用selectList
	 * 	使用ResultMap来处理结果映射
	 */	
	@Test
	public void testSelectUserList_ResultMap() {
		SqlSession session = null;
		try {
			session = sessionFactory.openSession();
			List<User> users = session.selectList("selectUserList_resultMap"); // selectList返回集合
			users.stream().forEach(System.out::println);
			session.commit();
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			session.close();
		}
	}
	
	/**
	 * 集合查询
	 */
	@Test
	public void testSelectUserVisitCollection() {
		SqlSession session = null;
		try {
			session = sessionFactory.openSession();
			List<User> users = session.selectList("selectUserVisitCollection"); // selectList返回集合
			users.stream().forEach(System.out::println);
			session.commit();
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			session.close();
		}
	}
	
	/**
	 * 枚举类型
	 */
	@Test
	public void testSelectUserGenderEnum() {
		SqlSession session = null;
		try {
			session = sessionFactory.openSession();
			
			User user = new User("bob", "bob123");
			user.setGender(Gender.男);
			session.insert("insertUserGenderEnum", user);
			
			List<User> userList = session.selectList("getUserGenderEnum");
			userList.stream().forEach(System.out::println);
			
			session.commit();
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			session.close();
		}
	}
	
	
}
