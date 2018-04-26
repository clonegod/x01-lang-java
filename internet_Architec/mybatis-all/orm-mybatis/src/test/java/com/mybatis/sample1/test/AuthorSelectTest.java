package com.mybatis.sample1.test;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mybatis.sample1.model.Author;
import com.mybatis.sample1.util.Sample1ConnectionFactory;

public class AuthorSelectTest {
	
	static SqlSessionFactory sessionFactory;
	
	@BeforeClass
	public static void setUp() {
		sessionFactory = Sample1ConnectionFactory.getSessionFactory();
	}

	@Test
	public void testSelectJoin() {
		SqlSession session = null;
		try {
			session = sessionFactory.openSession(false); // 关闭自动提交
			
			List<Author> authors = session.selectList("selectAuthorByJoin"); // 执行sql
			authors.stream().forEach(System.out::println);
			session.commit(); // 提交事务
		} catch(Exception ex) {
			session.rollback(); // 回滚事务
			ex.printStackTrace();
		} finally {
			session.close(); // 关闭session
		}
	}
	
	@Test
	public void testSelectBySubSelect() {
		SqlSession session = null;
		try {
			session = sessionFactory.openSession(false); // 关闭自动提交
			
			List<Author> authors = session.selectList("authorSubSelect"); // 执行sql
			for(Author au : authors) {
				System.out.println(au.getRealName()+"\t"+au.getIdcard());
				System.out.println("开始执行懒加载");
				System.out.println(au.getUser().getUsername()); // 真正使用到关联表数据时，才发出sql查询关联表
				System.out.println("====================");
			}
			session.commit(); // 提交事务
		} catch(Exception ex) {
			session.rollback(); // 回滚事务
			ex.printStackTrace();
		} finally {
			session.close(); // 关闭session
		}
	}
}
