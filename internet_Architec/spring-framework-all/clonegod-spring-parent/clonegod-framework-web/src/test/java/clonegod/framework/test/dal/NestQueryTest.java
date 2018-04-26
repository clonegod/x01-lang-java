package clonegod.framework.test.dal;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import clonegod.framework.dal.dao.Author;
import clonegod.framework.dal.dao.AuthorMapper;
import clonegod.framework.dal.dao.Blog;
import clonegod.framework.dal.dao.BlogMapper;
import clonegod.framework.dal.dao.BlogPostsMapper;
import clonegod.framework.dal.dao.Post;
import clonegod.framework.dal.dao.PostMapper;
import clonegod.framework.dal.dao.resultmap.BlogPostsResultMap;
import clonegod.framework.dal.enums.CurrencyEnum;
import clonegod.framework.test.DalBaseTest;

public class NestQueryTest extends DalBaseTest {
	
	@Autowired
	AuthorMapper authorMapper;
	
	@Autowired
	BlogMapper blogMapper;

	@Autowired
	PostMapper postMapper;
	
	
	@Test
	@Transactional
	@Rollback(false)
	public void testInsertBlogDatas() {
		Author bob = new Author().withUsername("bob")
				.withPassword(UUID.randomUUID().hashCode()+"")
				.withEmail("bob@spring.com")
				.withCurrency(CurrencyEnum.USA);
		
		authorMapper.insert(bob);
		
		Blog blog = new Blog().withAuthorId(bob.getId())
							.withTitle("bob's Blog");
		blogMapper.insert(blog);
		
		Post post1 = new Post().withAuthorId(bob.getId())
								.withBlogId(blog.getId())
								.withSection("section 1").withSubject("java").withBody("java is good").withDraft("empty")
								.withCreatedOn(new Date());
		Post post2 = new Post().withAuthorId(bob.getId())
								.withBlogId(blog.getId())
								.withSection("section 2").withSubject("java").withBody("spring is good").withDraft("empty")
								.withCreatedOn(new Date());
		Post post3 = new Post().withAuthorId(bob.getId())
								.withBlogId(blog.getId())
								.withSection("section 3").withSubject("java").withBody("mybatis is good").withDraft("empty")
								.withCreatedOn(new Date());
		postMapper.insert(post1);
		postMapper.insert(post2);
		postMapper.insert(post3);
		
	}
	
	
	@Autowired
	BlogPostsMapper blogPostMapper;
	
	/**
	 * 联合查询-嵌套查询
	 * N + 1 查询 - 需要发出后续sql来获取关联表的数据
	 * 懒加载策略
	 * @throws Exception
	 */
	@Test
	public void testLazyLoading() throws Exception {
		BlogPostsResultMap blogPosts = blogPostMapper.selectBlogPosts(1);
		System.out.println(blogPosts.getBlog());
		
		// 开启懒加载后，可优化嵌套查询N+1的问题
		TimeUnit.SECONDS.sleep(3);
		blogPosts.getPosts().forEach(post -> System.out.println(post));
	}
	
	/**
	 * 联合查询-嵌套结果
	 * join 查询 - 1条sql查询所有关联表的数据
	 */
	@Test
	public void testQueryTablesByJoin() {
		BlogPostsResultMap blogPosts = blogPostMapper.selectBlogAllRelatedInfoById(1);
		System.out.println(blogPosts.getAuthor());
		System.out.println(blogPosts.getBlog());
		blogPosts.getPosts().forEach(post -> System.out.println(post));
	}
}
