package clonegod.framework.test.dal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import clonegod.framework.dal.dao.Author;
import clonegod.framework.dal.dao.AuthorMapper;
import clonegod.framework.dal.dao.BlogPostsMapper;
import clonegod.framework.dal.dao.Post;
import clonegod.framework.dal.enums.CurrencyEnum;
import clonegod.framework.test.DalBaseTest;

/**
 * 批量查询一次不能发送太多的数据，受数据库的限制
 * 	有 SQL 长度限制， 定好 List 大小
		show variables like '%packet%';
		show variables like '%net_buffer%';
 *
 */
public class BatchInsertTest extends DalBaseTest {
	
	@Autowired
	SqlSessionTemplate sqlSessionTemplate;
	
	@Autowired
	AuthorMapper authorMapper;
	
	@Autowired
	BlogPostsMapper blogPostsMapper;
	
	/**
	 * 循环insert - 效率低
	 */
	@Test
	public void batchInsert1() {
		long start = System.currentTimeMillis();
		for(int i=0; i<10; i++) {
			Author author = new Author().withUsername("alice"+i)
	    			.withPassword("alice123")
	    			.withEmail("alice@mybatis.com");
			authorMapper.insert(author);
		}
		System.out.println("cost:" + (System.currentTimeMillis() - start));
	}
	
	/**
	 * foreach 拼接sql --- 效率高
	 */
	@Test
	@Transactional
	@Rollback(false)
	public void batchInsert2() {
		long start = System.currentTimeMillis();
		List<Post> posts = new ArrayList<>();
		for(int i=0; i<10; i++) {
	    	posts.add(new Post().withAuthorId(1).withBlogId(1)
					.withSection("section 1")
					.withSubject("Mybatis Batch insert")
					.withDraft("xxxxxxxxx")
					.withBody("Happy Programming")
					.withCreatedOn(new Date()));
		}
		blogPostsMapper.insertBatch(posts);
		System.out.println("cost:" + (System.currentTimeMillis() - start));
	}
	
	
	/**
	 * BatchExecutor
	 */
	@Test
	@Transactional
	public void batchInsert3() {
		SqlSession batchedSqlSession = sqlSessionTemplate.getSqlSessionFactory()
				.openSession(ExecutorType.BATCH);// 提供batch功能的session
		
		AuthorMapper authorMapper = batchedSqlSession.getMapper(AuthorMapper.class);
		
		long start = System.currentTimeMillis();
		
		for(int i=0; i<10; i++) {
			Author author = new Author().withUsername("author"+i).withPassword("").withEmail(""+i).withCurrency(CurrencyEnum.RMB);
			authorMapper.insert(author);
			// 批量提交
			if(i % 3 == 0 && i != 0) {
				batchedSqlSession.commit();
				batchedSqlSession.clearCache();
			}
		}
		
		System.out.println("cost:" + (System.currentTimeMillis() - start));
		batchedSqlSession.close();
	}
	
}
