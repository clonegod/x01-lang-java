package demo.mybatis.select.cache;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import clonegod.framework.dal.dao.Author;
import clonegod.framework.dal.dao.AuthorMapper;
import clonegod.framework.dal.dao.BlogPostsMapper;
import clonegod.framework.dal.dao.Post;
import clonegod.framework.dal.dao.resultmap.BlogPostsResultMap;
import clonegod.mybatis.utils.MybatisUtil;

/**
 * 二级缓存：全局缓存，可以跨多个sqlsession进行缓存共享。
 * 
 * 开启二级缓存： 
 * 1、在settings配置：
 * 	 <setting name="cacheEnabled" value="true" />
 * 2、在mapper中添加cache标签： 
 * 	 <cache/>
 * 
 * 二级缓存的scope：同一个namespace下的crud共享一个缓存，即二级缓存是通过namespace进行隔离的。
 *  The cache will only apply to statements declared in the mapping file where the cache tag is located. 
 *  
 * 二级缓存有两个问题：
 * 1、容易产生脏数据 - 多表查询时，部分表的数据在其它namespace下发生更新，而当前namespace中仍然缓存的旧数据。
 * 2、二级缓存更新策略粒度太粗 - mapper namespace下，任一update操作，都会将该namespace下的所有二级缓存全部清空。
 * 
 * 建议：使用第三方缓存 redis来做二级缓存。
 */
public class TestMybatisSecondLevelCache {
	
	/**
	 * 二级缓存容易产生脏数据
	 * sqlsession1 从mapper1 查询blog和author的信息 --- 启用了二级缓存
	 * sqlsession2 从mapper2更新auhtor的数据
	 * 
	 * 由于操作的是不同的mapper，因此sqlsession1中再次查询将直接从二级缓存返回数据，此时返回的缓存数据已经是脏数据了。
	 * 
	 * 二级缓存产生脏数据的解决办法：
	 * 1、使用第三方缓存代替mybatis的二级缓存（推荐）
	 * 2、在涉及到的mapper中，通过cache-ref，指定为同一个namespace，可以解决脏数据的问题（比较麻烦）。
	 */
    @Test
    public void test01_secondLevelCacheReadDirty() throws Exception {
    	System.out.println("\nsqlSession1 - 第1次查询");
    	SqlSession sqlSession1 = MybatisUtil.getSqlSession();
    	BlogPostsMapper blogPostMapper1 = sqlSession1.getMapper(BlogPostsMapper.class);
    	BlogPostsResultMap blogPostMap1 = blogPostMapper1.selectBlogAllRelatedInfoById(1);
    	System.out.println(blogPostMap1.getAuthor().getUsername() + ": " + blogPostMap1.getAuthor().getPassword());
        sqlSession1.commit();
        sqlSession1.close();
    	
        System.out.println("\nsqlSession2 - 第2次查询");
    	SqlSession sqlSession2 = MybatisUtil.getSqlSession();
    	BlogPostsMapper blogPostMapper2 = sqlSession2.getMapper(BlogPostsMapper.class);
    	BlogPostsResultMap blogPostMap2 = blogPostMapper2.selectBlogAllRelatedInfoById(1);
    	System.out.println(blogPostMap2.getAuthor().getUsername() + ": " + blogPostMap2.getAuthor().getPassword());
        sqlSession2.commit();
        sqlSession2.close();
        
        System.err.println("\nsqlsession3 - 通过authorMapper更新了author的数据，但是二级缓存并没有更新，仍然缓存的脏数据");
        SqlSession sqlSession3 = MybatisUtil.getSqlSession();
        AuthorMapper authorMapper = sqlSession3.getMapper(AuthorMapper.class);
        authorMapper.updateByPrimaryKeySelective(new Author().withId(1).withPassword(Instant.now().toEpochMilli()+""));
        sqlSession3.commit();
        sqlSession3.close();
        
        System.out.println("\nsqlSession4 - 第3次查询");
    	SqlSession sqlSession4 = MybatisUtil.getSqlSession();
    	BlogPostsMapper blogPostMapper4 = sqlSession4.getMapper(BlogPostsMapper.class);
    	BlogPostsResultMap blogPostMap4 = blogPostMapper4.selectBlogAllRelatedInfoById(1);
    	System.out.println(blogPostMap4.getAuthor().getUsername() + ": " + blogPostMap4.getAuthor().getPassword());
        sqlSession4.commit();
        sqlSession4.close();
        
    }

    /**
     * insert,update,delete 导致对应namespace下的二级缓存失效 
     */
    @Test
    public void test02_secondLevelCacheFlushAllWhenUpdate() throws Exception {
    	System.out.println("\nsqlSession1 - 第1次查询");
    	SqlSession sqlSession1 = MybatisUtil.getSqlSession();
    	BlogPostsMapper blogPostMapper1 = sqlSession1.getMapper(BlogPostsMapper.class);
    	BlogPostsResultMap blogPostMap1 = blogPostMapper1.selectBlogAllRelatedInfoById(1);
    	System.out.println(blogPostMap1.getAuthor().getUsername() + ": " + blogPostMap1.getAuthor().getPassword());
        sqlSession1.commit();
        sqlSession1.close();
    	
        System.out.println("\nsqlSession2 - 第2次查询");
    	SqlSession sqlSession2 = MybatisUtil.getSqlSession();
    	BlogPostsMapper blogPostMapper2 = sqlSession2.getMapper(BlogPostsMapper.class);
    	BlogPostsResultMap blogPostMap2 = blogPostMapper2.selectBlogAllRelatedInfoById(1);
    	System.out.println(blogPostMap2.getAuthor().getUsername() + ": " + blogPostMap2.getAuthor().getPassword());
        sqlSession2.commit();
        sqlSession2.close();
        
        System.err.println("\nsqlsession3 - insert new record，导致二级缓存失效");
        SqlSession sqlSession3 = MybatisUtil.getSqlSession();
        BlogPostsMapper blogPostMapper3 = sqlSession3.getMapper(BlogPostsMapper.class);
        blogPostMapper3.insertBatch(Arrays.asList(new Post().withAuthorId(1).withBlogId(1)
				.withSection("section 2")
				.withSubject("Mybatis3")
				.withDraft("yyyyyyyyyyy")
				.withBody("Happy Programming")
    			.withCreatedOn(new Date())));
        sqlSession3.commit();
        sqlSession3.close();
        
        System.out.println("\nsqlSession4 - 第3次查询");
    	SqlSession sqlSession4 = MybatisUtil.getSqlSession();
    	BlogPostsMapper blogPostMapper4 = sqlSession4.getMapper(BlogPostsMapper.class);
    	BlogPostsResultMap blogPostMap4 = blogPostMapper4.selectBlogAllRelatedInfoById(1);
    	System.out.println(blogPostMap4.getAuthor().getUsername() + ": " + blogPostMap4.getAuthor().getPassword());
        sqlSession4.commit();
        sqlSession4.close();
    }
    
}
