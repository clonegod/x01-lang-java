package demo.mybatis.select;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import clonegod.framework.dal.dao.BlogPostsMapper;
import clonegod.framework.dal.dao.Post;
import clonegod.framework.dal.dao.resultmap.BlogPostsResultMap;
import clonegod.mybatis.utils.MybatisUtil;

public class TestMybatisSelect {

	/**
	 * 查询多条记录
	 * 
	 */
	@Test
	public void test01_selectList() throws Exception {
		SqlSession sqlSession = MybatisUtil.getSqlSession();
		BlogPostsMapper postMapper = sqlSession.getMapper(BlogPostsMapper.class);
		
		List<Post> posts = postMapper.selectPosts("Mybatis", 1);
		posts.forEach(x -> System.err.println(x));
	}
	
	/**
	 * 多表联合查询
	 * 
	 */
	@Test
	public void test02_selectMultiTableWithJoin() throws Exception {
		SqlSession sqlSession = MybatisUtil.getSqlSession();
		BlogPostsMapper blogMapper = sqlSession.getMapper(BlogPostsMapper.class);
		
		BlogPostsResultMap blogDetail = blogMapper.selectBlogPosts(1);
		System.out.println(blogDetail.getBlog());
		System.out.println(blogDetail.getAuthor());
		blogDetail.getPosts().forEach(post -> System.out.println(post.getSubject() + ":" + post.getBody()));
	}
	
	/**
	 * 多表查询- N+1问题，与懒加载策略
	 * 
	 * <collection ... fetchType="lazy"/ >
	 */
	@Test
	public void test03_selectMultiTableWith_N_plus_1() throws Exception {
		SqlSession sqlSession = MybatisUtil.getSqlSession();
		BlogPostsMapper blogMapper = sqlSession.getMapper(BlogPostsMapper.class);
		
		BlogPostsResultMap blogDetail = blogMapper.selectBlogPosts(5);
		System.out.println(blogDetail.getBlog().getTitle());
		
		// 如果开启懒加载，只有当真正关联表的获取数据时，才会发起SQL查询。因此可有效缓解N+1的问题。
		boolean getRelatedTableInfo = Math.random() > 0.5;
		if(getRelatedTableInfo) {
			blogDetail.getPosts().forEach(x -> System.out.println("postid=" + x.getId()));
		}
	}
	
	/**
	 * 内存分页 --- 效率低
	 * mybatis提供的RowBounds分页，并不是在数据库上进行的分页查询。而是将所有数据都查询到内存中，再丢弃那些不满足分页条件的数据。 
	 * 
	 * org.apache.ibatis.executor.resultset.DefaultResultSetHandler.skipRows(ResultSet, RowBounds)
	 * 
	 */
	@Test
	public void testPageQuery() throws Exception {
		SqlSession sqlSession = MybatisUtil.getSqlSession();
		List<Object> lists = sqlSession.selectList("selectPostsForBlog", 1, new RowBounds(3, 2));
		lists.forEach(x -> {System.out.println(x);});
	}
}
