package com.mybatis.sample1.test;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mybatis.sample1.model.Author;
import com.mybatis.sample1.model.User;
import com.mybatis.sample1.util.Sample1ConnectionFactory;

public class AuthorTest {
	static SqlSessionFactory sessionFactory;
	
	@BeforeClass
	public static void setUp() {
		sessionFactory = Sample1ConnectionFactory.getSessionFactory();
	}
	
	@Test
	public void testInsertAuthor() {
		SqlSession session = null;
		try {
			session = sessionFactory.openSession(false); // 关闭自动提交
			
			User user = new User("alice", "123456");
			session.insert("insertUser", user);
			System.out.println("新插入的记录，userid="+user.getId());
			
			Author author = new Author("张三", "123456777789");
			author.setUser(user);
			
			session.insert("insertAuthor", author); // 执行sql
			
			session.commit(); // 提交事务
		} catch(Exception ex) {
			session.rollback(); // 回滚事务
			ex.printStackTrace();
		} finally {
			session.close(); // 关闭session
		}
	}
	
	
}
