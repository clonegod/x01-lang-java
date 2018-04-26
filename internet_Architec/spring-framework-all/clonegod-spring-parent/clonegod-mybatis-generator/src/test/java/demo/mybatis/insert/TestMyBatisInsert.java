package demo.mybatis.insert;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;

import clonegod.framework.dal.dao.Author;
import clonegod.framework.dal.dao.AuthorMapper;
import clonegod.framework.dal.dao.Blog;
import clonegod.framework.dal.dao.BlogMapper;
import clonegod.framework.dal.dao.BlogPostsMapper;
import clonegod.framework.dal.dao.Post;
import clonegod.mybatis.utils.MybatisUtil;

@FixMethodOrder
public class TestMyBatisInsert {
	
	/**
	 * 测试插入数据
	 */
    @Test
    public void test01_Insert() throws Exception {
    	SqlSession sqlSession = MybatisUtil.getSqlSession();
    	AuthorMapper authorMapper = sqlSession.getMapper(AuthorMapper.class);
    	
    	Author author = new Author().withUsername("alice1")
    			.withPassword("alice123")
    			.withEmail("alice@mybatis.com");
    	authorMapper.insertSelective(author);
    	
    	// 必须提交sqlSession，否则数据是不会插入成功的！
    	sqlSession.commit();
    	sqlSession.close();
    	
    	Assert.assertTrue(author.getId() > 0);
    }
    
    /**
     * 测试批量插入数据
     */
    @Test
    public void test02_InsertBatch() throws Exception {
    	SqlSession sqlSession = MybatisUtil.getSqlSession();
    	
    	AuthorMapper authorMapper = sqlSession.getMapper(AuthorMapper.class);
    	Author author = new Author().withUsername("alice2")
    			.withPassword("alice123")
    			.withEmail("alice@mybatis.com");
    	authorMapper.insert(author);
    	
    	BlogMapper blogMapper = sqlSession.getMapper(BlogMapper.class);
    	Blog blog = new Blog().withAuthorId(author.getId())
    					.withTitle("FirstBlog");
    	blogMapper.insert(blog);
    	
    	BlogPostsMapper postMapper = sqlSession.getMapper(BlogPostsMapper.class);
    	List<Post> posts = new ArrayList<>();
    	posts.add(new Post().withAuthorId(author.getId()).withBlogId(blog.getId())
				.withSection("section 1")
				.withSubject("Mybatis Generator")
				.withDraft("xxxxxxxxx")
				.withBody("Happy Programming")
				.withCreatedOn(new Date()));
    	posts.add(new Post().withAuthorId(author.getId()).withBlogId(blog.getId())
				.withSection("section 2")
				.withSubject("Mybatis3")
				.withDraft("yyyyyyyyyyy")
				.withBody("Happy Programming")
    			.withCreatedOn(new Date()));
    	postMapper.insertBatch(posts);
    	
    	// 必须提交sqlSession，否则数据是不会插入成功的！
    	sqlSession.commit();
    	sqlSession.close();
    	
    }

}
