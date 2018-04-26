package com.mybatis.sample2.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mybatis.sample2.dao.AuthorMapper;
import com.mybatis.sample2.dao.BlogMapper;
import com.mybatis.sample2.dao.CommentMapper;
import com.mybatis.sample2.dao.PostMapper;
import com.mybatis.sample2.dao.PostTagMapper;
import com.mybatis.sample2.dao.TagMapper;
import com.mybatis.sample2.model.Author;
import com.mybatis.sample2.model.Blog;
import com.mybatis.sample2.model.Comment;
import com.mybatis.sample2.model.Gender;
import com.mybatis.sample2.model.Post;
import com.mybatis.sample2.model.PostTag;
import com.mybatis.sample2.model.Tag;
import com.mybatis.sample2.util.Sample2ConnectionFactory;

public class InsertTest {
	static Random r;
	static SqlSessionFactory sessionFactory;
	static ThreadPoolExecutor executor;
	static int N_THREADS = 500;
	static final int BATCH_PER_THREAD = 10000;
	
	@BeforeClass
	public static void init() {
		r = new Random();
		sessionFactory = Sample2ConnectionFactory.getSessionFactory();
		executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(N_THREADS);
		executor.prestartAllCoreThreads();
	}
	
	@Test
	public void insertAll() throws Exception {
		long start = System.currentTimeMillis();
		
		initTag();
		
		List<Callable<Object>> tasks = new ArrayList<>();
		for(int i=0; i<N_THREADS; i++) {
			tasks.add(new InsertTask());
		}
		executor.invokeAll(tasks);
		
		long end = System.currentTimeMillis();
		
		System.out.println(String.format("Insert done! total time=%s(s)", (end-start)/1000));
	}

	/***
	 * Tag需要预先初始化到数据库中
	 */
	private void initTag() {
		boolean autoCommit = false;
		SqlSession session = sessionFactory.openSession(autoCommit);
		TagMapper tagMapper = session.getMapper(TagMapper.class);
		
		Tag tag = tagMapper.selectAnyTag();
		if (tag != null) {
			System.out.println("---------->>> Already exist Tag");
			return;
		}
		
		try {
			Tag tag1 = new Tag("Java");
			Tag tag2 = new Tag("Mybatis3");
			tagMapper.insertTag(tag1);
			tagMapper.insertTag(tag2);
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.rollback();
		} finally {
			session.close();
		}
	}
	
	/**
	 * 每个线程开启一个SqlSession
	 * 每个线程循环N次，依次插入Author,Blog,Post,Comment到数据库
	 * 需要先插入主表后才能得到主键，以便设置关联字段
	 */
	class InsertTask implements Callable<Object> {

		@Override
		public Object call() {
			boolean autoCommit = false;
			SqlSession session = sessionFactory.openSession(autoCommit);
			try {
				for (int i = 0; i < r.nextInt(BATCH_PER_THREAD); i++) {
					AuthorMapper authorMapper = session.getMapper(AuthorMapper.class);
					BlogMapper blogMapper = session.getMapper(BlogMapper.class);
					PostMapper postMapper = session.getMapper(PostMapper.class);
					TagMapper tagMapper = session.getMapper(TagMapper.class);
					PostTagMapper postTagMapper = session.getMapper(PostTagMapper.class);
					CommentMapper commentMapper = session.getMapper(CommentMapper.class);
					
					Author author = new Author("alice"+r.nextLong(), 
											"password"+r.nextLong(), 
											"alice"+r.nextLong()+"@blog.com", 
											r.nextInt()%2==0?Gender.男:Gender.女);
					authorMapper.insertAuthor(author);
					
					
					Blog blog = new Blog("my blog " + r.nextLong());
					blog.setAuthor(author);
					blogMapper.insertBlog(blog);
					
					
					Post post = new Post("post"+r.nextLong(), "body"+r.nextLong());
					post.setAuthor(author);
					post.setBlog(blog);
					postMapper.insertPost(post);
					
					for(int j = 0; j < r.nextInt(5); j++) {
						Comment comment = new Comment("this is comment "+r.nextLong());
						comment.setPost(post);
						commentMapper.insertComment(comment);
					}
					
					PostTag pt1 = new PostTag(post, tagMapper.selectAnyTag());
					PostTag pt2 = new PostTag(post, tagMapper.selectAnyTag());
					postTagMapper.insertPostTag(pt1);
					if(! pt1.getTag().getName().equals(pt2.getTag().getName())) {
						postTagMapper.insertPostTag(pt2);
					}
					session.commit();
				}
			} catch (Exception e) {
				e.printStackTrace();
				session.rollback();
			} finally {
				session.close();
			}
			return null;
		}
	}
}
