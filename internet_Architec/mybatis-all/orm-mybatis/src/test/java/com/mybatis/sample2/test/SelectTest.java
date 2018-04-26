package com.mybatis.sample2.test;

import java.util.List;
import java.util.Random;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gson.GsonBuilder;
import com.mybatis.sample2.dao.AuthorMapper;
import com.mybatis.sample2.dao.BlogMapper;
import com.mybatis.sample2.model.Author;
import com.mybatis.sample2.model.Blog;
import com.mybatis.sample2.util.Sample2ConnectionFactory;

public class SelectTest {

	static Random r;
	static SqlSessionFactory sessionFactory;
	static int SIZE = 100;
	
	@BeforeClass
	public static void init() {
		r = new Random();
		sessionFactory = Sample2ConnectionFactory.getSessionFactory();
	}
	
	/**
	 * 测试resultMap对嵌套对象的结果映射。
	 * 	1对1结果映射；association
	 * 	1对多的结果映射；collection
	 */
	@Test
	public void selectBlogDetailWithComplexJoin() throws Exception {
		boolean autoCommit = false;
		SqlSession session = sessionFactory.openSession(autoCommit);
		
		BlogMapper blogMapper = session.getMapper(BlogMapper.class);
		Blog blog = blogMapper.selectBlogDetails(3);
		
		System.out.println(new GsonBuilder().setPrettyPrinting().create().toJson(blog));
		
		blog = blogMapper.selectBlogDetails(1024);
		
		System.out.println(new GsonBuilder().setPrettyPrinting().create().toJson(blog));
		
		session.close();
		
	}
	
	/**
	 * 一级缓存、二级缓存的测试：
	 *  useCache: 是否启用二级缓存，对select而言默认启用，但是需手动配置。
	 *  flushCache：默认false，如果设置为true，则会清除一级缓存和二级缓存。
	 *  
	 * 循环2次：
	 * 	如果启用一级缓存，不启用二级缓存，则每次循环内部，会发出1次查询；
	 * 	如果启用二级缓存并在mapper中显示配置了二级缓存，则两次循环只会发出1条查询，第2次循环的时候直接使用二级缓存的结果。
	 */
	@Test
	public void selectBlogDetailTestCachePolicy() throws Exception {
		boolean autoCommit = false;
		for (int i = 0; i < 2; i++) {
			SqlSession session = sessionFactory.openSession(autoCommit);
			
			BlogMapper blogMapper = session.getMapper(BlogMapper.class);
			Blog blog = blogMapper.selectBlogDetails(3);
			
			System.out.println(new GsonBuilder().setPrettyPrinting().create().toJson(blog));
			
			blog = blogMapper.selectBlogDetails(1024);
			
			System.out.println(new GsonBuilder().setPrettyPrinting().create().toJson(blog));
			
			session.close(); // 关闭session后才会把查询结果写入到二级缓存中
		}
		
	}
	
	
	@Test
	public void selectAuthors() throws Exception {
		boolean autoCommit = false;
		SqlSession session = sessionFactory.openSession(autoCommit);
		AuthorMapper authorMapper = session.getMapper(AuthorMapper.class);
		List<Author> authors = authorMapper.selectAuthorByUsernameOrEmail("alice1%", "");
		
		System.out.println(new GsonBuilder().setPrettyPrinting().create().toJson(authors));
		
		session.close();
		
	}
}
